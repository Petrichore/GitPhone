package com.stefanenko.gitphone

import android.app.Application
import com.stefanenko.gitphone.di.AppComponent
import com.stefanenko.gitphone.di.AppModule
import com.stefanenko.gitphone.di.DaggerAppComponent
import com.stefanenko.gitphone.di.DatabaseModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        DaggerAppComponent.builder().databaseModule(DatabaseModule(this)).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}