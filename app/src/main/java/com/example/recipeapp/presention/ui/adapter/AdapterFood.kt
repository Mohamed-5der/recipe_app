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
import com.example.recipeapp.data.model.Category
class AdapterFood(
    var itemList: List<Category>,
    val onItemClick: (id: String) -> Unit,
    val onFavoriteClick: (addFav: Boolean, item: Category) -> Unit
) : RecyclerView.Adapter<AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return AdapterViewHolder(v)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, onItemClick, onFavoriteClick)
    }

    fun updateData(newData: List<Category>) {
        itemList = newData
        notifyDataSetChanged()
    }
}

class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val description = itemView.findViewById<TextView>(R.id.subtitle)
    private val image = itemView.findViewById<ImageView>(R.id.image)
    private val addFavorite = itemView.findViewById<ImageButton>(R.id.add_to_favorite)

    fun bind(category: Category, onItemClick: (id: String) -> Unit, onFavoriteClick: (addFav: Boolean, item: Category) -> Unit) {
        var addFav = false
        title.text = category.strCategory
        description.text = category.strCategoryDescription
        image.load(category.strCategoryThumb)
        itemView.setOnClickListener {
            onItemClick(category.idCategory)
        }
        addFavorite.setOnClickListener {
            addFav = !addFav
            if (addFav) {
                addFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                addFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            onFavoriteClick(addFav, category)
        }
    }
}
