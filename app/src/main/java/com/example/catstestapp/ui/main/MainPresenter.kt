package com.example.catstestapp.ui.main

import com.example.catstestapp.models.Cat
import com.example.catstestapp.models.ModelCatFavourites
import com.example.catstestapp.ui.Interactor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val interactor: Interactor
) : MvpPresenter<MainContractView>() {

    override fun attachView(view: MainContractView) {
        super.attachView(view)
        interactor.getCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listCats ->
                viewState.showCats(listCats)
            }, { error ->
                when (error) {
                    is java.net.UnknownHostException -> viewState.showError("Отсутствует подключение к интернету.")
                    else -> viewState.showError("Неизвестная ошибка.")
                }
            })
    }

    fun onClickInFavorites(cat: Cat) {
        if (!cat.isFavourites) addCat(cat)
        else deleteCat(cat)
    }

    private fun addCat(cat: Cat) {
        Observable.just(cat)
            .map { ModelCatFavourites(url = it.url) }
            .doOnNext {
                interactor.addCat(it)
            }
            .flatMap { interactor.getCats() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCats(it)
            }, {
                viewState.showError("Ошибка добавления в избранное.")
            })
    }

    private fun deleteCat(cat: Cat) {
        Observable.just(cat)
            .map { ModelCatFavourites(url = it.url) }
            .doOnNext {
                interactor.delCat(it)
            }
            .flatMap { interactor.getCats() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCats(it)
            }, {
                viewState.showError("Изображение не выбранно. Выберите изображение")
            })
    }

    fun showFavourites() {
        viewState.transitionFavouritesCats()
    }

    fun onClickDownload(cat: Cat) {
        if (!interactor.checkPermission())
            viewState.installPermission()

        interactor.downloadCat(cat)
            .delay(300, TimeUnit.MILLISECONDS)
            .flatMap {
                if (it) interactor.getCats()
                else throw RuntimeException()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCats(it)
                viewState.showSuccessfulResultDownload()
            }, {
                viewState.showError("Неизвестная ошибка загрузки изображения.")
            })
    }
}