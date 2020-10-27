package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.localData.ParcelableGitRepositoryList
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import kotlinx.android.synthetic.main.fragment_start_screen.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartScreenFragment : BaseObserveFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: StartScreenViewModel

    override fun getLayoutId(): Int = R.layout.fragment_start_screen

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[StartScreenViewModel::class.java]
    }

    override fun observeViewModel() {
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, { singleEvent ->
            if (!singleEvent.isHandled) {
                findNavController().navigate(
                    StartScreenFragmentDirections.actionStartScreenFragmentToRepositoryListFragment(
                        singleEvent.getNotHandledContent()!!
                    )
                )
            }else{
                showDebugLog("Event have already handled")
            }
        })

        viewModel.validationErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            if (!singleEvent.isHandled) {
                showDebugLog(singleEvent.getNotHandledContent().toString())
            }
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            if (!singleEvent.isHandled) {
                showDebugLog(singleEvent.getNotHandledContent().toString())
            }
        })
    }

    override fun setListeners() {
        fetchRepoBtn.setOnClickListener {
            viewModel.fetchGitRepositoryList(usernameTextField.editText?.text.toString())
            findNavController().navigate(
                StartScreenFragmentDirections.actionStartScreenFragmentToRepositoryListFragment(
                    ParcelableGitRepositoryList(emptyList())
                )
            )
        }
    }
}