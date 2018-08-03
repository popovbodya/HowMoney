package ru.popov.bodya.howmoney.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.popov.bodya.howmoney.data.database.dao.ExchangeRateDao
import ru.popov.bodya.howmoney.data.database.dao.TransactionsDao
import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.domain.wallet.models.*

@TypeConverters(TimeStampConverter::class, CurrencyTypeConverter::class,
        TransactionCategoryConverter::class,
        WalletTypeConverter::class)
@Database(entities = [Transaction::class, Wallet::class, ExchangeRate::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionsDao: TransactionsDao
    abstract val walletDao: WalletDao
    abstract val exchangeRateDao: ExchangeRateDao
}