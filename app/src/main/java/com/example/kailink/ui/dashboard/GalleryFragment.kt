package com.example.kailink.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.adapter.DashboardItem
import com.example.kailink.adapter.GalleryAdapter
import com.example.kailink.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_gallery, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.gallery_recycler_view)
        val dashboardItems = listOf(
            DashboardItem(R.drawable.image1, "Image 1"),
            DashboardItem(R.drawable.image2, "Image 2"),
            DashboardItem(R.drawable.image3, "Image 3"),
            DashboardItem(R.drawable.image4, "Image 4"),
            DashboardItem(R.drawable.image1, "Image 1"),
            DashboardItem(R.drawable.image2, "Image 2"),
            DashboardItem(R.drawable.image3, "Image 3"),
            DashboardItem(R.drawable.image4, "Image 4"),
            DashboardItem(R.drawable.image1, "Image 1"),
            DashboardItem(R.drawable.image2, "Image 2"),
            DashboardItem(R.drawable.image3, "Image 3"),
            DashboardItem(R.drawable.image4, "Image 4")
        )

        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2열 그리드
        recyclerView.adapter = GalleryAdapter(dashboardItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}