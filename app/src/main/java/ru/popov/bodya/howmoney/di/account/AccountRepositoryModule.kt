package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.repositories.CurrencyRepository
import ru.popov.bodya.howmoney.di.global.modules.DataModule

/**
 *  @author popovbodya
 */
@Module(includes = [DataModule::class])
class AccountRepositoryModule {

    @Provides
    fun provideRepository(sharedPreferencesWrapper: SharedPreferencesWrapper): CurrencyRepository =
            CurrencyRepository(sharedPreferencesWrapper)
}