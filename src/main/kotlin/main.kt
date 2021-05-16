import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
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
		var text by mutableStateOf("Initial value")

		CoroutineScope(IO).launch {
			val webClient = WebClient.create("http://localhost:8080")

			val response = webClient.get()
				.uri("/algo/block/summary-flux")
				.retrieve()
				.bodyToFlux<ShortBlockSummary>()
				.map {
					return@map it
				}
			response.subscribe {
				text = it.toString()
			}
		}

		MaterialTheme {
			Button(onClick = { text = "Hello, Desktop!" }) {
				Text(text)
			}
		}
	}
}