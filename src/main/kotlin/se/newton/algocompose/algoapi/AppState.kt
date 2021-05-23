package se.newton.algocompose.algoapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import reactor.kotlin.core.publisher.toFlux
import reactor.netty.http.client.HttpClient
import se.newton.algocompose.algoapi.models.ShortBlockSummary

object AppState {
	private const val baseUrl = "http://localhost:8080"
	private val gson = Gson()
	private var lastBlockSummaryInternal by mutableStateOf(ShortBlockSummary())

	// lastBlockSummary has a private setter
	val lastBlockSummary
		get() = lastBlockSummaryInternal
	val blockSummaryList = mutableStateListOf<ShortBlockSummary>()

	init {
		CoroutineScope(Dispatchers.IO).launch {
			HttpClient.create()
				.baseUrl("$baseUrl/algo/block/summary-flux")
				.get()
				.responseContent()
				.toFlux()
				.subscribe {
					val jsonString = it.getCharSequence(0, it.capacity(), Charsets.UTF_8).toString()
					val blockSummary = gson.fromJson(jsonString, ShortBlockSummary::class.java)

					lastBlockSummaryInternal = blockSummary
					if (blockSummaryList.size >= 5) blockSummaryList.removeLast()
					blockSummaryList.add(0, blockSummary)
				}
		}
	}
}