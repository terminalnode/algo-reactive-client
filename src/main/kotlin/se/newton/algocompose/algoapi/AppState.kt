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

	var blocksReceivedCount by mutableStateOf(0)
	private set

	var lastBlockSummary by mutableStateOf(ShortBlockSummary())
	private set

	const val blockLimitMin = 5
	const val blockLimitMax = 25

	var blockLimit by mutableStateOf(10)
	private set

	// lastBlockSummary has a private setter
	val blockSummaryList = mutableStateListOf<ShortBlockSummary>()

	fun changeBlockLimit(newValue: Int) {
		if (newValue in blockLimitMin..blockLimitMax) {
			blockLimit = newValue
		}
	}

	fun incrementBlockLimit() {
		changeBlockLimit(blockLimit + 1)
	}

	fun decrementBlockLimit() {
		changeBlockLimit(blockLimit - 1)
	}

	init {
		CoroutineScope(Dispatchers.IO).launch {
			HttpClient.create()
				.baseUrl("$baseUrl/algo/block/summary-flux")
				.get()
				.responseContent()
				.toFlux<ShortBlockSummary>()
				.subscribe {
					lastBlockSummary = it
					blocksReceivedCount += 1

					if (blockSummaryList.size >= blockLimit) {
						blockSummaryList.removeLast()
					}
					blockSummaryList.add(0, it)
				}
		}
	}
}