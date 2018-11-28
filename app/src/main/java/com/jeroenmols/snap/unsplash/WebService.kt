package com.jeroenmols.snap.unsplash

import android.util.Log
import com.jeroenmols.snap.unsplash.api.UnsplashService
import com.jeroenmols.snap.unsplash.data.SearchResult
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
        Log.v("JEROEN", "Doing a network call")
        val call = unsplashService.getPhotos()
        call.enqueue(myCallback)
    }

    fun searchPhotos(searchTerm : String, myCallback: Callback<SearchResult>) {
        Log.v("JEROEN", "Doing a network call")
        val call = unsplashService.searchPhotos(searchTerm)
        call.enqueue(myCallback)
    }
}