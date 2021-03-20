package com.example.catstestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catstestapp.models.Cat

class CustomAdapter(
    private val favoriteListener: (Cat) -> Unit,
    private val downloadListener: (Cat) -> Unit
) : RecyclerView.Adapter<CatsViewHolder>() {

    private val items: MutableList<Cat> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return CatsViewHolder(view, favoriteListener, downloadListener)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(list: List<Cat>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}