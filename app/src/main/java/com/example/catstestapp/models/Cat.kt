package com.example.catstestapp.models

data class Cat(
    val url: String,
    var isFavourites: Boolean = false,
    var isDownloads: Boolean = false
)