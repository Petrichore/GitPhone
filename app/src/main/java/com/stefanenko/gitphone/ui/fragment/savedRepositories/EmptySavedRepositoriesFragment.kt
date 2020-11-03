package com.stefanenko.gitphone.ui.fragment.savedRepositories

import android.os.Bundle
import android.view.View
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repositories_empty.*

class EmptySavedRepositoriesFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_saved_repository_empty

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setListeners() {}
}