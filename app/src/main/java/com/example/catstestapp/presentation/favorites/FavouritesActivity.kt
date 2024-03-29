package com.example.catstestapp.presentation.favorites

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catstestapp.presentation.adapters.CustomAdapter
import com.example.catstestapp.di.AppModule
import com.example.catstestapp.di.DaggerAppComponent
import com.example.catstestapp.R
import com.example.catstestapp.domain.models.Cat
import kotlinx.android.synthetic.main.activity_favourites.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavouritesActivity : MvpAppCompatActivity(),
    FavouritesContractView {

    private val customAdapter: CustomAdapter by lazy { CustomAdapter(
        { cat -> presenter.onClickInFavorites(cat) },
        { cat -> presenter.onClickDownload(cat) }
    ) }

    @Inject
    lateinit var presenterLazy: dagger.Lazy<FavouritesPresenter>

    @InjectPresenter
    lateinit var presenter: FavouritesPresenter

    @ProvidePresenter
    fun providePresenter(): FavouritesPresenter {
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
            .injectFavouritesActivity(this)

        return presenterLazy.get()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun installPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        recyclerViewFavourites.apply {
            layoutManager = LinearLayoutManager(this@FavouritesActivity)
            adapter = customAdapter
        }
    }

    override fun showCats(listCats: List<Cat>) {
        customAdapter.updateItems(listCats)
    }

    override fun showError(messageError: String) {
        Toast.makeText(applicationContext, messageError, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessfulResultDownload() {
        Toast.makeText(applicationContext, "Картинка загружена", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1000
    }
}