package com.example.catstestapp.ui.main

import com.example.catstestapp.models.Cat
import com.example.catstestapp.ApiService
import com.example.catstestapp.models.CatResponse
import io.reactivex.Observable

class MainRepository(private val apiService: ApiService) {

    fun searchCat(page: Int, limit: Int): Observable<List<Cat>> =
        mapList(apiService.getListCats(page, limit))

    fun mapList(list: Observable<List<CatResponse>>): Observable<List<Cat>> =
        list.map {
            it.map {
                Cat(url = it.url)
            }
        }
}