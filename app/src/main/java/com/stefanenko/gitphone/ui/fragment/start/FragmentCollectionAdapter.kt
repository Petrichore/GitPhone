package com.stefanenko.gitphone.ui.fragment.start

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.stefanenko.gitphone.ui.base.BaseFragment
import com.stefanenko.gitphone.ui.fragment.savedRepositories.SavedRepositoriesFragment
import com.stefanenko.gitphone.ui.fragment.enterToRepository.GitUsernameFragment
import java.lang.Exception

class FragmentCollectionAdapter(fragment: BaseFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GitUsernameFragment()
            1 -> SavedRepositoriesFragment()
            else -> throw Exception("NoSuchFragmentException")
        }
    }
}