package com.zdran.photogallery.bingApi

import com.google.gson.annotations.SerializedName

class BingResponse {
    @SerializedName("images")
    lateinit var galleryItems: List<BingGalleryItem>
}