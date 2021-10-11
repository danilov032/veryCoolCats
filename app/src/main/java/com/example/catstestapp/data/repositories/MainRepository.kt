package com.example.catstestapp.data.repositories

import com.example.catstestapp.data.api.ApiService
import com.example.catstestapp.data.datastore.CatsDataStore
import com.example.catstestapp.data.mappers.CatModelMapper
import com.example.catstestapp.domain.models.Cat
import io.reactivex.Observable

class MainRepository(
    private val apiService: ApiService,
    private val dataStore: CatsDataStore,
    private val mapper: CatModelMapper
) {

    fun searchCat(page: Int, limit: Int): Observable<List<Cat>> =
        dataStore.getListCats()
            .switchIfEmpty(
                apiService.getListCats(page, limit)
                    .map { responseList ->
                        responseList.map { response ->
                            mapper(response)
                        }
                    }
                    .doOnNext { cats ->
                        dataStore.updateListCats(cats)
                    }
            )

    fun clearDataStore(){
        dataStore.clear()
    }
}