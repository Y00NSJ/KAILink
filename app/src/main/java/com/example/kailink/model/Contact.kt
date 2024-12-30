package com.example.kailink.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kailink.data.BookmarkContact


data class Contact (
    @PrimaryKey val id: Int,
    val name: String,
    val phoneNumber: String,
    val address: String,
)