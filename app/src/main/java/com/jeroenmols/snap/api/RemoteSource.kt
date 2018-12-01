package com.jeroenmols.snap.api

import com.jeroenmols.snap.data.Photo
import io.reactivex.Single

interface RemoteSource {
    fun getPhotos(): Single<List<Photo>>
    fun searchPhotos(query: String): Single<List<Photo>>
}