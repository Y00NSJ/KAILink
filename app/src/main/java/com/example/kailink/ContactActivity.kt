package com.example.kailink

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kailink.databinding.ActivityContactBinding
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kailink.model.Contact
import com.example.kailink.adapter.ContactAdapter
import com.example.kailink.utils.JsonUtils
import androidx.appcompat.widget.SearchView
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.example.kailink.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.kailink.ui.contacts.ContactsDialogFragment

class ContactActivity : AppCompatActivity(), ContactsDialogFragment.OnPlaceButtonClickListener {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up the Toolbar as the ActionBar
        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navView: BottomNavigationView = binding.navView
     //   val navController = findNavController(R.id.nav_host_fragment_activity_contact)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_contact) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_contacts,
                R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onPlaceButtonClicked() {
        // Simulate navigation to the Gallery tab
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.selectedItemId = R.id.navigation_dashboard // Replace with the actual ID for the Gallery tab
    }

}