package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.howmoney.data.repositories.CurrencyRateRepository
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.enrollment.interactors.EnrollmentInteractor
import ru.popov.bodya.howmoney.domain.expense.interactor.ExpenseInteractor
import ru.popov.bodya.howmoney.domain.launch.LaunchInteractor

/**
 *  @author popovbodya
 */
@Module
class AccountDomainModule {

    @Provides
    fun provideLaunchInteractor(currencyRateRepository: CurrencyRateRepository)  =
            LaunchInteractor(currencyRateRepository)

    @Provides
    fun provideEnrollmentInteractor(currencyRateRepository: CurrencyRateRepository, walletRepository: WalletRepository) =
            EnrollmentInteractor(currencyRateRepository, walletRepository)

    @Provides
    fun provideExpenseInteractor(currencyRateRepository: CurrencyRateRepository, walletRepository: WalletRepository) =
            ExpenseInteractor(currencyRateRepository, walletRepository)

}