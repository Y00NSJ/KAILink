package com.example.kailink.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkContact (
    var name : String,
    var phoneNumber : String,
    var address : String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}