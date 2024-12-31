package com.example.kailink.ui.home

import com.example.kailink.data.Profile
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kailink.R
import com.example.kailink.data.BookmarkContact
import com.example.kailink.data.AppDatabase
import com.example.kailink.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import java.io.File
import java.io.FileOutputStream

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


        setupRecyclerView()
        loadBookmarks()
        loadProfile()
        // Set click listener for the button
        binding.BtnProfile.setOnClickListener {
            openGallery()
            loadProfile()
        }


        binding.clearButton.setOnClickListener {
            clearBookmarks()
        }
        return root
    }
    private fun clearBookmarks(){
        val db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            db?.bookmarkContactDao()?.clearAllBookmarks()
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "All bookmarks cleared!", Toast.LENGTH_SHORT).show()
                loadBookmarks()
            }
        }
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
            if (imageUri != null) {
                // Save the image to internal storage
                val imagePath = saveImageToInternalStorage(imageUri)

                // Update the profile in the database
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getInstance(requireContext())
                    val profileDao = db!!.profileDao()
                    val profile = profileDao.getProfileById(1)

                    profile?.let {
                        it.profileImage = imagePath // Update the image path
                        profileDao.updateProfile(it)
                    }
                }

                // Update the UI
                binding.profileImage.setImageURI(imageUri)
            }
        }
    }
    private fun saveImageToInternalStorage(imageUri: Uri): String {
        val context = requireContext()
        val fileName = "profile_image.png"
        val file = File(context.filesDir, fileName)

        context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file.absolutePath // Return the path to save in the database
    }

    private fun loadProfile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            val profileDao = db!!.profileDao()

            // Check if a profile exists
            var profile = profileDao.getProfileById(1)
            if (profile == null) {
                // Insert a default profile
                profile = Profile(
                    name = "Default User",
                    email = "user@example.com",
                    profileImage = null // No profile image initially
                )
                profileDao.insertProfile(profile)
            }

            // Load the profile to the UI
            withContext(Dispatchers.Main) {
                binding.userName.setText(profile.name)
                binding.userEmail.setText(profile.email)
                if (!profile.profileImage.isNullOrEmpty()) {
                    Glide.with(this@HomeFragment)
                        .load(File(profile.profileImage)) // Load the saved image path
                        .circleCrop() // Make the image circular
                        .into(binding.profileImage) // Target ImageView
                } else {
                    binding.profileImage.setImageResource(R.drawable.ic_user_placeholder)
                }
            }
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
            val db = AppDatabase.getInstance(requireContext())
            val bookmarks = db!!.bookmarkContactDao().getAll()
            withContext(Dispatchers.Main) {
                bookmarkAdapter.updateData(bookmarks)
            }
        }
    }

    private fun deleteBookmark(bookmark: BookmarkContact) {
        val db = AppDatabase.getInstance(requireContext())
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