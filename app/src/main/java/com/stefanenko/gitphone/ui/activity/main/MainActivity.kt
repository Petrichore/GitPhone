package com.stefanenko.gitphone.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.base.BaseActivity

class MainActivity: BaseActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun getContentLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.main_nav_host_fragment)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.e("App Context", "${viewModel.appContext}")
    }
}