package com.example.catstestapp.presentation.holders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catstestapp.domain.models.Cat
import kotlinx.android.synthetic.main.recycler_item.view.*

class CatsViewHolder(
    private val view: View,
    private val favoriteListenerInFavourites: (Cat) -> Unit,
    private val favoriteListenerDownload: (Cat) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val imageView = view.itemIm
    private val buttonDownload = view.floatingActionButtonDownload
    private val buttonFavourites = view.floatingActionButtonInFavourites

    fun bindItems(cat: Cat) {

        buttonDownload.setOnClickListener { favoriteListenerDownload(cat) }
        buttonFavourites.setOnClickListener { favoriteListenerInFavourites(cat) }

        buttonFavourites.setColorFilter(
            if (cat.isFavourites)Color.parseColor("#FFCD3166")
            else  Color.parseColor("#000000")
        )

        Glide
            .with(view.context)
            .load(cat.url)
            .fitCenter()
            .into(imageView)
    }
}