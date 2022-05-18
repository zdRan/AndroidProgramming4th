package com.zdran.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zdran.photogallery.api.FlickrApi
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

class FlickrFetchr {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrRequest: Call<FlickrResponse> = flickrApi.fetchPhoto()
        flickrRequest.enqueue(object : Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var galleryItems: List<GalleryItem>? =
                    photoResponse?.galleryItems ?: mutableListOf()
                galleryItems = galleryItems?.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ", t)
                val galleryItem = GalleryItem("test","test","https://www.bing.com/th?id=OHR.SchlossGluecksburg_ZH-CN4079837227_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp")
                responseLiveData.value = mutableListOf(galleryItem)
            }
        })
        return responseLiveData
    }
}