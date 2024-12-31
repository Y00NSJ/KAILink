package com.example.kailink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Insert
    fun insertProfile(profile: Profile)

    @Query("SELECT * FROM Profile WHERE id = :id LIMIT 1")
    fun getProfileById(id: Int): Profile?

    @Update
    fun updateProfile(profile: Profile)
}