package com.jeroenmols.snap.source.unsplash.data

data class UnsplashPhoto(
    val id: String,
    val color: String,
    val urls : Map<String, String>
)