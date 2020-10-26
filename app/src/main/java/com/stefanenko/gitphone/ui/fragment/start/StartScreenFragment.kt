package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.Observer
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

    override fun observeViewModel() {
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, Observer {
            showDebugLog(it.toString())
        })

        viewModel.validationErrorLiveData.observe(viewLifecycleOwner, Observer {
            showDebugLog(it)
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, Observer {
            showDebugLog(it)
        })
    }

}