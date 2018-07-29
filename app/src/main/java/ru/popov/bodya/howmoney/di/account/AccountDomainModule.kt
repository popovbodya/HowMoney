package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.enrollment.interactors.EnrollmentInteractor

/**
 *  @author popovbodya
 */
@Module
class AccountDomainModule {

    @Provides
    fun provideAccountInteractor(currencyRateRepository: CurrencyRateRepository, walletRepository: WalletRepository) =
            EnrollmentInteractor(currencyRateRepository, walletRepository)

}