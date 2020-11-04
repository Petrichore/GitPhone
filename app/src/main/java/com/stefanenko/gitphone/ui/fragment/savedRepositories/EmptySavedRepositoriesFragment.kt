package com.stefanenko.gitphone.ui.fragment.savedRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repositories_empty.*

class EmptySavedRepositoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_repository_empty, container, false)
    }
}