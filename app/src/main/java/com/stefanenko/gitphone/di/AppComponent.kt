package com.stefanenko.gitphone.di

import android.app.Application
import com.stefanenko.gitphone.App
import com.stefanenko.gitphone.ui.activity.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ViewModelModule::class,
    DataModule::class,
    FragmentModule::class
])
interface AppComponent {

    fun inject(app: App)
}