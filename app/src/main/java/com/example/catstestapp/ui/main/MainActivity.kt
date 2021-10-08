package com.example.catstestapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.catstestapp.ui.favorites.FavouritesActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(),
    MainContractView {

    @Inject
    lateinit var presenterLazy: dagger.Lazy<MainPresenter>

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
            .injectMainActivity(this)

        return presenterLazy.get()
    }

    val customAdapter: CustomAdapter by lazy { CustomAdapter(
        { cat -> presenter.onClickInFavorites(cat) },
        { cat -> presenter.onClickDownload(cat) }
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customAdapter
        }

        buttonFavourites.setOnClickListener { presenter.showFavourites() }
    }

    override fun showCats(list: List<Cat>) {
        customAdapter.updateItems(list)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun installPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun shoeSuccessfulResult() {
        Toast.makeText(applicationContext, "Картинка добавлена", Toast.LENGTH_SHORT).show()
    }

    override fun transitionFavouritesCats() {
        val intent = Intent(this, FavouritesActivity::class.java)
        startActivity(intent)
    }

    override fun showSuccessfulResultDownload() {
        Toast.makeText(applicationContext, "Картинка загружена", Toast.LENGTH_SHORT).show()
    }

    override fun showError(strErr: String) {
        Toast.makeText(applicationContext, strErr, Toast.LENGTH_SHORT).show()
    }

    companion object{
           private const val STORAGE_PERMISSION_CODE = 1000
    }
}