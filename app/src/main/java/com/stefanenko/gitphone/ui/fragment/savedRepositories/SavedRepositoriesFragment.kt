package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
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
                if (it.isNotEmpty()) {
                    initRecycler(it)
                }
            }
        })

        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })
    }

    private fun initRecycler(itemList: List<RepositoryOwner>) {
        with(savedRepoRecycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(VerticalItemDecoration(16.toDp()))
            //TODO change to suitable list
            adapter = AdapterSavedRepositoryList(itemList) {
                //TODO implements onStarClickListener - delete repo from database
            }
        }
    }

    override fun setListeners() {}
}