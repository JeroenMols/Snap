package com.jeroenmols.snap.api

import com.jeroenmols.snap.data.Photo
import com.jeroenmols.snap.source.unsplash.api.UnsplashService
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class WebService {

    private val unsplashService: UnsplashService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(UnsplashService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        unsplashService = retrofit.create(UnsplashService::class.java)
    }

    fun getPhotos(): Single<List<Photo>> = unsplashService.getPhotos().map(this::convertToPhotos)

    fun searchPhotos(searchTerm: String): Single<List<Photo>> =
        unsplashService.searchPhotos(searchTerm).map { it.results }.map(this::convertToPhotos)

    private fun convertToPhotos(list: List<UnsplashPhoto>): List<Photo> {
        return list.map { Photo(it.id, it.urls["thumb"]!!, it.urls["full"]!!, it.color) }
    }
}