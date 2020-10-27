package com.stefanenko.gitphone.ui.fragment.repositoryList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.ui.base.BaseFragment
import com.stefanenko.gitphone.ui.base.decorators.HorizontalItemDecoration
import com.stefanenko.gitphone.ui.fragment.repositoryList.recycler.AdapterRepositoryList
import com.stefanenko.gitphone.util.toDp
import kotlinx.android.synthetic.main.fragment_repositories.*

class RepositoryListFragment: BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_repositories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositoryList = RepositoryListFragmentArgs.fromBundle(requireArguments()).repositoryList.repositoryList
        setUiData(repositoryList)
    }

    private fun setUiData(repositoryList: List<GitRepository>){
        if(repositoryList.isNotEmpty()){
            with(repositoryList[0].repoOwnerGit){
                ownerNameText.text = ownerName
                Glide.with(requireContext()).load(avatarUrl).into(ownerImageView)
            }

            initRecycler(repositoryList)
        }else{
            //TODO navigate to empty screen
        }
    }

    private fun initRecycler(itemList: List<GitRepository>){
        with(repositoryRecycler){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterRepositoryList(itemList){
                //TODO onItemClickListener implementation
            }
            addItemDecoration(HorizontalItemDecoration(16.toDp()))
        }
    }

    override fun setListeners() {
    }
}