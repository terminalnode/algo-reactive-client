package se.newton.algocompose.algoapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import se.newton.algocompose.algoapi.models.ShortBlockSummary

object AppState {
	private val webClient: WebClient = WebClient.create("http://localhost:8080")
	val blockSummaryList = mutableStateListOf<ShortBlockSummary>()
	private var lastBlockSummaryInternal by mutableStateOf(ShortBlockSummary())

	init {
		CoroutineScope(Dispatchers.IO).launch {
			webClient
				.get()
				.uri("/algo/block/summary-flux")
				.retrieve()
				.bodyToFlux<ShortBlockSummary>()
				.subscribe {
					lastBlockSummaryInternal = it

					if (blockSummaryList.size >= 5) blockSummaryList.removeLast()
					blockSummaryList.add(0, it)
				}
		}
	}

	val lastBlockSummary
		get() = lastBlockSummaryInternal
}