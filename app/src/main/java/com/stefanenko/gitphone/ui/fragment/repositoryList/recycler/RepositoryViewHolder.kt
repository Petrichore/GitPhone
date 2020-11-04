package com.stefanenko.gitphone.ui.fragment.repositoryList.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.entity.RepositoryLocal
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryViewHolder(
    private val view: View,
    private val onStarClickListener: (com.stefanenko.gitphone.domain.entity.RepositoryLocal) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val repositoryName = view.repositoryNameText
    private val projectLanguage = view.languageText
    private val projectDescription = view.descriptionText

    fun bind(item: com.stefanenko.gitphone.domain.entity.RepositoryLocal){

        with(item){
            repositoryName.text = name
            projectLanguage.text = language
            projectDescription.text = description
        }

        view.emptyStar.setOnClickListener {
            onStarClickListener.invoke(item)
        }
    }
}