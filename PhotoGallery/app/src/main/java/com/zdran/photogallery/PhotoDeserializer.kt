package com.zdran.photogallery

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.zdran.photogallery.api.GalleryItem
import com.zdran.photogallery.api.PhotoResponse
import com.zdran.photogallery.bingApi.BingGalleryItem
import com.zdran.photogallery.bingApi.BingResponse
import java.lang.reflect.Type

class PhotoDeserializer : JsonDeserializer<BingResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BingResponse {
        Log.d("PhotoDeserializer", "deserialize: $json")
        val photoResponse = BingResponse()

        val jsonObj = json?.asJsonObject
        val galleryItemsJson = jsonObj?.get("images")?.asJsonArray

        if (galleryItemsJson != null) {
            val galleryItems: ArrayList<BingGalleryItem> = ArrayList()
            for (item in galleryItemsJson) {
                val itemObject = item.asJsonObject
                galleryItems.add(
                    BingGalleryItem(
                        itemObject.get("title").asString, itemObject.get("hsh").asString,
                        itemObject.get("url").asString
                    )
                )
            }
            photoResponse.galleryItems = galleryItems
        }
        return photoResponse
    }
}