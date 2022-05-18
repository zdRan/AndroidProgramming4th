package com.zdran.photogallery.mockapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zdran.photogallery.api.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickrFetchr"

/**
 * 由于一些其他因素导致 flickr 网站无法访问，所以这里使用了必应的每日壁纸接口。
 */
class MockFlickrFetchr {
    private val mockFlickrApi: MockFlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.bing.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mockFlickrApi = retrofit.create(MockFlickrApi::class.java)
    }

    fun fetchPhotos(): LiveData<List<MockGalleryItem>> {
        val responseLiveData: MutableLiveData<List<MockGalleryItem>> = MutableLiveData()
        val flickrRequest: Call<MockFlickrResponse> = mockFlickrApi.fetchPhoto()
        flickrRequest.enqueue(object : Callback<MockFlickrResponse> {
            override fun onResponse(call: Call<MockFlickrResponse>, response: Response<MockFlickrResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                var mockGalleryItems: List<MockGalleryItem> = response.body()?.galleryItems ?: mutableListOf()
                mockGalleryItems = mockGalleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = mockGalleryItems
            }

            override fun onFailure(call: Call<MockFlickrResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ", t)
            }
        })
        return responseLiveData
    }
}