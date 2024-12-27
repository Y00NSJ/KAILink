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

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up the Toolbar as the ActionBar
        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        // Load contacts from JSON in the raw directory
//        val jsonString = JsonUtils.loadJSONFromRaw(this, R.raw.contacts)
//        val contactList: List<Contact> = JsonUtils.parseContactsFromJson(jsonString)
//
//        // Set up RecyclerView
//        val recyclerView = binding.contactRecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = ContactAdapter(contactList)


        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_contact)
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
}