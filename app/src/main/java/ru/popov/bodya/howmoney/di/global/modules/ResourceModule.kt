package ru.popov.bodya.howmoney.di.global.modules

import android.content.Context
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import ru.popov.bodya.core.dagger.ApplicationContext
import ru.popov.bodya.core.resources.ResourceManager
import ru.popov.bodya.howmoney.app.HowMoneyApp
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Module
class ResourceModule {

    @Singleton
    @Provides
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)

}