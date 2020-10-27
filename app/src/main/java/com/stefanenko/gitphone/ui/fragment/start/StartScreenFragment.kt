package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_start_screen.*
import kotlinx.coroutines.launch
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
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, {
            showDebugLog(it.toString())
        })

        viewModel.validationErrorLiveData.observe(viewLifecycleOwner, {
            showDebugLog(it)
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, {
            showDebugLog(it)
        })
    }

    override fun setListeners() {
        fetchRepoBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.fetchGitRepositoryList(usernameTextField.editText?.text.toString())
            }
        }
    }
}