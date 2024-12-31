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
import com.example.kailink.adapter.GalleryAdapter
import com.example.kailink.databinding.FragmentContactsBinding
import com.example.kailink.databinding.FragmentGalleryBinding
import com.example.kailink.model.Contact
import com.example.kailink.model.Gallery
import com.example.kailink.utils.JsonUtils

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jsonString = JsonUtils.loadJSONFromRaw(requireContext(), R.raw.gallery)
        val galleryList: List<Gallery> = JsonUtils.parseGalleryFromJson(jsonString)

        setupRecyclerView(galleryList)
        setupSearchView()
    }

    private fun setupRecyclerView(galleryList: List<Gallery>) {
        galleryAdapter = GalleryAdapter(galleryList)
        binding.galleryRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // 2열 그리드
            adapter = galleryAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false // 항상 확장 상태 유지
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Optional: Handle search submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                galleryAdapter.filter.filter(newText) // Dynamically filter the adapter
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}