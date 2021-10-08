package com.example.catstestapp.ui.favorites

import com.example.catstestapp.DB.ReadoutModelDao
import com.example.catstestapp.models.ModelCatFavourites
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val dbCatsFavourites: ReadoutModelDao
){

    fun selectCats():Observable<List<ModelCatFavourites>> = dbCatsFavourites.getCats()

    fun addCat(cat: ModelCatFavourites) = dbCatsFavourites.insertCat(cat)

    fun delCat(cat: ModelCatFavourites) = dbCatsFavourites.deleteCat(cat.url)
}