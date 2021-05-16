package se.newton.algocompose.algoapi.models

data class ShortBlockSummary(
	var net: String = "unknown",
	var round: Int = 0,
	var transactions: Int = 0,
) {
	fun toFunString(): String {
		return "$transactions txs ($net, #$round)"
	}
}