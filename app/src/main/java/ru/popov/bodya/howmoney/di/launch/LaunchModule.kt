package ru.popov.bodya.howmoney.di.launch

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.howmoney.presentation.ui.launch.activities.LaunchActivity

/**
 * @author popovbodya
 */
@Module
interface LaunchModule {
    @ContributesAndroidInjector(modules = [LaunchFragmentsInjectorBuilders::class])
    fun provideOverviewActivity(): LaunchActivity
}
