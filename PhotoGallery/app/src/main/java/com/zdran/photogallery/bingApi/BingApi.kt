package com.zdran.photogallery.bingApi

import retrofit2.Call
import retrofit2.http.GET

interface BingApi {
    @GET("HPImageArchive.aspx?format=js&idx=0&n=3")
    fun fetchContents(): Call<String>



    @GET("HPImageArchive.aspx?format=js&idx=0&n=8")
    fun fetchPhoto(): Call<BingResponse>
}