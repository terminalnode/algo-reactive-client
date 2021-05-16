import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import se.newton.algocompose.algoapi.AppState

fun main() {
	return Window(
		title = "Newton AlgoCompose",
		size = IntSize(300, 300)
	) {
		MaterialTheme {
			Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
				Scaffold(
					topBar = {
						TopAppBar(
							title = { Text("Newton AlgoCompose (${AppState.lastBlockSummary.net})") }
						)
					},
					content = {
						Column {
							Text("Last block: ${AppState.lastBlockSummary.round}")
							Text("Blocks received: ${AppState.blockSummaryList.size}")
							AppState.blockSummaryList.forEach { it.cardBody() }
						}
					}
				)
			}
		}
	}
}