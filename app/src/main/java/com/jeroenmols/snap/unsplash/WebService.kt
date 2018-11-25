package com.jeroenmols.snap.unsplash

import com.jeroenmols.snap.unsplash.api.UnsplashService
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WebService {

    private val unsplashService: UnsplashService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        unsplashService = retrofit.create(UnsplashService::class.java)

    }

    fun getPhotos(myCallback: Callback<List<UnsplashPhoto>>) {
        val call = unsplashService.getPhotos()
        call.enqueue(myCallback)
    }
}