package com.example.catstestapp.data.datastore

import com.example.catstestapp.domain.models.Cat
import io.reactivex.Observable
import javax.inject.Inject

class CatsDataStore @Inject constructor() {
    private var cats: List<Cat>? = null

    fun updateListCats(listCats: List<Cat>) {
        cats = listCats
    }

    fun getListCats(): Observable<List<Cat>> =
        cats?.let { cats -> Observable.just(cats) } ?: Observable.empty()


    fun clear() {
        cats = null
    }
}