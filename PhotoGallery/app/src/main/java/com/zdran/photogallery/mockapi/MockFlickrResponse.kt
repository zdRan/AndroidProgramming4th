package com.zdran.photogallery.mockapi

import com.google.gson.annotations.SerializedName

class MockFlickrResponse {
    @SerializedName("images")
    lateinit var galleryItems: List<MockGalleryItem>
}