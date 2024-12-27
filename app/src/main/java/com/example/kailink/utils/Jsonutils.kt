package com.example.kailink.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.kailink.model.Contact
import java.io.IOException

object JsonUtils {

    // Function to read JSON from the raw directory
    fun loadJSONFromRaw(context: Context, resId: Int): String? {
        return try {
            val inputStream = context.resources.openRawResource(resId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
    // Function to parse contacts from JSON string
    fun parseContactsFromJson(jsonString: String?): List<Contact> {
        return if (!jsonString.isNullOrEmpty()) {
            val listType = object : TypeToken<List<Contact>>() {}.type
            Gson().fromJson(jsonString, listType)
        } else {
            emptyList()
        }
    }
}