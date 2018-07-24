package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.howmoney.domain.account.CurrencyInteractor

/**
 *  @author popovbodya
 */
@Module
class AccountDomainModule {

    @Provides
    fun provideAccountInteractor() = CurrencyInteractor()

}