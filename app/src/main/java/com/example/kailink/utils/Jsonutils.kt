package com.example.kailink.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.kailink.model.Contact
import com.example.kailink.model.Gallery
import org.json.JSONArray
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

    fun parseGalleryFromJson(jsonString: String?): List<Gallery> {
        val items = mutableListOf<Gallery>()
        try {
            if (!jsonString.isNullOrEmpty()) {
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val item = Gallery(
                        image = jsonObject.getString("building_image"),
                        galleryNum = jsonObject.getString("building_no"),
                        galleryName = jsonObject.getString("building_name"),
                        galleryAlias = jsonObject.getString("building_alias"),
                        latitude = jsonObject.getDouble("latitude"),
                        longitude = jsonObject.getDouble("longitude")
                    )
                    items.add(item)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return items
    }
}