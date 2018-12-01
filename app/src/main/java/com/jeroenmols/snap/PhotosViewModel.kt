package com.jeroenmols.snap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeroenmols.snap.api.PhotosRepository
import com.jeroenmols.snap.common.data.Photo

class PhotosViewModel(private val photosRepository: PhotosRepository) : ViewModel() {

    val photos = MutableLiveData<List<Photo>>()

    init {
        photosRepository.getPhotos().subscribe(photos::postValue)
    }

    fun search(query: String) {
        photosRepository.searchPhotos(query).subscribe(photos::postValue)
    }
}
