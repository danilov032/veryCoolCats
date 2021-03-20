package com.example.catstestapp.ui.favorites

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catstestapp.CustomAdapter
import com.example.catstestapp.DI.AppModule
import com.example.catstestapp.DI.DaggerAppComponent
import com.example.catstestapp.R
import com.example.catstestapp.models.Cat
import kotlinx.android.synthetic.main.activity_favourites.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavouritesActivity : MvpAppCompatActivity(),
    FavouritesContractView {

    val customAdapter: CustomAdapter by lazy { CustomAdapter(
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
            .injectFavourites(this)

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

    override fun showCats(list: List<Cat>) {
        customAdapter.updateItems(list)
    }

    override fun showError(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun downloadCatOk() {
        Toast.makeText(applicationContext, "Картинка загружена", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1000
    }
}