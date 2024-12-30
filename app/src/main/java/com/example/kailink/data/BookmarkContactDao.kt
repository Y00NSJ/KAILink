package com.example.kailink.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkContactDao {
    @Query("SELECT COUNT(*) FROM BookmarkContact WHERE name = :name AND phoneNumber = :phoneNumber AND address = :address")
    fun isBookmarked(name: String, phoneNumber: String, address: String): Int

    @Query("DELETE FROM BookmarkContact")
    fun clearAllBookmarks()

    @Insert
    fun insert(bookmarkContact: BookmarkContact)

    @Delete
    fun delete(bookmarkContact: BookmarkContact)

    @Query("SELECT * FROM BookmarkContact")
    fun getAll(): List<BookmarkContact>

}