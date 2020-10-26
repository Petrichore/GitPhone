package com.stefanenko.gitphone.di

import android.app.Application
import com.stefanenko.gitphone.App
import com.stefanenko.gitphone.ui.activity.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ViewModelModule::class, DataModule::class])
interface AppComponent {

    fun inject(viewModel: MainViewModel)
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(app: Application): Builder
    }
}