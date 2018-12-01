package com.jeroenmols.snap.source.unsplash.api

import com.jeroenmols.snap.BuildConfig
import com.jeroenmols.snap.source.unsplash.data.UnsplashSearchResult
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashService {

    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
    @GET("photos?per_page=100")
    fun getPhotos(): Single<List<UnsplashPhoto>>

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
    @GET("search/photos?per_page=100")
    fun searchPhotos(@Query("query") searchTerm: String): Single<UnsplashSearchResult>
}