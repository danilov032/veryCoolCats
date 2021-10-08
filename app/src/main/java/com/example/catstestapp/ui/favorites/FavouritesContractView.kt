package com.example.catstestapp.ui.favorites

import com.example.catstestapp.models.Cat
import com.example.catstestapp.models.ModelCatFavourites
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavouritesContractView : MvpView{

    fun showCats(listCats: List<Cat>)
    fun showError(messageError: String)
    fun installPermission()
    fun showSuccessfulResultDownload()
}