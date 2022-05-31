package com.zdran.photogallery.dager

import com.google.gson.GsonBuilder
import com.zdran.photogallery.PhotoDeserializer
import com.zdran.photogallery.api.PhotoResponse
import com.zdran.photogallery.bingApi.BingApi
import com.zdran.photogallery.bingApi.BingFetchr
import com.zdran.photogallery.bingApi.BingResponse
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BingApiModule {
    @Provides
    fun getBingApi(): BingApi {
        val gsonBuilder = GsonBuilder()
        val photoDeserializer = PhotoDeserializer()
        gsonBuilder.registerTypeAdapter(BingResponse::class.java, photoDeserializer)
        val gson = gsonBuilder.create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.bing.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(BingApi::class.java)
    }
}