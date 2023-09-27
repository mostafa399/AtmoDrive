package com.mostafahelal.AtmoDrive.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mostafahelal.AtmoDrive.R

data class ItemModel(val imageResource: Int, val location: String, val city: String)
class MyAdapter(private val itemList: List<ItemModel>):  RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]

        // Bind data to the views
        holder.imageItemRecyclerView.setImageResource(currentItem.imageResource)
        holder.textLocation.text = currentItem.location
        holder.textCity.text = currentItem.city
    }

    override fun getItemCount() = itemList.size
}
class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageItemRecyclerView: ImageView = itemView.findViewById(R.id.imageItemRecyclerView)
    val textLocation: TextView = itemView.findViewById(R.id.textlocation)
    val textCity: TextView = itemView.findViewById(R.id.textCity)
}