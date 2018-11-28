package com.jeroenmols.snap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {

    lateinit var viewModel: PhotosViewModel
    lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, PhotosViewModelFactory()).get(PhotosViewModel::class.java)
        adapter = PhotosAdapter()

        viewModel.photos.observe(this, Observer<List<UnsplashPhoto>> { photos -> adapter.photos = photos })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photos_list.adapter = adapter
        photos_list.layoutManager = GridLayoutManager(activity, 2)
        photos_search.setOnClickListener { viewModel.search("lego")}
    }

}
