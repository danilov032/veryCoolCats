package com.example.catstestapp.di

import android.app.Application
import com.example.catstestapp.data.api.ApiService
import com.example.catstestapp.data.mappers.CatModelMapper
import com.example.catstestapp.data.datastore.CatsDataStore
import com.example.catstestapp.data.db.ReadoutModelDao
import com.example.catstestapp.data.repositories.DownloadRepository
import com.example.catstestapp.data.repositories.MainRepository
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