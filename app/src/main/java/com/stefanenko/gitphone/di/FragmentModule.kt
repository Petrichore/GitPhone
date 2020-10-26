package com.stefanenko.gitphone.di

import com.stefanenko.gitphone.ui.fragment.start.StartScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideStartScreenFragment(): StartScreenFragment
}