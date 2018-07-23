package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.domain.global.models.Currency

/**
 *  @author popovbodya
 */
class CurrencyRepository(private val sharedPreferencesWrapper: SharedPreferencesWrapper) {

    fun readDefaultCurrencyValue(): Single<Currency> = Single.fromCallable { sharedPreferencesWrapper.getDefaultCurrencyType() }

    fun saveDefaultCurrencyValue(currency: Currency): Completable =
            Completable.fromAction { sharedPreferencesWrapper.saveDefaultCurrency(currency) }
}