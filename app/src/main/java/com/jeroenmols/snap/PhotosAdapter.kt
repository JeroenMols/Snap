package com.jeroenmols.snap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeroenmols.snap.common.data.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photo.*

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    var photos: List<Photo> = emptyList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotosViewHolder(view)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    class PhotosViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(photo: Photo) {
            Picasso.get().load(photo.thumbSrc)
                .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(imageview_photo)
        }
    }
}