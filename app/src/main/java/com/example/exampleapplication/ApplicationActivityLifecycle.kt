package com.example.exampleapplication

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log

interface AppStateListener {
    fun enterForeground() {}
    fun enterBackground() {}
}

class ApplicationActivityLifecycle: ActivityLifecycleCallbacks {
    var currentTopActivity: Activity? = null
    set(value) {
        field = value
        if (value is AppStateListener) {
            if (!targets.contains(value)) {
                currentActivityTarget = value
            }
        }
    }

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    private var targets: ArrayList<AppStateListener> = ArrayList()
    private var currentActivityTarget: AppStateListener? = null

    private var foregroundAction: (() -> Unit)? = null
    private var backgroundAction: (() -> Unit)? = null

    fun applicationStateBackground(action: () -> Unit) {
        backgroundAction = action
    }

    fun applicationStateForeground(action: () -> Unit) {
        foregroundAction = action
    }

    // add target manually
    fun setAppStateListener(target: AppStateListener) {
        if (currentActivityTarget == target) {
            currentActivityTarget = null
        }
        if (!targets.contains(target)) {
            targets.add(target)
        }
        Log.d("debug", targets.toString())
    }

    // ActivityLifecycleCallbacks
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentTopActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        currentTopActivity = activity
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // enter foreground
            foregroundAction?.let { it() }
            currentActivityTarget?.enterForeground()
            for (target in targets) {
                target.enterForeground()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        currentTopActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        currentTopActivity = activity
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations;
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // enter background
            backgroundAction?.let { it() }
            currentActivityTarget?.enterBackground()
            for (target in targets) {
                target.enterBackground()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivityTarget == activity) {
            currentActivityTarget = null
        } else {
            if ((activity is AppStateListener) && targets.contains(activity)) {
                targets.remove(activity)
            }
        }
    }
}