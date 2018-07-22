package ru.popov.bodya.howmoney.di.global

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.popov.bodya.howmoney.app.HowMoneyApp
import ru.popov.bodya.howmoney.di.global.modules.AppModule
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Singleton
@Component(modules = [
    AppModule::class,
    ActivitiesBuildersModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(howMoneyApp: HowMoneyApp)
}