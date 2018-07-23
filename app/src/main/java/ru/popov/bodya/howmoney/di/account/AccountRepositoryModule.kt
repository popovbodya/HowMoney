package ru.popov.bodya.howmoney.di.account

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ru.popov.bodya.core.dagger.ApplicationContext
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.repositories.CurrencyRepository
import ru.popov.bodya.howmoney.di.global.modules.DataModule

/**
 *  @author popovbodya
 */
@Module(includes = [DataModule::class])
class AccountRepositoryModule {

    @Provides
    internal fun provideRepository(sharedPreferencesWrapper: SharedPreferencesWrapper): CurrencyRepository =
            CurrencyRepository(sharedPreferencesWrapper)
}