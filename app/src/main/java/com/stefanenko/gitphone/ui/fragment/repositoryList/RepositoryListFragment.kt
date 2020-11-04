package com.stefanenko.gitphone.ui.fragment.repositoryList


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import com.stefanenko.gitphone.ui.base.decorators.VerticalItemDecoration
import com.stefanenko.gitphone.ui.fragment.repositoryList.recycler.AdapterRepositoryList
import com.stefanenko.gitphone.util.toDp
import kotlinx.android.synthetic.main.fragment_repositories.*
import javax.inject.Inject

class RepositoryListFragment : BaseObserveFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_repositories

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RepositoryListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositoryList =
            RepositoryListFragmentArgs.fromBundle(requireArguments()).userRepositories
        setUiData(repositoryList)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[RepositoryListViewModel::class.java]
    }

    override fun observeViewModel() {
        viewModel.successResponseLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog("Data have added")
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent {
                showDebugLog(it)
            }
        })

        viewModel.noOwnersForSavingRepository.observe(viewLifecycleOwner, { singleEvent ->
            singleEvent.handleEvent { eventData ->
                showAlertDialog("Owner missing", eventData.second, {
                    viewModel.addOwnerAndSavedRepository(
                        eventData.first,
                        RepositoryListFragmentArgs.fromBundle(requireArguments()).userRepositories
                    )
                    it.dismiss()
                }, {
                    it.dismiss()
                })
            }
        })
    }

    private fun setUiData(repositoryOwner: com.stefanenko.gitphone.domain.entity.RepositoryOwner) {
        ownerNameText.text = repositoryOwner.name
        Glide.with(requireContext()).load(repositoryOwner.imageUrl).into(ownerImageView)

        initRecycler(repositoryOwner.repositoryList)
    }

    private fun initRecycler(itemList: List<com.stefanenko.gitphone.domain.entity.RepositoryLocal>) {
        with(repositoryRecycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterRepositoryList(itemList) {
                showAlertDialog("Save repository", "Do you want to save ${it.name}",
                    { dialog ->
                        viewModel.saveRepository(
                            it,
                            RepositoryListFragmentArgs.fromBundle(requireArguments()).userRepositories.ownerId
                        )
                        dialog.dismiss()
                    }, { dialog ->
                        dialog.dismiss()
                    })
            }
            addItemDecoration(VerticalItemDecoration(16.toDp()))
        }
    }

    override fun setListeners() {}
}