package com.stefanenko.gitphone.ui.fragment.start

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.stefanenko.gitphone.R
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.base.BaseFragment
import com.stefanenko.gitphone.ui.base.BaseObserveFragment
import kotlinx.android.synthetic.main.fragment_start_screen.*
import javax.inject.Inject

class StartScreenFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_start_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewPager.adapter = FragmentCollectionAdapter(this)
        TabLayoutMediator(tabLayout, fragmentViewPager) { tab, position ->
            tab.text = if (position == 0) "Git" else "Saved repositories"
        }.attach()
    }

    override fun setListeners() {}
}