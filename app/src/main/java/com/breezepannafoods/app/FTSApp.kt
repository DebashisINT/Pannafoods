package com.breezepannafoods.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.breezepannafoods.R
import com.breezepannafoods.app.domain.CrashReportEntity
import com.breezepannafoods.app.utils.CustomExceptionHandler
import com.breezepannafoods.app.utils.FileLoggingTree
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import io.fabric.sdk.android.Fabric
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FTSApp : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    companion object {
        private var currentActivity: Activity? = null

        // Static method to retrieve the current activity
        fun getCurrentActivity(): Activity? {
            return currentActivity
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize other components
        AppDatabase.initAppDatabase(this)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        initTimber()

        // Initialize PreferenceHolder with the application context
        PreferenceHolder.setContext(applicationContext)

        // Set custom exception handler
        Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler(this))
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
        Timber.plant(FileLoggingTree())
    }
}
