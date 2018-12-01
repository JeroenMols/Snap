package com.jeroenmols.snap.common.api

import com.jeroenmols.snap.common.data.Photo
import io.reactivex.Single

interface RemoteSource {
    fun getPhotos(): Single<List<Photo>>
    fun searchPhotos(query: String): Single<List<Photo>>
}