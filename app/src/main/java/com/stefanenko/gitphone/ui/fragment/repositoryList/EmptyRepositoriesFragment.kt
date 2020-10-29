package com.stefanenko.gitphone.ui.fragment.repositoryList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repositories_empty.*

class EmptyRepositoriesFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_repositories_empty

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = EmptyRepositoriesFragmentArgs.fromBundle(requireArguments()).username
        setInfoText(username)
    }

    private fun setInfoText(username: String) {
        usernameText.text = username
    }

    override fun setListeners() {
        toStartScreenBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}