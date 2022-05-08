package com.zdran.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {
    @GET("/")
    fun fetchContents(): Call<String>

    @GET("service/rest/?method=method=flickr.interestingness.getList&api_key=_yourApiKeyHere_&format=json&nojsoncallback=1&extras=url_s")
    fun fetchPhoto(): Call<String>
}