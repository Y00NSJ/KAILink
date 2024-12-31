package com.example.kailink.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kailink.data.BookmarkContact
import com.example.kailink.data.BookmarkContactDatabase
import com.example.kailink.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.kailink.data.BookmarkContactDao
import com.example.kailink.ui.home.BookmarkAdapter
import kotlinx.coroutines.CoroutineScope

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var bookmarkAdapter: BookmarkAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set click listener for the button
        binding.BtnProfile.setOnClickListener {
            openGallery()
        }
        setupRecyclerView()
        loadBookmarks()


        binding.clearButton.setOnClickListener {
            val db = BookmarkContactDatabase.getInstance(requireContext())
            CoroutineScope(Dispatchers.IO).launch {
                db?.bookmarkContactDao()?.clearAllBookmarks()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "All bookmarks cleared!", Toast.LENGTH_SHORT).show()
                    loadBookmarks()
                }
            }
        }
        return root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            // Set the selected image in the ImageView
            binding.profileImage.setImageURI(imageUri)
        }
    }
    private fun setupRecyclerView() {
        bookmarkAdapter = BookmarkAdapter(emptyList()) { bookmark ->
            deleteBookmark(bookmark) // Pass logic to delete bookmark
        }
        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookmarkRecyclerView.adapter = bookmarkAdapter
    }

    fun loadBookmarks() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = BookmarkContactDatabase.getInstance(requireContext())
            val bookmarks = db!!.bookmarkContactDao().getAll()
            withContext(Dispatchers.Main) {
                bookmarkAdapter.updateData(bookmarks)
            }
        }
    }

    private fun deleteBookmark(bookmark: BookmarkContact) {
        val db = BookmarkContactDatabase.getInstance(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.bookmarkContactDao().delete(bookmark)
            val updatedBookmarks = db.bookmarkContactDao().getAll()
            withContext(Dispatchers.Main) {
                bookmarkAdapter.updateData(updatedBookmarks)
                Toast.makeText(requireContext(), "Bookmark deleted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}