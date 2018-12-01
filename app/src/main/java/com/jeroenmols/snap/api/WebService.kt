package com.jeroenmols.snap.api

import com.jeroenmols.snap.data.Photo
import com.jeroenmols.snap.source.pexels.PexelsWebService
import com.jeroenmols.snap.source.pexels.data.PexelsPhoto
import com.jeroenmols.snap.source.unsplash.UnsplashWebService
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import io.reactivex.Observable
import io.reactivex.Single

class WebService {

    private val unsplashService: UnsplashWebService
    private val pexelsService: PexelsWebService

    init {
        unsplashService = UnsplashWebService()
        pexelsService = PexelsWebService()
    }

    fun getPhotos(): Single<List<Photo>> {
        val unsplash = unsplashService.getPhotos().toObservable()
        val pexels = pexelsService.getPhotos().toObservable()
        return Observable.zip<List<Photo>, List<Photo>>(listOf(pexels, unsplash)) { t1 ->
            val all = mutableListOf<Photo>()
            t1.asList().forEach { all.addAll(it as List<Photo>) }
            all
        }.firstOrError()
    }

    fun searchPhotos(searchTerm: String): Single<List<Photo>> {
        val unsplash = unsplashService.searchPhotos(searchTerm).toObservable()
        val pexels = pexelsService.searchPhotos(searchTerm).toObservable()
        return Observable.zip<List<Photo>, List<Photo>>(listOf(pexels, unsplash)) { t1 ->
            val all = mutableListOf<Photo>()
            t1.asList().forEach { all.addAll(it as List<Photo>) }
            all
        }.firstOrError()
    }

}