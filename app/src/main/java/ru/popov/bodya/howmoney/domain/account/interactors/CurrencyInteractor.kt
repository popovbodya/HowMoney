package ru.popov.bodya.howmoney.domain.account.interactors

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.data.repositories.CurrencyRepository
import ru.popov.bodya.howmoney.domain.account.models.Currency

/**
 *  @author popovbodya
 */
class CurrencyInteractor(private val accountRepository: CurrencyRepository) {

    fun getCurrentBalance(currency: Currency): Single<Double> {
        return accountRepository.getCurrentBalance().flatMap { getBalanceBasedOnCurrency(it, currency) }
    }

//    fun calculateBalance(operationList: List<Operation>): Long = operationList
//            .asSequence()
//            .map { getOperationBalance(it) }
//            .sum()
//
//    private fun getOperationBalance(operation: Operation): Long {
//        var amount = when (operation.operationType) {
//            OperationType.WITHDRAWAL -> -1L
//            OperationType.ADDITION -> 1L
//        }
//        amount *= when (operation.currency) {
//            Currency.USD -> operation.amount * accountRepository.getExchangeRate()
//            Currency.RUB -> operation.amount
//        }
//        return amount
//    }


    private fun getBalanceBasedOnCurrency(balance: Long, currency: Currency): Single<Double> =
            when(currency) {
                Currency.RUB -> Single.just(balance.toDouble())
                Currency.USD -> Single.just(balance).zipWith(
                        accountRepository.getExchangeRate(),
                        BiFunction { amount: Long, currentRate: CurrentRateBean -> amount / currentRate.result}
                )
            }
}