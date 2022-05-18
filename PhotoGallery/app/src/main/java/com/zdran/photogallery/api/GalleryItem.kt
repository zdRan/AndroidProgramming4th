package com.zdran.photogallery.api

import com.google.gson.annotations.SerializedName

class GalleryItem {
    var title: String = ""
    var id: String = ""

    @SerializedName("url_s")
    var url: String = ""

    constructor(title: String, id: String, url: String) {
        this.title = title
        this.id = id
        this.url = url

    }
}