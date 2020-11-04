package com.stefanenko.gitphone.ui.fragment.savedRepositories

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import com.stefanenko.gitphone.ui.base.decorators.VerticalItemDecoration
import com.stefanenko.gitphone.ui.fragment.savedRepositories.recycler.AdapterSavedRepositoryList
import com.stefanenko.gitphone.util.toDp
import kotlinx.android.synthetic.main.fragment_repositories_list_cache.*
import javax.inject.Inject

class SavedRepositoriesFragment : BaseObserveFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SavedRepositoriesViewModel

    private lateinit var recyclerSavedRepo: RecyclerView
    private lateinit var adapterSavedRepoList: AdapterSavedRepositoryList

    override fun getLayoutId(): Int = R.layout.fragment_repositories_list_cache

    override fun setListeners() {}

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SavedRepositoriesViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSavedRepositoriesWithUser()
    }

    override fun observeViewModel() {
        viewModel.repositoryListLiveData.observe(viewLifecycleOwner, { itemList ->
            Log.d("listUpdates", "$itemList")
            if (itemList.isNotEmpty()) {
                if (::recyclerSavedRepo.isInitialized) {
                    if (childFragmentManager.fragments.size != 0) deleteEmptyFragment()
                    adapterSavedRepoList.onDataSetChanged(itemList)
                } else {
                    initRecycler(itemList)
                }
            } else {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, EmptySavedRepositoriesFragment()).commit()
            }
        })

        viewModel.repoDeleteResponse.observe(viewLifecycleOwner, { deletedRepoId ->
            Log.d("Deleted repo id", "$deletedRepoId")
        })

//        viewModel.inProgress.observe(viewLifecycleOwner, {
//            if (it) {
//                progressBar.visibility = View.VISIBLE
//                inProgressView.visibility = View.VISIBLE
//            } else {
//                progressBar.visibility = View.GONE
//                inProgressView.visibility = View.GONE
//            }
//        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    private fun initRecycler(itemList: List<com.stefanenko.gitphone.domain.entity.RepositoryWithOwner>) {
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

    private fun deleteEmptyFragment() {
        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
    }
}