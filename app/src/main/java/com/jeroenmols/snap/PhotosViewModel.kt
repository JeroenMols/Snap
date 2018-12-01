package com.jeroenmols.snap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver

class PhotosViewModel(private val webService: WebService) : ViewModel() {

    val photos = MutableLiveData<List<UnsplashPhoto>>()
    var disposable :Disposable = TestObserver<Any>()

    init {
        webService.getPhotos().subscribe(photos::postValue)
    }

    fun search(query: String) {
        disposable.dispose()
        disposable = webService.searchPhotos(query).subscribe(photos::postValue)
    }
}
