import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.MinusSquare
import compose.icons.feathericons.PlusSquare
import se.newton.algocompose.algoapi.AppState

fun main() {
	return Window(
		title = "Newton AlgoCompose",
		size = IntSize(300, 300)
	) {
		MaterialTheme {
			Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
				Scaffold(
					topBar = { AlgoComposeTopAppBar() },
					content = {
						Row {
							BlockSummaryColumn()

							Column(Modifier.padding(10.dp)) {
								BlockReceivedStatus()
								BlocksToKeepSetter()
								TransactionGraph()
							}
						}
					}
				)
			}
		}
	}
}

@Composable
@Suppress("FunctionName")
fun AlgoComposeTopAppBar() {
	TopAppBar(
		title = { Text(
			text = "Newton AlgoCompose (${AppState.lastBlockSummary.net})",
			color = Color.White) },
		backgroundColor = Color.Black,
	)
}

@Composable
@Suppress("FunctionName")
fun TransactionGraph() {
	Column {
		AppState.blockSummaryList.forEach {
			Canvas(Modifier.size(height = 30.dp, width = 55.dp)) {
				drawRect(
					color = Color.Red,
					size = Size(
						width = 5.0F + (it.transactions) * 10,
						height = 25.0F)
				)
			}
		}
	}
}

@Composable
@Suppress("FunctionName")
fun BlockSummaryColumn() {
	Column(Modifier.defaultMinSize(minWidth = 250.dp)) {
		AppState.blockSummaryList.map { it.cardBody() }
	}
}

@Composable
@Suppress("FunctionName")
fun BlockReceivedStatus() {
	Row {
		Column {
			Text(text = "Last block:", fontWeight = FontWeight.Bold)
			Text(text = "Blocks received:", fontWeight = FontWeight.Bold)
		}
		Column(Modifier.padding(start = 10.dp)) {
			Text(text = "${AppState.lastBlockSummary.round}")
			Text(text = "${AppState.blocksReceivedCount}")
		}
	}
}

@Composable
@Suppress("FunctionName")
fun BlocksToKeepSetter() {
	Row(verticalAlignment = Alignment.CenterVertically) {
		Text(
			text = "Blocks to keep:",
			fontWeight = FontWeight.Bold)
		BlocksToKeepSetterButton(isIncrease = false) { AppState.decrementBlockLimit() }

		Text(
			text = "${AppState.blockLimit}",
			modifier = Modifier.padding(start = 2.dp, end = 2.dp))

		BlocksToKeepSetterButton(isIncrease = true) { AppState.incrementBlockLimit() }
	}
}

@Composable
@Suppress("FunctionName")
fun BlocksToKeepSetterButton(isIncrease: Boolean, onClick: (() -> Unit)) {
	val buttonModifier = Modifier.size(30.dp)
	val increaseOrDecrease = if (isIncrease) "Increase" else "Decrease"

	val isEnabled = if (isIncrease) AppState.blockLimit < AppState.blockLimitMax
	else AppState.blockLimit > AppState.blockLimitMin

	IconButton(onClick = onClick, enabled = isEnabled) {
		Icon(
			imageVector = if (isIncrease) FeatherIcons.PlusSquare else FeatherIcons.MinusSquare,
			contentDescription = "$increaseOrDecrease number of blocks to keep",
			modifier = buttonModifier
		)
	}
}