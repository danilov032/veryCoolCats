package com.example.catstestapp.ui.favorites

import com.example.catstestapp.DB.ReadoutModelDao
import com.example.catstestapp.models.ModelCatFavourites
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val dbCatsFavourites: ReadoutModelDao
){

    fun selectCats():Observable<List<ModelCatFavourites>> = dbCatsFavourites.getCats()

    fun addCat(cat: ModelCatFavourites){
        with(dbCatsFavourites) {
            this.insertCat(cat)
        }
    }

    fun delCat(cat: ModelCatFavourites) {
        with(dbCatsFavourites) {
            this.deleteCat(cat.url)
        }
    }
}