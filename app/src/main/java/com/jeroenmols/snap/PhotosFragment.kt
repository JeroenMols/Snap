package com.jeroenmols.snap

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(photos_toolbar)
        photos_list.adapter = adapter
        photos_list.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.photos, menu)
        val searchView = menu!!.findItem(R.id.action_search)!!.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText!!)
                return true
            }
        })
    }
}
