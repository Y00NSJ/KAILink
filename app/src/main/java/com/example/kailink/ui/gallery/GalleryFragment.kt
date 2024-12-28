package com.example.kailink.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.adapter.ContactAdapter
import com.example.kailink.adapter.DashboardItem
import com.example.kailink.adapter.GalleryAdapter
import com.example.kailink.databinding.FragmentGalleryBinding
import com.example.kailink.model.Contact
import com.example.kailink.model.Gallery
import com.example.kailink.utils.JsonUtils

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
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2열 그리드

        val jsonString = JsonUtils.loadJSONFromRaw(requireContext(), R.raw.gallery)
        val galleryList: List<Gallery> = JsonUtils.parseGalleryFromJson(jsonString)




        recyclerView.adapter = GalleryAdapter(galleryList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}