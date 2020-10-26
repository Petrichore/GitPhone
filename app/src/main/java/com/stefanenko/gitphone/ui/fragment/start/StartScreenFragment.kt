package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.ViewModelProvider
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseFragment
import javax.inject.Inject

class StartScreenFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: StartScreenViewModel

    override fun getLayoutId(): Int = R.layout.fragment_start_screen

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[StartScreenViewModel::class.java]
    }

}