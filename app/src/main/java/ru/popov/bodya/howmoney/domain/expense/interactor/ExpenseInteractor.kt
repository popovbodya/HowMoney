package ru.popov.bodya.howmoney.domain.expense.interactor

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategoryBalance
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import java.util.*

/**
 *  @author popovbodya
 */
class ExpenseInteractor(private val currencyRateRepository: CurrencyRateRepository,
                        private val walletRepository: WalletRepository) {


    fun calculateFinalExpenseBalance(expenseCategoryMap: Map<ExpenseCategory, Double>): Single<Double> {
        return Observable.fromIterable(expenseCategoryMap.values)
                .reduce(0.0) { acc, balance -> acc + balance }
    }

    fun getExpenseCategoryMap(wallet: Wallet): Single<Map<ExpenseCategory, Double>> {
        return Single.fromCallable { walletRepository.getExpenseOperationList(wallet) }.map { getCategoryBalanceMap(it) }
    }

    private fun getCategoryBalanceMap(expenseOperationList: List<ExpenseOperation>): Map<ExpenseCategory, Double> {
        val categoryBalanceMap = EnumMap<ExpenseCategory, Double>(ExpenseCategory::class.java)
        for (operation in expenseOperationList) {
            val currentAmount = categoryBalanceMap[operation.expenseCategory]
            if (currentAmount == null) {
                categoryBalanceMap[operation.expenseCategory] = getOperationAmount(operation)
            } else {
                categoryBalanceMap[operation.expenseCategory] = currentAmount + getOperationAmount(operation)
            }
        }
        return categoryBalanceMap
    }

    private fun getOperationAmount(expenseOperation: ExpenseOperation): Double {
        return when (expenseOperation.currency) {
            Currency.USD -> currencyRateRepository.getCachedExchangeRate() * expenseOperation.amount
            Currency.RUB -> expenseOperation.amount
        }
    }
}