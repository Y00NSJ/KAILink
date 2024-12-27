package com.example.kailink.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kailink.R
import com.example.kailink.adapter.ContactAdapter
import com.example.kailink.databinding.FragmentContactsBinding
import com.example.kailink.model.Contact
import com.example.kailink.utils.JsonUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.Toolbar

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load contacts from JSON in the raw directory
        val jsonString = JsonUtils.loadJSONFromRaw(requireContext(), R.raw.contacts)
        val contactList: List<Contact> = JsonUtils.parseContactsFromJson(jsonString)

        // Set up RecyclerView
        val recyclerView = binding.contactRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ContactAdapter(contactList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}