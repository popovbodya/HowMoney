package ru.popov.bodya.howmoney.di.launch

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.presentation.ui.about.fragments.AboutFragment
import ru.popov.bodya.howmoney.presentation.ui.account.fragments.AccountFragment

/**
 *  @author popovbodya
 */
@Module
interface LaunchFragmentsInjectorBuilders {

    @ContributesAndroidInjector
    fun provideAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    fun provideAccountFragment(): AccountFragment
}