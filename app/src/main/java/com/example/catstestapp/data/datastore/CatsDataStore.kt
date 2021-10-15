package com.example.catstestapp.data.datastore

import com.example.catstestapp.domain.models.Cat
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CatsDataStore @Inject constructor() {
    private var cats: List<Cat>? = null

    fun updateListCats(listCats: List<Cat>) {
        cats = listCats
    }

    fun getListCats(): Maybe<List<Cat>> =
        cats?.let { cats -> Maybe.just(cats) } ?: Maybe.empty()


    fun clear() {
        cats = null
    }
}