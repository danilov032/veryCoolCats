package com.example.catstestapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catstestapp.domain.models.ModelCatFavourites
import io.reactivex.Single

@Dao
interface ReadoutModelDao {

    @Query("SELECT * FROM ModelCatFavourites")
    fun getCats(): Single<List<ModelCatFavourites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(vararg todo: ModelCatFavourites)

    @Query("DELETE from ModelCatFavourites WHERE url = :urlStr")
    fun deleteCat(urlStr: String)

    @Query("DELETE FROM ModelCatFavourites")
    fun nukeTable()
}