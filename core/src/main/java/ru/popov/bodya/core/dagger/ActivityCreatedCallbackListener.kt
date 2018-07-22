package ru.popov.bodya.core.dagger

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 *  @author popovbodya
 */
abstract class ActivityCreatedCallbackListener : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        onActivityCreated(activity)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    abstract fun onActivityCreated(activity: Activity)

}