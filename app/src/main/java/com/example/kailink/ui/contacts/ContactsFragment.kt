package com.example.kailink.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
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

    private lateinit var contactAdapter: ContactAdapter

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

        setupRecyclerView(contactList)
        setupSearchView()

        return root
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Adjust padding for the activity's Toolbar
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
//
////        toolbar?.post {
////            val toolbarHeight = toolbar.height
////            val searchViewHeight = binding.searchView.height
////            // Apply padding to account for the Toolbar
////            binding.root.setPadding(
////                binding.root.paddingLeft,
////                toolbarHeight,
////                binding.root.paddingRight,
////                binding.root.paddingBottom
////            )
////        }
//    }
    private fun setupRecyclerView(contactList: List<Contact>) {
        contactAdapter = ContactAdapter(contactList) { clickedContact ->
            // 1) Extract or already have phoneNumber from the clickedContact
            val phoneNumber = clickedContact.phoneNumber ?: ""

            // 2) Show your dialog
            ContactsDialogFragment.newInstance(phoneNumber)
                .show(parentFragmentManager, "ContactDialog")
        }
        binding.contactRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Optional: Handle search submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                contactAdapter.filter.filter(newText) // Dynamically filter the adapter
                return true
            }
        })
    }
    private fun showContactDialog(contact: Contact) {
        // Simple AlertDialog or optional DialogFragment
        AlertDialog.Builder(requireContext())
            .setTitle(contact.name)
            .setMessage("Phone: ${contact.phoneNumber}\nAddress: ${contact.address}")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}