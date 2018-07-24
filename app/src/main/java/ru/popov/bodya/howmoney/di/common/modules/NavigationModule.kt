package ru.popov.bodya.howmoney.di.common.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Module
class NavigationModule {

    @Provides
    @Singleton
    internal fun provideCicerone(): Cicerone<out Router> = Cicerone.create()

    @Provides
    @Singleton
    internal fun provideRouter(cicerone: Cicerone<out Router>): Router = cicerone.router

    @Provides
    @Singleton
    internal fun provideNavigationHolder(cicerone: Cicerone<out Router>): NavigatorHolder = cicerone.navigatorHolder

}