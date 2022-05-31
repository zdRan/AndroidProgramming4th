package com.zdran.photogallery.bingApi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "BingFetchr"

/**
 * 由于一些其他因素导致 flickr 网站无法访问，所以这里使用了必应的每日壁纸接口。
 */
class BingFetchr @Inject constructor(private val bingApi: BingApi) {

    private lateinit var bingRequest: Call<BingResponse>
    fun fetchPhotos(): LiveData<List<BingGalleryItem>> {
        val responseLiveData: MutableLiveData<List<BingGalleryItem>> = MutableLiveData()
        bingRequest = bingApi.fetchPhoto()
        bingRequest.enqueue(object : Callback<BingResponse> {
            override fun onResponse(call: Call<BingResponse>, response: Response<BingResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                var bingGalleryItems: List<BingGalleryItem> =
                    response.body()?.galleryItems ?: mutableListOf()
                bingGalleryItems = bingGalleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = bingGalleryItems
            }

            override fun onFailure(call: Call<BingResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ", t)
            }
        })
        return responseLiveData
    }

    fun cancelRequest() {
        if (::bingRequest.isInitialized) {
            bingRequest.cancel()
        }
    }
}