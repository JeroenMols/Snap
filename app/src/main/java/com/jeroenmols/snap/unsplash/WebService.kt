package com.jeroenmols.snap.unsplash

import android.util.Log
import com.jeroenmols.snap.unsplash.api.UnsplashService
import com.jeroenmols.snap.unsplash.data.SearchResult
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import io.reactivex.Single
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class WebService {

    private val unsplashService: UnsplashService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        unsplashService = retrofit.create(UnsplashService::class.java)

    }

    fun getPhotos() : Single<List<UnsplashPhoto>> {
        Log.v("JEROEN", "Doing a network call")
        return unsplashService.getPhotos()
    }

    fun searchPhotos(searchTerm : String) : Single<List<UnsplashPhoto>> {
        Log.v("JEROEN", "Doing a network call")
        return unsplashService.searchPhotos(searchTerm).map { it.results }
    }
}