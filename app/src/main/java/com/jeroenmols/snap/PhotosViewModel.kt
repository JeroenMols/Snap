package com.jeroenmols.snap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.data.SearchResult
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel(private val webService: WebService) : ViewModel() {

    val photos = MutableLiveData<List<UnsplashPhoto>>()

    init {
        webService.getPhotos(object : Callback<List<UnsplashPhoto>> {
            override fun onResponse(call: Call<List<UnsplashPhoto>>, response: Response<List<UnsplashPhoto>>) {
                photos.postValue(response.body())
            }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    fun search(query : String) {
        webService.searchPhotos(query, object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                photos.postValue(response.body()!!.results)
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
