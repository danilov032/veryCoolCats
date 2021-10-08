package com.example.catstestapp.DI

import android.app.Application
import com.example.catstestapp.ApiService
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
        return MainRepository(apiService)
    }
    @get:Provides
    val apiService = ApiService.create()

    @Provides
    fun providesDbAbstract(): ReadoutModelDao {
        return dbAbstract.getDatabase(application).catsDao()
    }

    @Provides
    fun provideDownloadRepository(): DownloadRepository {
        return DownloadRepository(application.applicationContext)
    }
}