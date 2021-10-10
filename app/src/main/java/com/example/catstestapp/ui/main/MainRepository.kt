package com.example.catstestapp.ui.main

import com.example.catstestapp.models.Cat
import com.example.catstestapp.ApiService
import com.example.catstestapp.mappers.CatModelMapper
import com.example.catstestapp.CatsDataStore
import io.reactivex.Observable

class MainRepository(
    private val apiService: ApiService,
    private val dataStore: CatsDataStore,
    private val mapper: CatModelMapper
) {

    fun searchCat(page: Int, limit: Int): Observable<List<Cat>> =
        dataStore.getListCats()
            .switchIfEmpty {
                apiService.getListCats(page, limit)
                    .map { responseList ->
                        responseList.map { response ->
                            mapper(response)
                        }
                    }
                    .doOnNext {
                        dataStore.updateListCats(it)
                    }
            }

    fun clearDataStore(){
        dataStore.clear()
    }
}