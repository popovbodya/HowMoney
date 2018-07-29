package ru.popov.bodya.howmoney.domain.expense.interactor

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategoryBalance
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet

/**
 *  @author popovbodya
 */
class ExpenseInteractor(private val currencyRateRepository: CurrencyRateRepository,
                        private val walletRepository: WalletRepository) {

    fun calculateBalanceOfExpenseList(categoryList: List<ExpenseCategoryBalance>): Single<Double> {
        return Observable.fromIterable(categoryList)
                .reduce(0.0) { acc, balance -> acc + balance.amount }
    }

    fun getExpenseOperationList(wallet: Wallet): Single<List<ExpenseCategoryBalance>> {

        val operations = Observable.fromIterable(walletRepository.getExpenseOperationList(wallet))
        val categoryGroupsKeys: Observable<ExpenseCategory> = operations.map { it.expenseCategory }
        val categoryAmounts: Observable<Double> = operations.flatMap { getOperationAmount(it) }

        return categoryAmounts.zipWith(categoryGroupsKeys) { amount, key ->
            Observable.just(ExpenseCategoryBalance(key, amount))
                    .groupBy { it.expenseCategory }
                    .flatMap { group -> group.reduce(0.0) { acc, balance -> acc + balance.amount }.toObservable() }
                    .map { ExpenseCategoryBalance(key, it) }
        }
                .flatMap { it }
                .toList()
    }

    private fun getOperationAmount(expenseOperation: ExpenseOperation): Observable<Double> {
        return when (expenseOperation.currency) {
            Currency.USD -> currencyRateRepository.getExchangeRate().map { rate: CurrentRateBean ->
                rate.result * expenseOperation.amount
            }.toObservable()
            Currency.RUB -> Observable.just(expenseOperation.amount)
        }
    }
}