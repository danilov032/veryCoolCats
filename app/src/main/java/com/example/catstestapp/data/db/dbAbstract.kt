package com.example.mytestproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catstestapp.data.db.ReadoutModelDao
import com.example.catstestapp.domain.models.ModelCatFavourites

@Database(entities = [ModelCatFavourites::class],version = 1)
abstract class dbAbstract: RoomDatabase(){
    abstract fun catsDao(): ReadoutModelDao

    companion object {
        fun getDatabase(context: Context): dbAbstract {
            return  Room.databaseBuilder(
                context.applicationContext,
                dbAbstract::class.java,
                "myDB"
            ).build()
        }
    }
}