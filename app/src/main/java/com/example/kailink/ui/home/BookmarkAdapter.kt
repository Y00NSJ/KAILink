package com.example.kailink.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.data.BookmarkContact

class BookmarkAdapter(
    private var bookmarks: List<BookmarkContact>
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.bookmarkName)
        val phoneNumberTextView: TextView = view.findViewById(R.id.bookmarkPhoneNumber)
        val addressTextView: TextView = view.findViewById(R.id.bookmarkAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmarked_contact, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.nameTextView.text = bookmark.name
        holder.phoneNumberTextView.text = bookmark.phoneNumber
        holder.addressTextView.text = bookmark.address
    }

    override fun getItemCount(): Int = bookmarks.size

    fun updateData(newBookmarks: List<BookmarkContact>) {
        bookmarks = newBookmarks
        notifyDataSetChanged()
    }
}