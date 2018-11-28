package com.jeroenmols.snap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.data.SearchResult
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel(private val webService: WebService) : ViewModel() {

    val photos = MutableLiveData<List<UnsplashPhoto>>()

    init {
        webService.getPhotos().subscribeOn(Schedulers.io()).subscribe(photos::postValue)
    }

    fun search(query : String) {
        webService.searchPhotos(query).subscribeOn(Schedulers.io()).subscribe(photos::postValue)
    }
}
