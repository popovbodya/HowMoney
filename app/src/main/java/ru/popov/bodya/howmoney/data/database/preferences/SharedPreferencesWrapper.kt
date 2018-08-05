package ru.popov.bodya.howmoney.data.database.preferences

import android.content.SharedPreferences
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean

/**
 *  @author popovbodya
 */
class SharedPreferencesWrapper(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val FAV_CURRENCY_KEY = "FAV_CURRENCY_KEY"
        const val DEFAULT_CURRENCY = "RUB"
        const val EXCHANGE_RATE_KEY = "currency_type"
        const val DEFAULT_RATE = "60"
    }

    fun getExchangeRate(): String = sharedPreferences.getString(EXCHANGE_RATE_KEY, DEFAULT_RATE)

    fun getFavExchangeRate(): String = sharedPreferences.getString(FAV_CURRENCY_KEY, DEFAULT_CURRENCY)

    fun setNewFavCurrency(currency: String) {
        val editor = sharedPreferences.edit()
        editor.putString(FAV_CURRENCY_KEY, currency)
        editor.apply()
    }

    fun saveExchangeRate(currentRateBean: CurrentRateBean) {
        val editor = sharedPreferences.edit()
        editor.putString(EXCHANGE_RATE_KEY, currentRateBean.result.toString())
        editor.apply()
    }
}