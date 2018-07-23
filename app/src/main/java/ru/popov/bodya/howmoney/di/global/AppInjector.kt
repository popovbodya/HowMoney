package ru.popov.bodya.howmoney.di.global

import ru.popov.bodya.howmoney.app.HowMoneyApp

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