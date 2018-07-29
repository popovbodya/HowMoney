package ru.popov.bodya.howmoney.data.database.preferences

import android.content.SharedPreferences
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.domain.wallet.models.Currency

/**
 *  @author popovbodya
 */
class SharedPreferencesWrapper(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val EXCHANGE_RATE_KEY = "currency_type"
        const val DEFAULT_RATE = "60"
    }

    fun getExchangeRate(): String = sharedPreferences.getString(EXCHANGE_RATE_KEY, DEFAULT_RATE)

    fun saveExchangeRate(currentRateBean: CurrentRateBean) {
        val editor = sharedPreferences.edit()
        editor.putString(EXCHANGE_RATE_KEY, currentRateBean.result.toString())
        editor.apply()
    }
}