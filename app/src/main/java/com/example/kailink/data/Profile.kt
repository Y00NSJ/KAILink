package com.example.kailink.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var email: String,
    var profileImage: String? // Store image as a file path or Base64 string
)