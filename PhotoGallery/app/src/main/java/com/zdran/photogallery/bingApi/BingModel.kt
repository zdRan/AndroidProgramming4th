package com.zdran.photogallery.bingApi

import android.util.Log
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class BingModel {
    @Provides
    @Singleton
    fun getApi(): BingApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.bing.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d("BingModel", "getApi: 执行~")
        return retrofit.create(BingApi::class.java)
    }

}