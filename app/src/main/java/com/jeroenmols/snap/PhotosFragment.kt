package com.jeroenmols.snap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto

/**
 * A placeholder fragment containing a simple view.
 */
class PhotosFragment : Fragment() {

    lateinit var viewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, PhotosViewModelFactory()).get(PhotosViewModel::class.java)

        viewModel.photos.observe(this, object : Observer<List<UnsplashPhoto>> {
            override fun onChanged(t: List<UnsplashPhoto>?) {
                t?.forEach{ Log.v("JEROEN", "Loaded image: ${it.id} with url: ${it.urls["raw"]}") }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }
}
