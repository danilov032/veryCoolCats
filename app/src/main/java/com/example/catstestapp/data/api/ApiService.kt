package com.example.catstestapp.data.api

import com.example.catstestapp.data.responses.CatResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_QUERY)
    fun getListCats(@Query("page") perPage: Int,@Query("limit") limit: Int): Observable<List<CatResponse>>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.thecatapi.com")
                .build()

            return retrofit.create(ApiService::class.java);
        }

        private const val GET_QUERY = "/v1/images/search?order=DESC&api_key=7fd8c10a-a96d-4e0f-8d6a-6a83ca4ffbce"
    }
}