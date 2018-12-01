package com.jeroenmols.snap.api

import com.jeroenmols.snap.data.Photo
import com.jeroenmols.snap.source.pexels.PexelsSource
import com.jeroenmols.snap.source.unsplash.UnsplashSource
import io.reactivex.Observable
import io.reactivex.Single

class PhotosRepository {

    private val sources : List<RemoteSource> = listOf(UnsplashSource(), PexelsSource())

    fun getPhotos(): Single<List<Photo>> {
        return combineResultFromAllSources(RemoteSource::getPhotos)
    }

    fun searchPhotos(searchTerm: String): Single<List<Photo>> {
        return combineResultFromAllSources { it.searchPhotos(searchTerm) }
    }

    fun combineResultFromAllSources(function : (t: RemoteSource) -> Single<List<Photo>>) : Single<List<Photo>> {
        val observables = mutableListOf<Observable<List<Photo>>>()
        sources.forEach { observables.add(function(it).toObservable()) }

        return Observable.zip<List<Photo>, List<Photo>>(observables) { t1 ->
            val all = mutableListOf<Photo>()
            t1.asList().forEach { all.addAll(it as List<Photo>) }
            all
        }.firstOrError()
    }

}