package com.stefanenko.gitphone.ui.fragment.savedRepositories

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.domain.entity.RepositoryWithOwner
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import com.stefanenko.gitphone.ui.base.decorators.VerticalItemDecoration
import com.stefanenko.gitphone.ui.fragment.savedRepositories.recycler.AdapterSavedRepositoryList
import com.stefanenko.gitphone.util.toDp
import kotlinx.android.synthetic.main.fragment_repositories_list_cache.*
import javax.inject.Inject

class SavedRepositoriesFragment : BaseObserveFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_repositories_list_cache

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SavedRepositoriesViewModel

    private lateinit var recyclerSavedRepo: RecyclerView
    private lateinit var adapterSavedRepoList: AdapterSavedRepositoryList

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SavedRepositoriesViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSavedRepositoriesWithUser()
    }

    override fun observeViewModel() {
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                Log.d("listUpdates", "$it")
                if (it.isNotEmpty()) {
                    if (::recyclerSavedRepo.isInitialized) {
                        adapterSavedRepoList.onDataSetChanged(it)
                    } else {
                        initRecycler(it)
                    }
                } else {
                    findNavController().navigate(R.id.emptySavedRepositoriesFragment)
                }
            }
        })

        viewModel.repoDeleteResponse.observe(viewLifecycleOwner, { deletedRepoId ->

        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    private fun initRecycler(itemList: List<RepositoryWithOwner>) {
        recyclerSavedRepo = savedRepoRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration(16.toDp()))

            adapterSavedRepoList = AdapterSavedRepositoryList(itemList) { repository ->
                showAlertDialog(
                    "Delete repository",
                    "Are you sure you want to delete this repository from cache?",
                    {
                        viewModel.deleteSavedRepository(repository.repoId)
                        it.dismiss()
                    },
                    {
                        it.dismiss()
                    })
            }

            adapter = adapterSavedRepoList
        }
    }

    override fun setListeners() {}
}