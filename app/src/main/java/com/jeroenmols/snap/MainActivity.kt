package com.jeroenmols.snap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.api.UnsplashService
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        WebService().getPhotos(MyCallback())
    }

    class MyCallback : Callback<List<UnsplashPhoto>> {
        override fun onResponse(call: Call<List<UnsplashPhoto>>, response: Response<List<UnsplashPhoto>>) {
            val photos = response.body()!!
            photos.forEach { Log.v("JEROEN", "Loaded image: ${it.id} with url: ${it.urls["raw"]}") }
        }

        override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}

