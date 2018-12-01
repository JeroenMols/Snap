package com.jeroenmols.snap.source.unsplash

import com.jeroenmols.snap.common.api.RemoteSource
import com.jeroenmols.snap.common.data.Photo
import com.jeroenmols.snap.source.unsplash.api.UnsplashService
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class UnsplashSource : RemoteSource {

    private val service: UnsplashService

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

        service = retrofit.create(UnsplashService::class.java)
    }

    override fun getPhotos(): Single<List<Photo>> = service.getPhotos().map { it.toPhotos() }

    override fun searchPhotos(searchTerm: String): Single<List<Photo>> =
        service.searchPhotos(searchTerm).map { it.results.toPhotos() }

    private fun List<UnsplashPhoto>.toPhotos(): List<Photo> {
        return this.map { Photo(it.id, it.urls["thumb"]!!, it.urls["full"]!!, it.color) }
    }
}