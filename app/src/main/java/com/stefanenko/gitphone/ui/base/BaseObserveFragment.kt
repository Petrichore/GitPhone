package com.stefanenko.gitphone.ui.base

import android.os.Bundle
import android.view.View

abstract class BaseObserveFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
    }

    abstract fun initViewModel()
    abstract fun observeViewModel()
}