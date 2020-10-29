package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import kotlinx.android.synthetic.main.fragment_start_screen.*
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
            singleEvent.handleEvent {
                findNavController().navigate(
                    StartScreenFragmentDirections.actionStartScreenFragmentToRepositoryListFragment(it)
                )
            }
        })

        viewModel.navigateToEmptyScreen.observe(viewLifecycleOwner, {singleEvent->
            singleEvent.handleEvent{
                findNavController().navigate(
                    StartScreenFragmentDirections.actionStartScreenFragmentToEmptyRepo(it)
                )
            }
        })

        viewModel.validationErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
           singleEvent.handleEvent {
               showDebugLog(it)
           }
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    override fun setListeners() {
        fetchRepoBtn.setOnClickListener {
            viewModel.fetchGitRepositoryList(usernameTextField.editText?.text.toString())
        }
    }
}