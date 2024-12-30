package com.example.kailink.ui.contacts

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.kailink.R
import com.example.kailink.data.BookmarkContact
import com.example.kailink.data.BookmarkContactDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.kailink.ui.home.HomeFragment


class ContactDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the custom layout for this dialog
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_contact, null)
        val name = arguments?.getString("name_key") ?: "Unknown Name"
        val phoneNumber = arguments?.getString("phone_key") ?: "Unknown Phone Number"
        val address = arguments?.getString("address_key") ?: "Unknown Address"
        // Find buttons in the layout
        val nameTextView = view.findViewById<TextView>(R.id.contactNameTextView)
        val phoneTextView = view.findViewById<TextView>(R.id.contactPhoneTextView)
        val addressTextView = view.findViewById<TextView>(R.id.contactAddressTextView)

        nameTextView.text = name
        phoneTextView.text = phoneNumber
        addressTextView.text = address

        val callButton = view.findViewById<Button>(R.id.callButton)
        val bookmarkButton = view.findViewById<Button>(R.id.bookmarkButton)

        callButton.setOnClickListener {
            // If phoneNumber is null or blank, handle gracefully
            if (!phoneNumber.isNullOrBlank()) {
                val dialIntent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$phoneNumber")
                )
                startActivity(dialIntent)
            } else {
                Toast.makeText(requireContext(), "No valid phone number.", Toast.LENGTH_SHORT).show()
            }
        }

        bookmarkButton.setOnClickListener {
            var BookmarkedContact = BookmarkContact(name , phoneNumber, address)
            val db = BookmarkContactDatabase.getInstance(requireContext())
            CoroutineScope(Dispatchers.IO).launch {
                val isBookmarked = db!!.bookmarkContactDao().isBookmarked(name, phoneNumber, address) > 0
                if (isBookmarked) {
                    // If bookmarked, delete it
                    db!!.bookmarkContactDao().delete(BookmarkedContact)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Bookmark removed!", Toast.LENGTH_SHORT).show()
                        (parentFragmentManager.findFragmentByTag("HomeFragment") as? HomeFragment)?.loadBookmarks()
                    }
                } else {
                    // If not bookmarked, insert it
                    db!!.bookmarkContactDao().insert(BookmarkedContact)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Contact bookmarked!", Toast.LENGTH_SHORT).show()
                        (parentFragmentManager.findFragmentByTag("HomeFragment") as? HomeFragment)?.loadBookmarks()
                    }
                }
            }
        }



//        val placeButton = view.findViewById<Button>(R.id.placeButton)
//
//        placeButton.setOnClickListener{
//            listener?.onPlaceButtonClicked() // Notify the Activity
//            dismiss() // Close the dialog
//        }

        // Build the AlertDialog using the inflated view
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Close") { _, _ -> }
            .create()
    }
//    override fun onAttach(context: android.content.Context) {
//        super.onAttach(context)
//        if (context is OnPlaceButtonClickListener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement OnPlaceButtonClickListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }


    companion object {
        // If you need to pass arguments, define newInstance(...) accordingly
        fun newInstance(name: String, phoneNumber: String, address: String): ContactDialogFragment {
            val fragment = ContactDialogFragment()
            val bundle = Bundle()
            bundle.putString("name_key", name)
            bundle.putString("phone_key", phoneNumber)
            bundle.putString("address_key", address)
            fragment.arguments = bundle
            return fragment
        }
    }
}