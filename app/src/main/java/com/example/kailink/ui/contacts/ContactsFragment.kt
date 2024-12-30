package com.example.kailink.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kailink.R
import com.example.kailink.adapter.ContactAdapter
import com.example.kailink.databinding.FragmentContactsBinding
import com.example.kailink.model.Contact
import com.example.kailink.utils.JsonUtils
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


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

    private fun setupRecyclerView(contactList: List<Contact>) {
        contactAdapter = ContactAdapter(
            contactList = contactList,
            onItemClick = { clickedContact ->
                val name = clickedContact.name ?: ""
                val phoneNumber = clickedContact.phoneNumber ?: ""
                val address = clickedContact.address ?: ""
                // 2) Show your dialog
                ContactDialogFragment.newInstance(name, phoneNumber, address)
                    .show(parentFragmentManager, "ContactDialog")
            },
        )

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
            .setPositiveButton("Close", null)
            .show()
    }
//    private fun toggleBookmark(contact: Contact) {
//        lifecycleScope.launch {
//            try {
//                if (contact.isBookmarked) {
//                    // Remove from database
//                    contact.isBookmarked = false
//                    bookmarkedDao.removeBookmark(
//                        Bookmarked(
//                            contactId = contact.id,
//                            name = contact.name,
//                            phoneNumber = contact.phoneNumber,
//                            address = contact.address
//                        )
//                    )
//                } else {
//                    // Add to database
//                    contact.isBookmarked = true
//                    bookmarkedDao.addBookmark(
//                        Bookmarked(
//                            contactId = contact.id,
//                            name = contact.name,
//                            phoneNumber = contact.phoneNumber,
//                            address = contact.address
//                        )
//                    )
//                }
//                contactAdapter.notifyDataSetChanged() // Update UI
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}