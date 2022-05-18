package com.zdran.photogallery.mockapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zdran.photogallery.api.FlickrResponse
import com.zdran.photogallery.api.GalleryItem
import com.zdran.photogallery.api.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickrFetchr"

class MockFlickrFetchr {
    private val mockFlickrApi: MockFlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.bing.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        mockFlickrApi = retrofit.create(MockFlickrApi::class.java)
    }

    fun fetchPhotos(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val flickrRequest: Call<String> = mockFlickrApi.fetchContents()
        flickrRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onFailure: ", t)
            }
        })
        return responseLiveData
    }
}