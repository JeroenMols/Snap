package com.jeroenmols.snap.source.pexels

import com.jeroenmols.snap.source.pexels.api.PexelsService
import com.jeroenmols.snap.source.pexels.data.PexelsPhoto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class PexelsWebService {

    private val service: PexelsService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PexelsService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        service = retrofit.create(PexelsService::class.java)
    }

    fun getPhotos(): Single<List<PexelsPhoto>> = service.getPhotos().map { it.photos }

    fun searchPhotos(searchTerm: String): Single<List<PexelsPhoto>> =
        service.searchPhotos(searchTerm).map { it.photos }
}