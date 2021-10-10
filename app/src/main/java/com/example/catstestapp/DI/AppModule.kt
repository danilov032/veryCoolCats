package com.example.catstestapp.DI

import android.app.Application
import com.example.catstestapp.ApiService
import com.example.catstestapp.mappers.CatModelMapper
import com.example.catstestapp.CatsDataStore
import com.example.catstestapp.DB.ReadoutModelDao
import com.example.catstestapp.ui.DownloadRepository
import com.example.catstestapp.ui.main.MainRepository
import com.example.mytestproject.dbAbstract
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideMainRepository(): MainRepository {
        return MainRepository(apiService, dataStore, mapper)
    }
    @get:Provides
    val apiService = ApiService.create()

    @get:Provides
    val dataStore = CatsDataStore()

    @get:Provides
    val mapper = CatModelMapper()

    @Provides
    fun providesDbAbstract(): ReadoutModelDao {
        return dbAbstract.getDatabase(application).catsDao()
    }

    @Provides
    fun provideDownloadRepository(): DownloadRepository {
        return DownloadRepository(application.applicationContext)
    }
}