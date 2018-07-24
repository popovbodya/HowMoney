package ru.popov.bodya.howmoney.di.common

import ru.popov.bodya.howmoney.app.HowMoneyApp
import ru.popov.bodya.howmoney.di.global.DaggerAppComponent

/**
 *  @author popovbodya
 */
object AppInjector {

    fun init(howMoneyApp: HowMoneyApp) {

        DaggerAppComponent
                .builder()
                .application(howMoneyApp)
                .appContext(howMoneyApp)
                .build()
                .inject(howMoneyApp)
    }
}