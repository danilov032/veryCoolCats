package com.example.catstestapp.ui.main

import com.example.catstestapp.models.Cat
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainContractView : MvpView {

    fun showCats(list: List<Cat>)
    fun installPermission()
    fun addFavouriteCatsOk()
    fun transitionFavouritesCats()
    fun downloadCatOk()
    fun showError(strErr: String)
}