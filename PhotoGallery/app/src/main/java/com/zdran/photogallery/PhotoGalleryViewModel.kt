package com.zdran.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zdran.photogallery.bingApi.BingFetchr
import com.zdran.photogallery.bingApi.BingGalleryItem

class PhotoGalleryViewModel : ViewModel() {
    val galleryItemLiveData: LiveData<List<BingGalleryItem>> = BingFetchr().fetchPhotos()

}