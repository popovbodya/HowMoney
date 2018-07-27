package ru.popov.bodya.howmoney.data.network.api

import io.reactivex.Single
import ru.popov.bodya.howmoney.BuildConfig
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.domain.account.models.Currency

/**
 * @author popovbodya
 */
class CurrenciesRateApiWrapper(private val currenciesRateApi: CurrenciesRateApi) {

    companion object {
        const val API_KEY = "access_key"
        const val FROM_CURRENCY_KEY = "from"
        const val TO_CURRENCY_KEY = "to"
        const val DEFAULT_AMOUNT_KEY = "amount"
        const val BASE_AMOUNT = "1"
    }

    fun getCurrentRate(): Single<CurrentRateBean> = currenciesRateApi.getCurrentRate(buildQueryMap())


    private fun buildQueryMap(): Map<String, String> {
        val hashMapOf = hashMapOf(
                API_KEY to BuildConfig.FIXER_API_KEY,
                FROM_CURRENCY_KEY to Currency.USD.stringValue,
                TO_CURRENCY_KEY to Currency.RUB.stringValue,
                DEFAULT_AMOUNT_KEY to BASE_AMOUNT
        )
        return hashMapOf
    }
}