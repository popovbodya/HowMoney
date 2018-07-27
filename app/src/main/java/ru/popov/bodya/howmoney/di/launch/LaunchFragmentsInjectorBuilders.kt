package ru.popov.bodya.howmoney.di.launch

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.di.account.AccountDomainModule
import ru.popov.bodya.howmoney.di.account.AccountRepositoryModule
import ru.popov.bodya.howmoney.di.common.modules.SettingsModule
import ru.popov.bodya.howmoney.presentation.ui.about.fragments.AboutFragment
import ru.popov.bodya.howmoney.presentation.ui.account.fragments.AccountFragment
import ru.popov.bodya.howmoney.presentation.ui.settings.fragments.SettingsFragment

/**
 *  @author popovbodya
 */
@Module
interface LaunchFragmentsInjectorBuilders {

    @ContributesAndroidInjector
    fun provideAboutFragment(): AboutFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    fun provideSettingFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [
        AccountRepositoryModule::class,
        AccountDomainModule::class
    ])
    fun provideAccountFragment(): AccountFragment
}