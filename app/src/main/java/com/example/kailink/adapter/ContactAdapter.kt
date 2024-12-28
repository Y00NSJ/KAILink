package com.example.kailink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.model.Contact
import android.widget.Filter

class ContactAdapter(
    private val contactList: List<Contact>,
    private val onItemClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), Filterable {
    private var filteredContactList = contactList.toMutableList()

    // ViewHolder class to hold the views for each item
//    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val nameTextView: TextView = view.findViewById(R.id.contact_name)
//        val numberTextView: TextView = view.findViewById(R.id.contact_number)
//        val addressTextView: TextView = view.findViewById(R.id.contact_address)
//    }
    class ContactViewHolder(
        itemView: View,
        private val onItemClick: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        // View references
        val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        val numberTextView: TextView = itemView.findViewById(R.id.contact_number)
        val addressTextView: TextView = itemView.findViewById(R.id.contact_address)

        fun bind(contact: Contact) {
            // Bind the contact data
            nameTextView.text = contact.name
            numberTextView.text = contact.phoneNumber
            addressTextView.text = contact.address

            // Entire row clickable
            itemView.setOnClickListener {
                onItemClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the item_contact layout for each item in the list
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, onItemClick)
    }
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // Bind the contact data to the views
        val contact = filteredContactList[position]
        holder.bind(contact)
        holder.nameTextView.text = contact.name
        holder.numberTextView.text = contact.phoneNumber
        holder.addressTextView.text = contact.address
    }

    override fun getItemCount(): Int {
        // Return the total number of items in the list
        return filteredContactList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    contactList
                } else {
                    contactList.filter {
                        it.name.lowercase().contains(query) ||
                                it.phoneNumber.contains(query) ||
                                it.address.lowercase().contains(query)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredContactList = results?.values as MutableList<Contact>
                notifyDataSetChanged()
            }
        }
    }
}