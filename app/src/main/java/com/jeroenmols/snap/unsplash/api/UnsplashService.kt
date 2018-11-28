package com.jeroenmols.snap.unsplash.api

import com.jeroenmols.snap.BuildConfig
import com.jeroenmols.snap.unsplash.data.SearchResult
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashService {
    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
    @GET("photos?per_page=100")
    fun getPhotos(): Call<List<UnsplashPhoto>>

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
    @GET("search/photos?per_page=100")
    fun searchPhotos(@Query("query") searchTerm: String): Call<SearchResult>
}