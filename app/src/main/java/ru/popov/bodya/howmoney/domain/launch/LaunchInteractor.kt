package ru.popov.bodya.howmoney.domain.launch

import io.reactivex.Single
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository

/**
 *  @author popovbodya
 */
class LaunchInteractor(private val currencyRateRepository: CurrencyRateRepository) {

    fun getExchangeRate(): Single<CurrentRateBean> = currencyRateRepository.getExchangeRate()

}