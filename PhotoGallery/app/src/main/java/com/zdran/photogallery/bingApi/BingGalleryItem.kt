package com.zdran.photogallery.bingApi

class BingGalleryItem {
    var title: String = ""
    var hsh: String = ""
    var url: String = ""

    constructor(title: String, hsh: String, url: String) {
        this.title = title
        this.hsh = hsh
        this.url = url
    }
}