package com.example.catstestapp.ui.main

import android.annotation.SuppressLint
import com.example.catstestapp.DB.ReadoutModelDao
import com.example.catstestapp.models.Cat
import com.example.catstestapp.models.ModelCatFavourites
import com.example.catstestapp.ui.Interactor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val interactor: Interactor
) : MvpPresenter<MainContractView>() {

    @SuppressLint("CheckResult")
    override fun attachView(view: MainContractView) {
        super.attachView(view)
        interactor.getCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCats(it)
            }, {
                viewState.showError("Выбериет изображение")
            })
    }

    fun onClickInFavorites(cat: Cat) {
        if (!cat.isFavourites) addCat(cat)
        else deleteCat(cat)
    }

    @SuppressLint("CheckResult")
    private fun addCat(cat: Cat){
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
                viewState.showError("Выбериет изображение")
            })
    }

    @SuppressLint("CheckResult")
    private fun deleteCat(cat: Cat){
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
                viewState.showError("Выбериет изображение")
            })
    }

    fun showFavourites() {
        viewState.transitionFavouritesCats()
    }

    @SuppressLint("CheckResult")
    fun onClickDownload(cat: Cat) {
        if(!interactor.chechPermission())
            viewState.installPermission()

        interactor.downloadCat(cat)
            .delay(300, TimeUnit.MILLISECONDS )
            .flatMap {
                if (it) interactor.getCats()
                else throw RuntimeException()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCats(it)
                viewState.downloadCatOk()
            }, {
                viewState.showError("Ошибка ошибочка")
            })
    }
}