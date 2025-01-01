package com.example.kailink.ui.home

import com.example.kailink.data.Profile
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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
    private var isEditing = false

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
        binding.BtnEditProfile.setOnClickListener {
            openGallery()
            loadProfile()
        }

        binding.clearButton.setOnClickListener {
            clearBookmarks()
        }

        val editUserName = view?.findViewById<EditText>(R.id.userName)
        binding.BtnEditName.setOnClickListener {
            showEditProfileDialog()
            loadProfile()
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
            //    binding.profileImage.setImageURI(imageUri)
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
//    private fun deleteProfile() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val db = AppDatabase.getInstance(requireContext())
//            val profileDao = db!!.profileDao()
//            var profile: Profile? = profileDao.getProfileById(1)
//            profileDao.updateProfile(profile = Profile(
//                name = profile!!.name,
//                email = profile.email,
//                profileImage = null
//                )
//            )
//
//        }
//    }

    private fun loadProfile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            val profileDao = db!!.profileDao()

            // Check if a profile exists
            var profile = profileDao.getProfileById(1)
            Log.d("DatabaseCheck", "Profiles: $profile")
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
    private fun saveUserNameToDatabase(newUserName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            val profileDao = db!!.profileDao()
            val profile = profileDao.getProfileById(1)

            profile?.let {
                it.name = newUserName // Update the name field
                profileDao.updateProfile(it)
            }

        }
    }
    private fun showEditProfileDialog() {

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_profile, null)
        val dialogBuilder = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val editName = dialogView.findViewById<EditText>(R.id.editProfileName)
        val editEmail = dialogView.findViewById<EditText>(R.id.editProfileEmail)
        val saveButton = dialogView.findViewById<ImageButton>(R.id.saveProfileButton)

        // Pre-fill existing profile data
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            val profileDao = db!!.profileDao()
            val profile = profileDao.getProfileById(1)

            withContext(Dispatchers.Main) {
                profile?.let {
                    editName.setText(it.name)
                    editEmail.setText(it.email)
                }
            }
        }

        // Save changes when the "Save" button is clicked
        saveButton.setOnClickListener {
            var newName = editName.text.toString().trim()
            var newEmail = editEmail.text.toString().trim()

            if (newName.isNotBlank() && newEmail.isNotBlank()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getInstance(requireContext())
                    val profileDao = db!!.profileDao()
                    val profile = profileDao.getProfileById(1)

                    profile?.let {
                        it.name = newName
                        it.email = newEmail
                        profileDao.updateProfile(it)
                    }

                    withContext(Dispatchers.Main) {
                        loadProfile() // Refresh the profile in the UI
                        dialog.dismiss()
                        Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Both fields must be filled!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}