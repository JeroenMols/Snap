package com.jeroenmols.snap.pexels.api

import com.jeroenmols.snap.BuildConfig
import com.jeroenmols.snap.pexels.data.SearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsService {

    companion object {
        const val BASE_URL = "https://api.pexels.com/v1/"
    }

    @Headers("Authorization: ${BuildConfig.PEXELS_API_KEY}")
    @GET("curated?per_page=100")
    fun getPhotos(): Single<SearchResult>

    @Headers("Authorization: ${BuildConfig.PEXELS_API_KEY}")
    @GET("search?per_page=100")
    fun searchPhotos(@Query("query") searchTerm: String): Single<SearchResult>
}