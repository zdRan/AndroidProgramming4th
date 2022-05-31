package com.zdran.photogallery.dager

import com.zdran.photogallery.PhotoGalleryViewModel
import com.zdran.photogallery.bingApi.BingFetchr
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [BingApiModule::class])
interface BingFetchrComponent {

    fun getBingFetch():BingFetchr


}