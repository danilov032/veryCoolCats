package com.example.catstestapp.data.repositories

import com.example.catstestapp.data.db.ReadoutModelDao
import com.example.catstestapp.domain.models.ModelCatFavourites
import io.reactivex.Single
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val dbCatsFavourites: ReadoutModelDao
){

    fun selectCats():Single<List<ModelCatFavourites>> = dbCatsFavourites.getCats()

    fun addCat(cat: ModelCatFavourites) = dbCatsFavourites.insertCat(cat)

    fun delCat(cat: ModelCatFavourites) = dbCatsFavourites.deleteCat(cat.url)
}