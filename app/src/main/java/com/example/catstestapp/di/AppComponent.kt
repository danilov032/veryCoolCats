package com.example.catstestapp.di

import com.example.catstestapp.presentation.favorites.FavouritesActivity
import com.example.catstestapp.presentation.main.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectMainActivity(activity: MainActivity)
    fun injectFavouritesActivity(activity: FavouritesActivity)
}