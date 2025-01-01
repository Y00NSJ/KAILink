package com.example.kailink.ui.contacts

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.kailink.R
import com.example.kailink.data.BookmarkContact
import com.example.kailink.data.AppDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.kailink.ui.home.HomeFragment


class ContactDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())

        // Inflate the custom layout for this dialog
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_contact, null)
        dialog.setContentView(view)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

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

        val callButton = view.findViewById<ImageButton>(R.id.callButton)
        val bookmarkButton = view.findViewById<ImageButton>(R.id.bookmarkButton)
        val closeButton = view.findViewById<ImageButton>(R.id.closeButton)

        callButton.setOnClickListener {
            // If phoneNumber is null or blank, handle gracefully
            if (!phoneNumber.isNullOrBlank()) {
                val dialIntent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$phoneNumber")
                )
                startActivity(dialIntent)
            } else {
                Toast.makeText(requireContext(), "유효하지 않은 전화번호입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        bookmarkButton.setOnClickListener {
    //        var BookmarkedContact = BookmarkContact(name , phoneNumber, address)
            val db = AppDatabase.getInstance(requireContext())
            CoroutineScope(Dispatchers.IO).launch {
                val existingContact = db!!.bookmarkContactDao()
                    .getContactByDetails(name, phoneNumber, address)
    //            val isBookmarked = db!!.bookmarkContactDao().isBookmarked(name, phoneNumber, address) > 0
                if (existingContact!=null) {
                    // If bookmarked, delete it
                    db.bookmarkContactDao().delete(existingContact)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Bookmark removed!", Toast.LENGTH_SHORT).show()
                        (parentFragmentManager.findFragmentByTag("HomeFragment") as? HomeFragment)?.loadBookmarks()
                    }
                } else {
                    // If not bookmarked, insert it
                    val newContact = BookmarkContact(name = name, phoneNumber = phoneNumber, address = address)
                    db.bookmarkContactDao().insert(newContact)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Contact bookmarked!", Toast.LENGTH_SHORT).show()
                        (parentFragmentManager.findFragmentByTag("HomeFragment") as? HomeFragment)?.loadBookmarks()
                    }
                }
            }
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

//        val placeButton = view.findViewById<Button>(R.id.placeButton)
//
//        placeButton.setOnClickListener{
//            listener?.onPlaceButtonClicked() // Notify the Activity
//            dismiss() // Close the dialog
//        }

        return dialog
    }



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