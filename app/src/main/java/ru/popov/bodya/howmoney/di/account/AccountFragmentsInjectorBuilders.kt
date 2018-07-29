package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.di.common.modules.SettingsModule
import ru.popov.bodya.howmoney.presentation.ui.about.fragments.AboutFragment
import ru.popov.bodya.howmoney.presentation.ui.budget.fragments.BudgetFragment
import ru.popov.bodya.howmoney.presentation.ui.enrollment.EnrollmentFragment
import ru.popov.bodya.howmoney.presentation.ui.expense.ExpenseFragment
import ru.popov.bodya.howmoney.presentation.ui.settings.fragments.SettingsFragment

/**
 *  @author popovbodya
 */
@Module
interface AccountFragmentsInjectorBuilders {

    @ContributesAndroidInjector
    fun provideAboutFragment(): AboutFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    fun provideSettingFragment(): SettingsFragment

    @ContributesAndroidInjector
    fun provideAccountFragment(): BudgetFragment

    @ContributesAndroidInjector
    fun provideEnrollmentFragment(): EnrollmentFragment

    @ContributesAndroidInjector
    fun provideExpenseFragment(): ExpenseFragment
}