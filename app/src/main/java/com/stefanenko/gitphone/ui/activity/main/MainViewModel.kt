package com.stefanenko.gitphone.ui.activity.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.stefanenko.gitphone.App
import javax.inject.Inject

class MainViewModel: ViewModel() {

    init{
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var appContext: Application
}