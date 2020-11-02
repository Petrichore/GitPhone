package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.ViewModelProvider
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import javax.inject.Inject

class SavedRepositoriesFragment: BaseObserveFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_repositories_list_cache

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SavedRepositoriesViewModel

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[SavedRepositoriesViewModel::class.java]
    }

    override fun observeViewModel() {
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, {singleEvent->
            singleEvent.handleEvent {
                showDebugLog("$it")
            }
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, {singleEvent->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSavedRepositories()
    }

    override fun setListeners() {}
}