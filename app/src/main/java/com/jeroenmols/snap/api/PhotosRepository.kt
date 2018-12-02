package com.jeroenmols.snap.api

import android.util.Log
import com.jeroenmols.snap.common.data.Photo
import com.jeroenmols.snap.common.api.RemoteSource
import com.jeroenmols.snap.source.pexels.PexelsSource
import com.jeroenmols.snap.source.unsplash.UnsplashSource
import io.reactivex.Observable
import io.reactivex.Single

class PhotosRepository {

    private val sources : List<RemoteSource> = listOf(UnsplashSource(), PexelsSource())

    fun getPhotos(): Observable<List<Photo>> {
        return combineResultFromAllSources(RemoteSource::getPhotos)
    }

    fun searchPhotos(searchTerm: String): Observable<List<Photo>> {
        return combineResultFromAllSources { it.searchPhotos(searchTerm) }
    }

    fun combineResultFromAllSources(function : (t: RemoteSource) -> Single<List<Photo>>) : Observable<List<Photo>> {
        val observables = mutableListOf<Observable<List<Photo>>>()
        sources.forEach { observables.add(function(it).toObservable()) }

        return Observable.merge(observables).scan { a, b -> val mutableList = a.toMutableList()
            mutableList.addAll(b)
            mutableList
        }.doOnNext { Log.d("DEBUG", "showing ${it.size} items") }
    }

}