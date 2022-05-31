package com.zdran.photogallery.dager

import com.zdran.photogallery.bingApi.BingApi
import com.zdran.photogallery.bingApi.BingFetchr
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BingApiModule {
    @Provides
    fun getBingApi(): BingApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.bing.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BingApi::class.java)
    }
}