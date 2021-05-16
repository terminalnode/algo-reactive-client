package se.newton.algocompose.algoapi

import retrofit2.http.GET
import rx.Observable
import se.newton.algocompose.algoapi.models.ShortBlockSummary

interface AlgoApi {
	@GET("algo/block/summary-flux")
	suspend fun getBlockSummaryFlux(): Observable<List<ShortBlockSummary>>
}