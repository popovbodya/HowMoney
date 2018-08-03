package ru.popov.bodya.howmoney.domain.wallet

import io.reactivex.Completable
import io.reactivex.Single
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

    fun getWalletBalance(walletId: Int): Single<Double>
            = walletRepository.getWalletById(walletId).map { it.amount }

    fun getMajorCurrencyForWallet(walletId: Int): Single<Currency>
            = walletRepository.getWalletById(walletId).map { it.majorCurrency }

    fun getAllWallets(): Single<List<Wallet>> = walletRepository.getWallets()

    fun getAllIncomeTransactions(): Single<List<Transaction>>
            = transactionsRepository.getAllIncomeTransactions()

    fun getAllExpenseTransactions(): Single<List<Transaction>>
            = transactionsRepository.getAllExpenseTransactions()

    fun getAllIncomeTransactionsByWallet(walletId: Int): Single<List<Transaction>>
            = transactionsRepository.getAllIncomeTransactionsByWallet(walletId)

    fun getAllExpenseTransactionsByWallet(walletId: Int): Single<List<Transaction>>
            = transactionsRepository.getAllExpenseTransactionsByWallet(walletId)

    fun getAllTransactionsByWallet(walletId: Int): Single<List<Transaction>>
            = transactionsRepository.getAllTransactionsByWallet(walletId)

    fun createTransaction(transaction: Transaction): Completable {
        return walletRepository.getWalletById(transaction.walletId)
                .flatMapCompletable { wallet ->
                    if (wallet.majorCurrency != transaction.currency)
                        putTransactionOnWalletWithDifferentCurrency(transaction)
                    else putTransactionOnWalletWithTheSameCurrency(transaction)
                }
    }

    private fun putTransactionOnWalletWithTheSameCurrency(transaction: Transaction): Completable {
        return walletRepository.getWalletById(transaction.walletId)
                .map { it.amount + transaction.amount }
                .flatMapCompletable { newBalance -> walletRepository.updateWalletBalance(transaction.walletId, newBalance) }
                .andThen { transactionsRepository.addTransaction(transaction) }
    }

    private fun putTransactionOnWalletWithDifferentCurrency(transaction: Transaction): Completable {
        return walletRepository.getWalletById(transaction.walletId)
                .flatMap { wallet -> currencyRateRepository.getExchangeRate(transaction.currency, wallet.majorCurrency) }
                .map { rate -> transaction.amount * rate }
                .flatMapCompletable { inc -> walletRepository.increaseWalletBalance(transaction.walletId, inc) }
                .andThen { transactionsRepository.addTransaction(transaction) }
    }

}