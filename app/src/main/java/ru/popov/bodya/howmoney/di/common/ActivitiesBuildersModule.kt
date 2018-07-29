package ru.popov.bodya.howmoney.di.common

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.di.account.AccountDomainModule
import ru.popov.bodya.howmoney.di.account.AccountFragmentsInjectorBuilders
import ru.popov.bodya.howmoney.di.account.AccountRepositoryModule
import ru.popov.bodya.howmoney.presentation.ui.account.activities.AccountActivity

/**
 *  @author popovbodya
 */
@Module
interface ActivitiesBuildersModule {
    @ContributesAndroidInjector(modules = [
        AccountFragmentsInjectorBuilders::class,
        AccountDomainModule::class,
        AccountRepositoryModule::class]
    )
    fun provideOverviewActivity(): AccountActivity
}