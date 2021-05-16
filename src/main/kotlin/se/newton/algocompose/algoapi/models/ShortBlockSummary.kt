package se.newton.algocompose.algoapi.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShortBlockSummary(
	var net: String = "unknown",
	var round: Int = 0,
	var transactions: Int = 0,
) {
	fun toFunString(): String {
		return "$transactions txs ($net, #$round)"
	}

	@Composable
	fun cardBody() {
		Card(
			modifier = Modifier.padding(10.dp).fillMaxWidth(),
			border = BorderStroke(2.dp, Color.Magenta),
		) {
			Column(
				modifier = Modifier.padding(5.dp),
				verticalArrangement = Arrangement.spacedBy(2.dp)
			) {
				Text("Round $round")
				Text("$transactions txs")
			}
		}
	}
}