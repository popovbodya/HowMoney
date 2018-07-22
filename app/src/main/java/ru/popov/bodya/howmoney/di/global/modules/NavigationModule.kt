package ru.popov.bodya.howmoney.di.global.modules

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
    internal fun provideCicerone(): Cicerone<out Router> {
        return Cicerone.create()
    }

    @Provides
    @Singleton
    internal fun provideRouter(cicerone: Cicerone<out Router>): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    internal fun provideNavigationHolder(cicerone: Cicerone<out Router>): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}