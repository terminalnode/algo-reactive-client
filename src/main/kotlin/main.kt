import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import se.newton.algocompose.algoapi.models.ShortBlockSummary

fun main() {
	return Window(
		title = "Newton AlgoCompose",
		size = IntSize(300, 300)
	) {
		//var text by remember { mutableStateOf("Hello, World!") }
		var lastBlockSummary by mutableStateOf(ShortBlockSummary())
		val blockSummaryList = mutableStateListOf<ShortBlockSummary>()

		CoroutineScope(IO).launch {
			WebClient.create("http://localhost:8080")
				.get()
				.uri("/algo/block/summary-flux")
				.retrieve()
				.bodyToFlux<ShortBlockSummary>()
				.subscribe {
					lastBlockSummary = it
					if (blockSummaryList.size >= 5) blockSummaryList.removeLast()
					blockSummaryList.add(0, it)
				}
		}

		MaterialTheme {
			Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
				Scaffold(
					topBar = {
						TopAppBar(
							title = { Text("Newton AlgoCompose (${lastBlockSummary.net})") }
						)
					},
					content = {
						Column {
							Text("Last block: ${lastBlockSummary.round}")
							Text("Blocks received: ${blockSummaryList.size}")
							blockSummaryList.forEach { it.cardBody() }
						}
					}
				)
			}
		}
	}
}