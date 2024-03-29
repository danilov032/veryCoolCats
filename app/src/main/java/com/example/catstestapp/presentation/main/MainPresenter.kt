package com.example.catstestapp.presentation.main

import com.example.catstestapp.domain.models.Cat
import com.example.catstestapp.domain.models.ModelCatFavourites
import com.example.catstestapp.domain.interactor.Interactor
import io.reactivex.Single
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
        Single.just(cat)
            .map { ModelCatFavourites(url = it.url) }
            .doOnSuccess { catElement ->
                interactor.addCat(catElement)
            }
            .flatMap { interactor.getCats() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ catElement ->
                viewState.showCats(catElement)
            }, {
                viewState.showError("Ошибка добавления в избранное.")
            })
    }

    private fun deleteCat(cat: Cat) {
        Single.just(cat)
            .map { ModelCatFavourites(url = it.url) }
            .doOnSuccess {
                interactor.delCat(it)
            }
            .flatMap { interactor.getCats() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ catElement ->
                viewState.showCats(catElement)
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
            .delay(TIME_DALAY, TimeUnit.MILLISECONDS)
            .flatMap {
                if (it) interactor.getCats()
                else throw RuntimeException()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ catElement ->
                viewState.showCats(catElement)
                viewState.showSuccessfulResultDownload()
            }, {
                viewState.showError("Неизвестная ошибка загрузки изображения.")
            })
    }

    override fun onDestroy() {
        interactor.clearDataStore()
        super.onDestroy()
    }

    companion object {
        private const val TIME_DALAY: Long = 300
    }
}