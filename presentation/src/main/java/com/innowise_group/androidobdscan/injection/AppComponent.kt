package com.innowise_group.androidobdscan.injection

import com.innowise_group.androidobdscan.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}