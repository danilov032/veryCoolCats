package com.example.catstestapp.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catstestapp.models.ModelCatFavourites
import io.reactivex.Observable

@Dao
interface ReadoutModelDao {

    @Query("SELECT * FROM ModelCatFavourites")
    fun getCats(): Observable<List<ModelCatFavourites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(vararg todo: ModelCatFavourites)

    @Query("DELETE from ModelCatFavourites WHERE url = :urlStr")
    fun deleteCat(urlStr: String)

    @Query("DELETE FROM ModelCatFavourites")
    fun nukeTable()
}