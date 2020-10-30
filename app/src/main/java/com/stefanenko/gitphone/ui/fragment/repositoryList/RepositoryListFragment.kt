package com.stefanenko.gitphone.ui.fragment.repositoryList

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseFragment
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import com.stefanenko.gitphone.ui.base.decorators.HorizontalItemDecoration
import com.stefanenko.gitphone.ui.base.decorators.VerticalItemDecoration
import com.stefanenko.gitphone.ui.fragment.repositoryList.recycler.AdapterRepositoryList
import com.stefanenko.gitphone.util.toDp
import kotlinx.android.synthetic.main.fragment_repositories.*
import kotlinx.android.synthetic.main.item_repository.*
import javax.inject.Inject

class RepositoryListFragment : BaseObserveFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_repositories

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RepositoryListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositoryList =
            RepositoryListFragmentArgs.fromBundle(requireArguments()).repositoryList.repositoryList
        setUiData(repositoryList)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[RepositoryListViewModel::class.java]
    }

    override fun observeViewModel() {
        viewModel.loadSuccessfullyLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                motionLayout.transitionToStart()
                showDebugLog("Data have added")
            }
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    private fun setUiData(repositoryList: List<GitRepository>) {
        with(repositoryList[0].repoOwnerGit) {
            ownerNameText.text = ownerName
            Glide.with(requireContext()).load(avatarUrl).into(ownerImageView)
        }

        initRecycler(repositoryList)
    }

    private fun initRecycler(itemList: List<GitRepository>) {
        with(repositoryRecycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterRepositoryList(itemList) {
                Log.d("On star click", "Yessss")
                showAlertDialog("Save repository", "Do you want to save ${it.repoName}",
                    { dialog ->
                        viewModel.cacheRepository(it)
                        dialog.dismiss()
                    }, { dialog ->
                        dialog.dismiss()
                    })
            }
            addItemDecoration(VerticalItemDecoration(16.toDp()))
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveAction: (dialog: DialogInterface) -> Unit,
        negativeAction: (dialog: DialogInterface) -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Save") { dialog, _ ->
                positiveAction.invoke(dialog)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                negativeAction.invoke(dialog)
            }
            .create()
            .show()
    }

    override fun setListeners() {}
}