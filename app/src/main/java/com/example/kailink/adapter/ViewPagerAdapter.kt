package com.example.kailink.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kailink.ui.contacts.ContactsFragment
import com.example.kailink.ui.gallery.GalleryFragment
import com.example.kailink.ui.home.HomeFragment
import com.example.kailink.ui.chats.ChatsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ContactsFragment() // First tab
            2 -> GalleryFragment()  // Second tab
            3 -> ChatsFragment()   // Third tab
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}