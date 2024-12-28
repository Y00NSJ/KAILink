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

class ContactActivity : AppCompatActivity() {

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

//        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//
//        // Set up the ViewPager2 adapter
//        viewPager.adapter = ViewPagerAdapter(this)
//
//        // Link TabLayout and ViewPager2
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when (position) {
//                0 -> tab.text = "Contacts"
//                1 -> tab.text = "Gallery"
//                2 -> tab.text = "Mypage"
//            }
//        }.attach()
//        // Sync ViewPager2 with BottomNavigationView
//        navView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_contacts -> viewPager.currentItem = 0
//                R.id.navigation_dashboard -> viewPager.currentItem = 1
//                R.id.navigation_notifications -> viewPager.currentItem = 2
//                else -> false
//            }
//            true
//        }
//
//        // Sync BottomNavigationView with ViewPager2 swipes
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                when (position) {
//                    0 -> navView.menu.findItem(R.id.navigation_contacts).isChecked = true
//                    1 -> navView.menu.findItem(R.id.navigation_dashboard).isChecked = true
//                    2 -> navView.menu.findItem(R.id.navigation_notifications).isChecked = true
//                }
//            }
//        })
//    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.toolbar_menu, menu)
//        val searchItem = menu?.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.queryHint = "Search contacts"
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Handle search submission
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Filter the RecyclerView items dynamically
//                (binding.contactRecyclerView.adapter as ContactAdapter).filter.filter(newText)
//                return true
//            }
//        })
//        return true
//    }
}