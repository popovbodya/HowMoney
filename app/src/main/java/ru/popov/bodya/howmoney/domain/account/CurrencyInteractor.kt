package ru.popov.bodya.howmoney.domain.account

import io.reactivex.Single
import ru.popov.bodya.howmoney.domain.global.models.Currency
import ru.popov.bodya.howmoney.domain.global.models.Operation
import ru.popov.bodya.howmoney.domain.global.models.OperationType

/**
 *  @author popovbodya
 */
class CurrencyInteractor {

    fun getCurrentCurrencyAmount(currency: Currency): Single<Long> {
        return Single.fromCallable { calculateAmountInRublesWithCurrency(currency, getStubAmount()) }
    }

    fun calculateBalance(operationList: List<Operation>): Long = operationList
            .asSequence()
            .map { getOperationBalance(it) }
            .sum()

    private fun calculateAmountInRublesWithCurrency(currency: Currency, baseAmountInRubles: Long): Long =
            when (currency) {
                Currency.USD -> baseAmountInRubles / getStubExchangeRate()
                Currency.RUB -> baseAmountInRubles
            }

    private fun getOperationBalance(operation: Operation): Long {
        var amount = when (operation.operationType) {
            OperationType.WITHDRAWAL -> -1L
            OperationType.ADDITION -> 1L
        }
        amount *= when (operation.currency) {
            Currency.USD -> operation.amount * getStubExchangeRate()
            Currency.RUB -> operation.amount
        }
        return amount
    }

    private fun getStubAmount() = 301_456L

    private fun getStubExchangeRate() = 60

}