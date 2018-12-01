package com.jeroenmols.snap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeroenmols.snap.api.WebService
import com.jeroenmols.snap.data.Photo
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto

class PhotosViewModel(private val webService: WebService) : ViewModel() {

    val photos = MutableLiveData<List<Photo>>()

    init {
        webService.getPhotos().subscribe(photos::postValue)
    }

    fun search(query: String) {
        webService.searchPhotos(query).subscribe(photos::postValue)
    }
}
