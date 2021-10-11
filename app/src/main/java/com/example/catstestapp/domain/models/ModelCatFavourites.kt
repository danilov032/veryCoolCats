package com.example.catstestapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ModelCatFavourites (
    @PrimaryKey(autoGenerate = true) var  id: Int? = null,
    @ColumnInfo var url: String){
}