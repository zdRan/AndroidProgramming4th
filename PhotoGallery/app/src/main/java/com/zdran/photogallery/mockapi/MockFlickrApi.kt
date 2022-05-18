package com.zdran.photogallery.mockapi

import retrofit2.Call
import retrofit2.http.GET

interface MockFlickrApi {
    @GET("HPImageArchive.aspx?format=js&idx=0&n=3")
    fun fetchContents(): Call<String>
}