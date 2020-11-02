package com.stefanenko.gitphone.ui.fragment.savedRepositories.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

class AdapterSavedRepositoryList(
    private val itemList: List<GitRepository>,
    private val onStarClickListener: (GitRepository) -> Unit
): RecyclerView.Adapter<SavedRepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_repository, parent, false)
        return SavedRepositoryViewHolder(view, onStarClickListener)
    }

    override fun onBindViewHolder(holder: SavedRepositoryViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}