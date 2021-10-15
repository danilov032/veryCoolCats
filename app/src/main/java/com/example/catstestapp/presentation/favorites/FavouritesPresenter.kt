package com.example.catstestapp.presentation.favorites

import com.example.catstestapp.domain.models.Cat
import com.example.catstestapp.domain.models.ModelCatFavourites
import com.example.catstestapp.domain.interactor.Interactor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class FavouritesPresenter @Inject constructor(
    private val interactor: Interactor
):MvpPresenter<FavouritesContractView>(){

    override fun attachView(view: FavouritesContractView) {
        super.attachView(view)

        interactor.getFavouritesCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listCats ->
                viewState.showCats(listCats)
            }, {
                viewState.showError("Ошибка подключения к базе данных.")
            })
    }

    fun onClickInFavorites(cat: Cat) {
        Single.just(cat)
            .map { ModelCatFavourites(url = it.url) }
            .doOnSuccess {
                interactor.delCat(it)
            }
            .flatMap { interactor.getFavouritesCats() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ catElement ->
                viewState.showCats(catElement)
            }, {
                viewState.showError("Выбериет изображение")
            })
    }

    fun onClickDownload(cat: Cat) {
        if(!interactor.checkPermission())
            viewState.installPermission()

        interactor.downloadCat(cat)
            .delay(TIME_DALAY, TimeUnit.MILLISECONDS )
            .flatMap {
                if (it) interactor.getFavouritesCats()
                else throw RuntimeException()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ catElement ->
                viewState.showCats(catElement)
                viewState.showSuccessfulResultDownload()
            }, {
                viewState.showError("Ошибка скачивания")
            })
    }

    companion object{
        private const val TIME_DALAY: Long = 300
    }
}