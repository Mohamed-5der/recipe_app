package com.example.recipeapp.presention.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Favorite

class AdapterFavorite(
    private var itemList: List<Favorite>,
    private val onItemClick: (id: String) -> Unit,
    private val onFavoriteClick: (addFav: Boolean, item: Favorite) -> Unit
) : RecyclerView.Adapter<AdapterFavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return AdapterFavViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: AdapterFavViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, onItemClick, onFavoriteClick)
    }

    fun updateData(newData: List<Favorite>) {
        itemList = newData
        notifyDataSetChanged()
    }
}

class AdapterFavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.subtitle)
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val addFavorite: ImageButton = itemView.findViewById(R.id.add_to_favorite)

    fun bind(favorite: Favorite, onItemClick: (id: String) -> Unit, onFavoriteClick: (addFav: Boolean, item: Favorite) -> Unit) {
        var isFavorite = true // Assume all items are favorites; update this based on your logic
        title.text = favorite.strCategory
        description.text = favorite.strCategoryDescription
        image.load(favorite.strCategoryThumb)

        itemView.setOnClickListener {
            onItemClick(favorite.idCategory)
        }

        addFavorite.setOnClickListener {
            isFavorite = !isFavorite
            addFavorite.setImageResource(
                if (isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
            onFavoriteClick(isFavorite, favorite)
        }
    }
}
