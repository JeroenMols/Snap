package com.jeroenmols.snap.api

import com.jeroenmols.snap.data.Photo
import com.jeroenmols.snap.source.pexels.PexelsWebService
import com.jeroenmols.snap.source.pexels.data.PexelsPhoto
import com.jeroenmols.snap.source.unsplash.UnsplashWebService
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class WebService {

    private val unsplashService: UnsplashWebService
    private val pexelsService: PexelsWebService

    init {
        unsplashService = UnsplashWebService()
        pexelsService = PexelsWebService()
    }

    fun getPhotos(): Single<List<Photo>> {
        val unsplash = unsplashService.getPhotos().map(this::convertUnsplashToPhotos)
        val pexels = pexelsService.getPhotos().map(this::convertPexelsToPhotos)
        return unsplash.zipWith(pexels, BiFunction { a: List<Photo>, b: List<Photo> ->
            val all = mutableListOf<Photo>()
            all.addAll(a)
            all.addAll(b)
            all
        })
    }

    fun searchPhotos(searchTerm: String): Single<List<Photo>> {
        val unsplash = unsplashService.searchPhotos(searchTerm).map(this::convertUnsplashToPhotos)
        val pexels = pexelsService.searchPhotos(searchTerm).map(this::convertPexelsToPhotos)
        return unsplash.zipWith(pexels, BiFunction { a: List<Photo>, b: List<Photo> ->
            val all = mutableListOf<Photo>()
            all.addAll(a)
            all.addAll(b)
            all
        })
    }

    private fun convertUnsplashToPhotos(list: List<UnsplashPhoto>): List<Photo> {
        return list.map { Photo(it.id, it.urls["thumb"]!!, it.urls["full"]!!, it.color) }
    }

    private fun convertPexelsToPhotos(list: List<PexelsPhoto>): List<Photo> {
        return list.map { Photo("${it.id}", it.src["tiny"]!!, it.src["large2x"]!!, "#ffffff") }
    }
}