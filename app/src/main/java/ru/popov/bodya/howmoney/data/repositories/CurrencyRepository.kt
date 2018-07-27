package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApiWrapper
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.domain.account.models.Currency

/**
 *  @author popovbodya
 */
class CurrencyRepository(private val currenciesRateApiWrapper: CurrenciesRateApiWrapper, private val sharedPreferencesWrapper: SharedPreferencesWrapper) {

    fun readDefaultCurrencyValue(): Single<Currency> = Single.fromCallable { sharedPreferencesWrapper.getDefaultCurrencyType() }

    fun saveDefaultCurrencyValue(currency: Currency): Completable =
            Completable.fromAction { sharedPreferencesWrapper.saveDefaultCurrency(currency) }

    fun getExchangeRate(): Single<CurrentRateBean> = currenciesRateApiWrapper.getCurrentRate()

    fun getCurrentBalance(): Single<Long> = Single.just(301_456L)
}