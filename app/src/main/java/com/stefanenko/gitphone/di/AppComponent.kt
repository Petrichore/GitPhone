package com.stefanenko.gitphone.di

import com.stefanenko.gitphone.App
import com.stefanenko.gitphone.data.dependency.DatabaseModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)
}