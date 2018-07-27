package ru.popov.bodya.howmoney.data.network.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean

/**
 * @author popovbodya
 */
interface CurrenciesRateApi {

    companion object {
        const val FIXER_BASE_URL = "https://data.fixer.io/api/"
    }

    @GET("convert")
    fun getCurrentRate(@QueryMap params: Map<String, String>): Single<CurrentRateBean>

}