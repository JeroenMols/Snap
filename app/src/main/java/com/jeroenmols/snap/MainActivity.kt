package com.jeroenmols.snap

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val unsplashService = retrofit.create(UnsplashService::class.java)
        val call = unsplashService.listRepos()
        call.enqueue(MyCallback())

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

