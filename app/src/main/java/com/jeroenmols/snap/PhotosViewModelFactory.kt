package com.jeroenmols.snap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeroenmols.snap.unsplash.WebService
import java.lang.RuntimeException

class PhotosViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(WebService()) as T
        } else {
            throw RuntimeException()
        }
    }

}
