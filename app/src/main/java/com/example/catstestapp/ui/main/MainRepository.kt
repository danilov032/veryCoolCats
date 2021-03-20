package com.example.catstestapp.ui.main

import com.example.catstestapp.models.Cat
import com.example.catstestapp.ApiService
import io.reactivex.Observable

class MainRepository(val apiService: ApiService) {

    fun searchCat(page: Int,limit: Int): Observable<List<Cat>> = apiService.getListCats(page, limit)
}