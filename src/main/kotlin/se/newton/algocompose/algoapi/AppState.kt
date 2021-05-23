package se.newton.algocompose.algoapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import reactor.netty.http.client.HttpClient
import se.newton.algocompose.algoapi.models.ShortBlockSummary
import se.newton.algocompose.utils.toFlux

object AppState {
	private const val baseUrl = "http://localhost:8080"
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
				.toFlux<ShortBlockSummary>()
				.subscribe {
					lastBlockSummaryInternal = it
					if (blockSummaryList.size >= 5) blockSummaryList.removeLast()
					blockSummaryList.add(0, it)
				}
		}
	}
}