package com.example.catstestapp.ui

import android.annotation.SuppressLint
import com.example.catstestapp.models.Cat
import com.example.catstestapp.models.ModelCatFavourites
import com.example.catstestapp.ui.favorites.FavouritesRepository
import com.example.catstestapp.ui.main.MainRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class Interactor @Inject constructor(
    private val mainRepository: MainRepository,
    private val favouritesRepository: FavouritesRepository,
    private val downloadRepository: DownloadRepository
) {

    fun getCats(): Observable<List<Cat>> =
        Observable.zip (
                mainRepository.apiService.getListCats(25, 20),
                favouritesRepository.selectCats(),
                downloadRepository.getFilesDownloadO(),
                Function3<List<Cat>, List<ModelCatFavourites>, List<File>,List<Cat>> { all, favorite, file ->
                    all.map { cat ->
                        Cat(cat.url,
                            favorite.find { favCat -> favCat.url == cat.url }?.let { true } ?: false,
                            file.find {
                                file -> file.toString().substring(file.toString().lastIndexOf("/") + 1,file.toString().lastIndexOf(".")) ==
                                    cat.url.md5()
                            }?.let { true } ?: false
                        )
                    }
                }
            )

    fun addCat(cat: ModelCatFavourites){
        favouritesRepository.addCat(cat)
    }

    fun delCat(cat: ModelCatFavourites) =  favouritesRepository.delCat(cat)

    fun getFavouritesCats() : Observable<List<Cat>> =
        Observable.zip(
            favouritesRepository.selectCats(),
            downloadRepository.getFilesDownloadO(),
            BiFunction <List<ModelCatFavourites>,List<File>,List<Cat>>{ fav, file ->
                fav.map { cat->
                    Cat(cat.url,
                     true,
                        file.find {
                                file -> file.toString().substring(file.toString().lastIndexOf("/") + 1,file.toString().lastIndexOf(".")) ==
                                cat.url.md5()
                        }?.let { true } ?: false
                    )
                }
            }
        )

    fun downloadCat(cat: Cat): Observable<Boolean> = Observable.just(downloadRepository.saveCat(cat))

    fun checkPermission() = downloadRepository.checkPermission()

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}