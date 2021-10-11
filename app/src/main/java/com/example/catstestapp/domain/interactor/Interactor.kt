package com.example.catstestapp.domain.interactor

import com.example.catstestapp.domain.models.Cat
import com.example.catstestapp.domain.models.ModelCatFavourites
import com.example.catstestapp.data.repositories.DownloadRepository
import com.example.catstestapp.data.repositories.FavouritesRepository
import com.example.catstestapp.data.repositories.MainRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class Interactor @Inject constructor(
    private val mainRepository: MainRepository,
    private val favouritesRepository: FavouritesRepository,
    private val downloadRepository: DownloadRepository
) {

    fun getCats(): Observable<List<Cat>> =
        Observable.zip (
                mainRepository.searchCat(25, 20),
                favouritesRepository.selectCats(),
                BiFunction<List<Cat>, List<ModelCatFavourites>,List<Cat>> { all, favorite ->
                    all.map { cat ->
                        Cat(cat.url,
                            favorite.find { favCat -> favCat.url == cat.url }?.let { true } ?: false
                        )
                    }
                }
            )

    fun addCat(cat: ModelCatFavourites){
        favouritesRepository.addCat(cat)
    }

    fun delCat(cat: ModelCatFavourites) =  favouritesRepository.delCat(cat)

    fun getFavouritesCats() : Observable<List<Cat>> =
            favouritesRepository.selectCats()
                .map {cat ->
                    cat.map{
                        Cat(url = it.url,
                        isFavourites = true)
                    }
                }

    fun downloadCat(cat: Cat): Observable<Boolean> = Observable.just(downloadRepository.saveCat(cat))

    fun checkPermission() = downloadRepository.checkPermission()

    fun clearDataStore(){
        mainRepository.clearDataStore()
    }
}