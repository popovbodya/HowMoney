package ru.popov.bodya.howmoney.di.overview.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.di.overview.OverviewScope
import ru.popov.bodya.howmoney.presentation.ui.overview.activities.OverviewActivity
import javax.inject.Singleton

/**
 * @author popovbodya
 */
@Module
interface OverviewModule {
    @ContributesAndroidInjector
    fun provideOverviewActivity(): OverviewActivity
}
