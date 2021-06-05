package se.newton.algocompose.algoapi.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import se.newton.algocompose.algoapi.AppState

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
		return Card(
			modifier = Modifier
				.padding(start = 10.dp, top = 10.dp, end = 10.dp)
				.requiredWidth(230.dp),
			border = BorderStroke(2.dp, Color.Black),
		) {
			Column(
				modifier = Modifier.padding(5.dp),
				verticalArrangement = Arrangement.spacedBy(2.dp)
			) {
				Row {
					Column {
						Text(text = "Round:", fontWeight = FontWeight.Bold)
						Text(text = "Transactions:", fontWeight = FontWeight.Bold)
					}
					Column(Modifier.padding(start = 10.dp)) {
						Text(text = "$round")
						Text(text = "$transactions txs")
					}
				}
			}
		}
	}
}