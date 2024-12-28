package com.example.kailink.ui.contacts

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.kailink.R


class ContactsDialogFragment : DialogFragment() {
    private var listener: OnPlaceButtonClickListener? = null

    interface OnPlaceButtonClickListener {
        fun onPlaceButtonClicked()
    }

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

        val placeButton = view.findViewById<Button>(R.id.placeButton)

        placeButton.setOnClickListener{
            listener?.onPlaceButtonClicked() // Notify the Activity
            dismiss() // Close the dialog
        }

        // Build the AlertDialog using the inflated view
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Close") { _, _ -> }
            .create()
    }
    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is OnPlaceButtonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnPlaceButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        // If you need to pass arguments, define newInstance(...) accordingly
        fun newInstance(phoneNumber: String): ContactsDialogFragment {
            val fragment = ContactsDialogFragment()
            val bundle = Bundle()
            bundle.putString("phone_key", phoneNumber)
            fragment.arguments = bundle
            return fragment
        }
    }
}