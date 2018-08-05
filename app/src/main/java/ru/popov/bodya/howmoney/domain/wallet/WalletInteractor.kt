package ru.popov.bodya.howmoney.domain.wallet

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.TransactionsRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet

class WalletInteractor(private val currencyRateRepository: CurrencyRateRepository,
                       private val walletRepository: WalletRepository,
                       private val transactionsRepository: TransactionsRepository) {

    fun createWallet(wallet: Wallet): Completable
            = walletRepository.addWallet(wallet)

    fun deleteWallet(walletId: Int)
            = walletRepository.deleteWallet(walletId).andThen(transactionsRepository.deleteAllTransactionsByWalletId(walletId))

    fun getWalletBalance(walletId: Int): Flowable<Double>
            = walletRepository.getWalletById(walletId).map { it.amount }

    fun getMajorCurrencyForWallet(walletId: Int): Flowable<Currency>
            = walletRepository.getWalletById(walletId).map { it.majorCurrency }

    fun getAllWallets(): Flowable<List<Wallet>> = walletRepository.getWallets()

    fun getIncomeSumFromAllWalletsByWalletId(walletId: Int): Flowable<Double>
            = transactionsRepository.getAllIncomeTransactionsSumByWalletId(walletId)

    fun getExpenseSumFromAllWalletsByWalletId(walletId: Int): Flowable<Double>
            = transactionsRepository.getAllExpenseTransactionsSumByWalletId(walletId)

    fun getAllTransactions(): Flowable<List<Transaction>>
            = transactionsRepository.getAllTransactions()

    fun getAllIncomeTransactions(): Flowable<List<Transaction>>
            = transactionsRepository.getAllIncomeTransactions()

    fun getAllExpenseTransactions(): Flowable<List<Transaction>>
            = transactionsRepository.getAllExpenseTransactions()

    fun getAllIncomeTransactionsByWallet(walletId: Int): Flowable<List<Transaction>>
            = transactionsRepository.getAllIncomeTransactionsByWallet(walletId)

    fun getAllExpenseTransactionsByWallet(walletId: Int): Flowable<List<Transaction>>
            = transactionsRepository.getAllExpenseTransactionsByWallet(walletId)

    fun getAllTransactionsByWallet(walletId: Int): Flowable<List<Transaction>>
            = transactionsRepository.getAllTransactionsByWallet(walletId)

    fun getExchangeRate(fromCurrency: Currency, toCurrency: Currency)
            = currencyRateRepository.getExchangeRate(fromCurrency, toCurrency)

    fun createTransaction(transaction: Transaction): Completable {
        return putTransactionOnWalletWithTheSameCurrency(transaction)
    }

    private fun putTransactionOnWalletWithTheSameCurrency(transaction: Transaction): Completable {
        return walletRepository.increaseWalletBalance(transaction.walletId, transaction.amount).doOnComplete {
            transactionsRepository.addTransaction(transaction)
        }
    }

    private fun putTransactionOnWalletWithDifferentCurrency(transaction: Transaction): Completable {
        return walletRepository.getWalletById(transaction.walletId)
                .flatMap { wallet -> currencyRateRepository.getExchangeRate(transaction.currency, wallet.majorCurrency).toFlowable() }
                .map { rate -> transaction.amount * rate }
                .flatMapCompletable { inc -> walletRepository.increaseWalletBalance(transaction.walletId, inc) }
                .andThen { transactionsRepository.addTransaction(transaction) }
    }

}