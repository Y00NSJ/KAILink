package com.example.kailink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.model.Contact
import com.example.kailink.model.Gallery

class GalleryAdapter(private val items: List<Gallery>) :
    RecyclerView.Adapter<GalleryAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView_card)
        val galleryNumTextView: TextView = view.findViewById(R.id.gallery_num)
        val galleryNameTextView: TextView = view.findViewById(R.id.gallery_name)
        val galleryAliasTextView: TextView = view.findViewById(R.id.gallery_alias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_card, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = items[position]

        val resourceId = holder.itemView.context.resources.getIdentifier(item.image, "drawable", holder.itemView.context.packageName)
        holder.imageView.setImageResource(resourceId)

        holder.galleryNumTextView.text = item.galleryNum
        holder.galleryNameTextView.text = item.galleryName
        holder.galleryAliasTextView.text = item.galleryAlias
    }

    override fun getItemCount(): Int = items.size
}

data class DashboardItem(val imageResId: Int, val galleryNum: String, val galleryName: String, val galleryAlias: String)
