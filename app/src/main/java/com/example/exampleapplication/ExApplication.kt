package com.example.exampleapplication

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class ExApplication: Application() {
    private val activityLifecycle = ApplicationActivityLifecycle()

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // register callback
        registerActivityLifecycleCallbacks(activityLifecycle)
        activityLifecycle.applicationStateForeground {
            Log.d("debug", "applicationStateForeground")
        }

        activityLifecycle.applicationStateBackground {
            Log.d("debug", "applicationStateBackground")
        }
    }

    companion object {
        private var instance: ExApplication? = null

        fun setAppStateListener(activity: Activity) {
            if (activity is AppStateListener) {
                instance?.activityLifecycle?.setAppStateListener(activity)
            }
        }

        fun getCurrentActivity(): Activity? {
            return instance?.activityLifecycle?.currentTopActivity
        }
    }
}