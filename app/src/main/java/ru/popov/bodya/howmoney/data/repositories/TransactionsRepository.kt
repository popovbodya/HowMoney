package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Completable
import ru.popov.bodya.howmoney.data.database.dao.TransactionsDao
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction

class TransactionsRepository(private val transactionsDao: TransactionsDao) {
    fun addTransaction(transaction: Transaction)
            = Completable.fromAction { transactionsDao.insert(transaction) }

    fun addTransactions(transactions: List<Transaction>)
            = Completable.fromAction { transactionsDao.insertAll(transactions) }

    fun getAllTransactions()
            = transactionsDao.getAllTransactions()

    fun getAllIncomeTransactions()
            = transactionsDao.getAllIncomeTransactions()

    fun getAllExpenseTransactions()
            = transactionsDao.getAllExpenseTransactions()

    fun getAllTransactionsByWallet(walletId: Int)
            = transactionsDao.getAllTransactionsByWalletId(walletId)

    fun getAllIncomeTransactionsByWallet(walletId: Int)
            = transactionsDao.getAllIncomeTransactionsByWalletId(walletId)

    fun getAllExpenseTransactionsByWallet(walletId: Int)
            = transactionsDao.getAllExpenseTransactionsByWalletId(walletId)

    fun deleteTransaction(transaction: Transaction)
            = Completable.fromAction { transactionsDao.delete(transaction) }

    fun updateTransaction(transaction: Transaction)
            = Completable.fromAction { transactionsDao.update(transaction) }
}