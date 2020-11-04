package com.stefanenko.gitphone.ui.fragment.savedRepositories.recycler

import androidx.recyclerview.widget.DiffUtil
import com.stefanenko.gitphone.domain.entity.RepositoryWithOwner

class DiffUtilSavedRepoCallBack(
    private val oldItemList: List<com.stefanenko.gitphone.domain.entity.RepositoryWithOwner>,
    private val newItemList: List<com.stefanenko.gitphone.domain.entity.RepositoryWithOwner>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemList.size

    override fun getNewListSize(): Int = newItemList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition].repoId == newItemList[newItemPosition].repoId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition] == newItemList[newItemPosition]
    }
}