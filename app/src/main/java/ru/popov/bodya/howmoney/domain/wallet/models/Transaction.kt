package ru.popov.bodya.howmoney.domain.wallet.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                       val currency: Currency = Currency.RUB,
                       var amount: Double = 0.0,
                       val category: Category = Category.OTHER,
                       val walletId: Int = 0,
                       val comment: String = "",
                       val date: Date)

enum class Category {
    FOOD, CLOTHES, COMMUNAL_PAYMENTS, REST,
    EDUCATION, HOME, FAMILY, AUTO, TREATMENT, SALARY, OTHER
}

class TransactionCategoryConverter {

    @TypeConverter
    fun fromTransactionCategoryToInt(category: Category): Int =
            when (category) {
                Category.FOOD -> 0
                Category.HOME -> 1
                Category.COMMUNAL_PAYMENTS -> 2
                Category.EDUCATION -> 3
                Category.CLOTHES -> 4
                Category.FAMILY -> 5
                Category.REST -> 6
                Category.TREATMENT -> 7
                Category.AUTO -> 8
                Category.OTHER -> 9
                Category.SALARY -> 10
            }

    @TypeConverter
    fun fromIntToTransactionCategory(category: Int): Category =
            when (category) {
                0 -> Category.FOOD
                2 -> Category.HOME
                3 -> Category.COMMUNAL_PAYMENTS
                4 -> Category.EDUCATION
                5 -> Category.CLOTHES
                6 -> Category.FAMILY
                7 -> Category.REST
                8 -> Category.TREATMENT
                9 -> Category.OTHER
                10 -> Category.SALARY
                else -> Category.HOME
            }
}

class TimeStampConverter {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long) = Date(millisSinceEpoch)
}