package com.stefanenko.gitphone

import android.app.Application
import com.stefanenko.gitphone.di.AppComponent
import com.stefanenko.gitphone.di.AppModule
import com.stefanenko.gitphone.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    companion object{
        lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
    }

    private fun initDagger(): AppComponent{
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}