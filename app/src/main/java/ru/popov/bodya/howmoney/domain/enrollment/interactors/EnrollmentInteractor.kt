package ru.popov.bodya.howmoney.domain.enrollment.interactors

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategoryBalance
import ru.popov.bodya.howmoney.domain.operation.models.EnrollmentOperation
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import java.util.*
import java.util.concurrent.Callable
import java.util.function.BiFunction

/**
 *  @author popovbodya
 */
class EnrollmentInteractor(private val currencyRateRepository: CurrencyRateRepository,
                           private val walletRepository: WalletRepository) {

    fun calculateFinalEnrollmentBalance(enrollmentCategoryMap: Map<EnrollmentCategory, Double>): Single<Double> {
        return Observable.fromIterable(enrollmentCategoryMap.values)
                .reduce(0.0) { acc, balance -> acc + balance }
    }

    fun getEnrollmentCategoryMap(wallet: Wallet): Single<Map<EnrollmentCategory, Double>> {
        return Single.fromCallable { walletRepository.getEnrollmentOperationList(wallet) }.map { getCategoryBalanceMap(it) }
    }

    private fun getCategoryBalanceMap(enrollmentOperationList: List<EnrollmentOperation>): Map<EnrollmentCategory, Double> {
        val categoryBalanceMap = EnumMap<EnrollmentCategory, Double>(EnrollmentCategory::class.java)
        for (operation in enrollmentOperationList) {
            val currentAmount = categoryBalanceMap[operation.enrollmentCategory]
            if (currentAmount == null) {
                categoryBalanceMap[operation.enrollmentCategory] = getOperationAmount(operation)
            } else {
                categoryBalanceMap[operation.enrollmentCategory] = currentAmount + getOperationAmount(operation)
            }
        }
        return categoryBalanceMap
    }

    private fun getOperationAmount(enrollmentOperation: EnrollmentOperation): Double {
        return when (enrollmentOperation.currency) {
            Currency.USD -> currencyRateRepository.getCachedExchangeRate() * enrollmentOperation.amount
            Currency.RUB -> enrollmentOperation.amount
        }
    }

}