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

/**
 *  @author popovbodya
 */
class EnrollmentInteractor(private val currencyRateRepository: CurrencyRateRepository,
                           private val walletRepository: WalletRepository) {

    fun calculateBalanceOfEnrollmentList(categoryList: List<EnrollmentCategoryBalance>): Single<Double> {
        return Observable.fromIterable(categoryList)
                .reduce(0.0) { acc, balance -> acc + balance.amount }
    }

    fun getEnrollmentOperationList(wallet: Wallet): Single<List<EnrollmentCategoryBalance>> {

        val operations = Observable.fromIterable(walletRepository.getEnrollmentOperationList(wallet))
        val categoryGroupsKeys: Observable<EnrollmentCategory> = operations.map { it.enrollmentCategory }
        val categoryAmounts: Observable<Double> = operations.flatMap { getOperationAmount(it) }

        return categoryAmounts.zipWith(categoryGroupsKeys) { amount, key ->
            Observable.just(EnrollmentCategoryBalance(key, amount))
                    .groupBy { it.enrollmentCategory }
                    .flatMap { group -> group.reduce(0.0) { acc, balance -> acc + balance.amount }.toObservable() }
                    .map { EnrollmentCategoryBalance(key, it) }
        }
                .flatMap { it }
                .toList()
    }

    private fun getOperationAmount(enrollmentOperation: EnrollmentOperation): Observable<Double> {
        return when (enrollmentOperation.currency) {
            Currency.USD -> currencyRateRepository.getCachedExchangeRate().map { rate: Double ->
                rate * enrollmentOperation.amount
            }.toObservable()
            Currency.RUB -> Observable.just(enrollmentOperation.amount)
        }
    }

}