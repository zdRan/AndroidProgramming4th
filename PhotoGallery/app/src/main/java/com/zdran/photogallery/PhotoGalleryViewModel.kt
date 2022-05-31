package com.zdran.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zdran.photogallery.bingApi.BingFetchr
import com.zdran.photogallery.bingApi.BingGalleryItem
import com.zdran.photogallery.dager.DaggerBingFetchrComponent
import javax.inject.Inject

class PhotoGalleryViewModel: ViewModel() {

    private var bingFetchr = DaggerBingFetchrComponent.create().getBingFetch()

    val galleryItemLiveData: LiveData<List<BingGalleryItem>> = bingFetchr.fetchPhotos()

    override fun onCleared() {
        super.onCleared()
        bingFetchr.cancelRequest()
    }
}