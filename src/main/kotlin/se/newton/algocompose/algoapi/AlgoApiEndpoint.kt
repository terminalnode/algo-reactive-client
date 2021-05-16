package se.newton.algocompose.algoapi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import se.newton.algocompose.algoapi.models.ShortBlockSummary

private const val algoApiBaseUrl = "http://localhost:8080/"

object AlgoApiEndpoint {
	private val api: AlgoApi =
		Retrofit.Builder()
			.baseUrl(algoApiBaseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			.build()
			.create(AlgoApi::class.java)

	suspend fun getBlockSummaryFlux(): Observable<List<ShortBlockSummary>> =
		api.getBlockSummaryFlux()
}