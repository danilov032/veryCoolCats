package com.example.catstestapp.presentation.main

import com.example.catstestapp.domain.models.Cat
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainContractView : MvpView {

    fun showCats(listCats: List<Cat>)
    fun installPermission()
    fun shoeSuccessfulResult()
    fun transitionFavouritesCats()
    fun showSuccessfulResultDownload()
    fun showError(messageError: String)
}