package ru.popov.bodya.howmoney.di.global

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import ru.popov.bodya.core.dagger.ActivityCreatedCallbackListener
import ru.popov.bodya.core.dagger.Injectable
import ru.popov.bodya.howmoney.app.HowMoneyApp

/**
 *  @author popovbodya
 */
object AppInjector {

    fun init(howMoneyApp: HowMoneyApp) {

        DaggerAppComponent
                .builder()
                .application(howMoneyApp)
                .build()
                .inject(howMoneyApp)

        howMoneyApp.registerActivityLifecycleCallbacks(object : ActivityCreatedCallbackListener() {
            override fun onActivityCreated(activity: Activity) {
                obtainActivityCreatedCallback(activity)
            }

        })
    }

    private fun obtainActivityCreatedCallback(activity: Activity) {
        when (activity) {
            is Injectable ->  AndroidInjection.inject(activity)
            is HasSupportFragmentInjector -> AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity
                    .supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(fm: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) {
                            if (fragment is Injectable) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }
                    }, true)
        }
    }
}