package ru.popov.bodya.howmoney.data.database.preferences

import android.content.SharedPreferences
import ru.popov.bodya.howmoney.domain.global.models.Currency

/**
 *  @author popovbodya
 */
class SharedPreferencesWrapper(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val CURRENCY_TYPE_KEY = "currency_type"
        const val USD_CURRENCY = "USD"
        const val RUB_CURRENCY = "RUB"
    }

    fun getDefaultCurrencyType(): Currency {
        val currency: String = sharedPreferences.getString(CURRENCY_TYPE_KEY, USD_CURRENCY)
        return when (currency) {
            USD_CURRENCY -> Currency.USD
            RUB_CURRENCY -> Currency.RUB
            else -> Currency.RUB
        }
    }

    fun saveDefaultCurrency(currency: Currency) {
        val defaultCurrency: String = when (currency) {
            Currency.USD -> USD_CURRENCY
            Currency.RUB -> RUB_CURRENCY
        }
        saveDefaultCurrency(defaultCurrency)

    }

    fun saveDefaultCurrency(defaultCurrency: String) {
        val editor = sharedPreferences.edit();
        editor.putString(CURRENCY_TYPE_KEY, defaultCurrency)
        editor.apply()
    }

}