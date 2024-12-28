package com.example.kailink.ui.contacts

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.kailink.R

class ContactDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the custom layout for this dialog
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_contact, null)

        val phoneNumber = arguments?.getString("phone_key")
        // Find buttons in the layout

        val callButton = view.findViewById<Button>(R.id.callButton)

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

        // Build the AlertDialog using the inflated view
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Close") { _, _ -> }
            .create()
    }

    companion object {
        // If you need to pass arguments, define newInstance(...) accordingly
        fun newInstance(phoneNumber: String): ContactDialogFragment {
            val fragment = ContactDialogFragment()
            val bundle = Bundle()
            bundle.putString("phone_key", phoneNumber)
            fragment.arguments = bundle
            return fragment
        }
    }
}