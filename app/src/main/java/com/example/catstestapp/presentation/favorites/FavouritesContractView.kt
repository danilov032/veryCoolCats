package com.example.catstestapp.presentation.favorites

import com.example.catstestapp.domain.models.Cat
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