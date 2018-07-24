package ru.popov.bodya.howmoney.di.common.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.popov.bodya.core.dagger.ApplicationContext
import ru.popov.bodya.core.resources.ResourceManager
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