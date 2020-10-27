package com.stefanenko.gitphone.ui.fragment.repositoryList.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

class AdapterRepositoryList(
    private val itemList: List<GitRepository>,
    private val onItemClickListener: (GitRepository) -> Unit
): RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}