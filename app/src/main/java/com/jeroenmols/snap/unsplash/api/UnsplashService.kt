package com.jeroenmols.snap.unsplash.api

import com.jeroenmols.snap.BuildConfig
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashService {
    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
    @GET("photos")
    fun getPhotos(): Call<List<UnsplashPhoto>>
}