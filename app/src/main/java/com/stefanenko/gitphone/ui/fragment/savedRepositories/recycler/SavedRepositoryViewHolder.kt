package com.stefanenko.gitphone.ui.fragment.savedRepositories.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.entity.RepositoryWithOwner
import kotlinx.android.synthetic.main.fragment_repositories.*
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlinx.android.synthetic.main.item_repository.view.descriptionText
import kotlinx.android.synthetic.main.item_repository.view.fillStar
import kotlinx.android.synthetic.main.item_repository.view.languageText
import kotlinx.android.synthetic.main.item_repository.view.repositoryNameText
import kotlinx.android.synthetic.main.item_saved_repository.view.*

class SavedRepositoryViewHolder(
    private val view: View,
    private val onStarClickListener: (RepositoryWithOwner) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val repositoryName = view.repositoryNameText
    private val projectLanguage = view.languageText
    private val projectDescription = view.descriptionText

    fun bind(item: RepositoryWithOwner) {

        with(item) {
            repositoryName.text = repoName
            projectLanguage.text = language
            projectDescription.text = description
            Glide.with(view.context).load(item.imageUrl).into(view.userPhotoUrl)
        }

        view.fillStar.setOnClickListener {
            onStarClickListener.invoke(item)
        }
    }
}