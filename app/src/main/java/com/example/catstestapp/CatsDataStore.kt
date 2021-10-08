package com.example.catstestapp

import com.example.catstestapp.models.Cat
import io.reactivex.Observable
import javax.inject.Inject

class CatsDataStore @Inject constructor() {
    private var cats: List<Cat>? = null

    fun updateListCats(listCats: List<Cat>) {
        cats = listCats
    }

    fun getListCats(): Observable<List<Cat>> =
        cats?.let { Observable.just(it) } ?: Observable.empty()

    fun clear() {
        cats = null
    }
}