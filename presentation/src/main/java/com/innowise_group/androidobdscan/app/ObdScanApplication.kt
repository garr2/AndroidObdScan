package com.innowise_group.androidobdscan.app

import android.app.Application
import com.innowise_group.androidobdscan.injection.AppComponent
import com.innowise_group.androidobdscan.injection.AppModule
import com.innowise_group.androidobdscan.injection.DaggerAppComponent
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class ObdScanApplication: Application() {

    companion object {
        lateinit var instance: ObdScanApplication
        private set
        lateinit var appComponent: AppComponent
        private set
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
    }
}