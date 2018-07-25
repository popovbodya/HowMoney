package ru.popov.bodya.howmoney.di.common

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.popov.bodya.core.dagger.ApplicationContext
import ru.popov.bodya.howmoney.app.HowMoneyApp
import ru.popov.bodya.howmoney.di.common.ActivitiesBuildersModule
import ru.popov.bodya.howmoney.di.common.modules.NavigationModule
import ru.popov.bodya.howmoney.di.common.modules.ResourceModule
import ru.popov.bodya.howmoney.di.common.modules.RxModule
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    RxModule::class,
    NavigationModule::class,
    ActivitiesBuildersModule::class,
    ResourceModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(@ApplicationContext context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(howMoneyApp: HowMoneyApp)
}