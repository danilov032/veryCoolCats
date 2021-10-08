package com.example.catstestapp.DI

import com.example.catstestapp.ui.favorites.FavouritesActivity
import com.example.catstestapp.ui.main.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectMainActivity(activity: MainActivity)
    fun injectFavouritesActivity(activity: FavouritesActivity)
}