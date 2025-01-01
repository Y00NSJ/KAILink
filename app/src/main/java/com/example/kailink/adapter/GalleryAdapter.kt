package com.example.kailink.adapter

import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kailink.R
import com.example.kailink.model.Gallery
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GalleryAdapter(private val items: List<Gallery>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(), Filterable {

    private var filteredGalleryList = items.toMutableList()

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView_card)
        val galleryNumTextView: TextView = view.findViewById(R.id.gallery_num)
        val galleryNameTextView: TextView = view.findViewById(R.id.gallery_name)
        val galleryAliasTextView: TextView = view.findViewById(R.id.gallery_alias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_card, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = filteredGalleryList[position]

        // setImageResource는 int를 받으므로 string인 이미지명을 int로 변환
        val resourceId = holder.itemView.context.resources.getIdentifier(item.image, "drawable", holder.itemView.context.packageName)
        Glide.with(holder.itemView.context)
            .load(resourceId) // Drawable 리소스 ID를 로드
            .placeholder(R.drawable.ic_loading) // 로드 중에 보여줄 기본 이미지
            .error(R.drawable.ic_error) // 로드 실패 시 보여줄 이미지
            .into(holder.imageView) // ImageView에 설정

        holder.galleryNumTextView.text = item.galleryNum
        holder.galleryNameTextView.text = item.galleryName
        holder.galleryAliasTextView.text = item.galleryAlias

        holder.itemView.setOnClickListener {
           showCustomDialog(holder.itemView.context, item)
        }
    }

    private fun showCustomDialog(context: Context, item: Gallery) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_gallery)

        val imageView = dialog.findViewById<ImageView>(R.id.image_dialog)
        val galleryNumTextView = dialog.findViewById<TextView>(R.id.bnum_dialog)
        val galleryNameTextView = dialog.findViewById<TextView>(R.id.name_dialog)
        val galleryAliasTextView = dialog.findViewById<TextView>(R.id.alias_dialog)
        val mapView = dialog.findViewById<MapView>(R.id.mapView)

        mapView.onCreate(null)
        mapView.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Add a marker and move the camera
                val position = LatLng(item.latitude, item.longitude)
                googleMap.addMarker(MarkerOptions().position(position).title(item.galleryName))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
            }
        })

        val resourceId = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        imageView.setImageResource(resourceId)
        galleryNumTextView.text = item.galleryNum
        galleryNameTextView.text = item.galleryName
        galleryAliasTextView.text = item.galleryAlias

        dialog.findViewById<ImageButton>(R.id.close_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<ImageButton>(R.id.share_button).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${item.galleryName} (${item.galleryNum})\n${item.galleryAlias}\nKAILink에서 자세한 정보를 확인하세요!\nhttps://play.google.com/store/apps/details?id=kailink")
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, "KAIST 건물 정보")
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            dialog.context.startActivity(shareIntent)
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        mapView.onResume()
        dialog.setOnDismissListener {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    override fun getItemCount(): Int = filteredGalleryList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.galleryNum.lowercase().contains(query) ||
                                it.galleryName.lowercase().contains(query) ||
                                it.galleryAlias.lowercase().contains(query)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredGalleryList = results?.values as MutableList<Gallery>
                notifyDataSetChanged()
            }
        }
    }
}

// data class DashboardItem(val imageResId: Int, val galleryNum: String, val galleryName: String, val galleryAlias: String)
