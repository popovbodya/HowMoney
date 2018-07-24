package ru.popov.bodya.howmoney.domain.account.interactors

import io.reactivex.Single
import ru.popov.bodya.howmoney.domain.account.models.Account
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.domain.operation.models.Operation
import ru.popov.bodya.howmoney.domain.operation.models.OperationType

/**
 *  @author popovbodya
 */
class CurrencyInteractor {

    fun getCurrentCurrencyAmount(currency: Currency): Single<Account> {
        return Single.fromCallable { getAccount(currency, getStubAmount()) }
    }

    fun calculateBalance(operationList: List<Operation>): Long = operationList
            .asSequence()
            .map { getOperationBalance(it) }
            .sum()

    private fun getAccount(currency: Currency, baseAmountInRubles: Long): Account = when (currency) {
        Currency.USD -> Account(baseAmountInRubles / getStubExchangeRate(), currency)
        Currency.RUB -> Account(baseAmountInRubles, currency)
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