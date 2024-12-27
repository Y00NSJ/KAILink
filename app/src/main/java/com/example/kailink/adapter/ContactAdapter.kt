package com.example.kailink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.model.Contact

class ContactAdapter(
    private val contactList: List<Contact>
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // ViewHolder class to hold the views for each item
    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.contact_name)
        val numberTextView: TextView = view.findViewById(R.id.contact_number)
        val addressTextView: TextView = view.findViewById(R.id.contact_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the item_contact layout for each item in the list
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // Bind the contact data to the views
        val contact = contactList[position]
        holder.nameTextView.text = contact.name
        holder.numberTextView.text = contact.phoneNumber
        holder.addressTextView.text = contact.address
    }

    override fun getItemCount(): Int {
        // Return the total number of items in the list
        return contactList.size
    }
}