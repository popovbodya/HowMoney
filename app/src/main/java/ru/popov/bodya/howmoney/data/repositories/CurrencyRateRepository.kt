package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApiWrapper
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.domain.wallet.models.Currency

/**
 *  @author popovbodya
 */
class CurrencyRateRepository(private val currenciesRateApiWrapper: CurrenciesRateApiWrapper, private val sharedPreferencesWrapper: SharedPreferencesWrapper) {

    fun getExchangeRate(): Single<CurrentRateBean> =
            currenciesRateApiWrapper.getCurrentRate().doOnSuccess { sharedPreferencesWrapper.saveExchangeRate(it) }

    fun getCachedExchangeRate(): Double = sharedPreferencesWrapper.getExchangeRate().toDouble()
}