package ru.popov.bodya.howmoney.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction

@Dao
interface TransactionsDao : BaseDao<Transaction> {
    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Single<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE amount>0")
    fun getAllIncomeTransactions(): Single<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE amount<0")
    fun getAllExpenseTransactions(): Single<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE walletId=:walletId")
    fun getAllTransactionsByWalletId(walletId: Int): Single<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE walletId=:walletId AND amount>0")
    fun getAllIncomeTransactionsByWalletId(walletId: Int): Single<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE walletId=:walletId AND amount<0")
    fun getAllExpenseTransactionsByWalletId(walletId: Int): Single<List<Transaction>>
}