package com.example.kailink.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kailink.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.kailink.adapter.ViewPagerAdapter

class ContainerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout containing ViewPager2 and TabLayout
        val view = inflater.inflate(R.layout.fragment_container, container, false)

        // Find ViewPager2 and TabLayout in the layout
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)

        // Set up the ViewPager2 adapter
        viewPager.adapter = ViewPagerAdapter(requireActivity())

        // Link TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Contacts"
                2 -> tab.text = "Dashboard"
                3 -> tab.text = "Notifications"
            }
        }.attach()

        return view
    }
}