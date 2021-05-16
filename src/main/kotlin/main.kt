import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
							Column {
								Row {
									AppState.blockSummaryList.forEach {
										Canvas(Modifier.size(30.dp, 30.dp)) {
											drawRect(color = Color.Red, size = Size(25.0F, 5.0F + (it.transactions) * 10.0F))
										}
									}
								}
								Row {
									Text("Last block: ${AppState.lastBlockSummary.round}")
									Text("Blocks received: ${AppState.blockSummaryList.size}")
								}
							}
							AppState.blockSummaryList.forEach { it.cardBody() }
						}
					}
				)
			}
		}
	}
}