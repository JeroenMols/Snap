package com.jeroenmols.snap

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.jeroenmols.snap.common.data.Photo
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_photos.*
import java.util.concurrent.TimeUnit

class PhotosFragment : Fragment() {

    lateinit var viewModel: PhotosViewModel
    lateinit var adapter: PhotosAdapter

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, PhotosViewModelFactory()).get(PhotosViewModel::class.java)
        adapter = PhotosAdapter()

        viewModel.photos.observe(this, Observer<List<Photo>> { photos -> adapter.photos = photos })
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
        disposables.add(searchView.queryTextChanges().skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribe { viewModel.search(it.toString()) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}
