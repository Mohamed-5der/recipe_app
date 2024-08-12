package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recipeapp.model.Category

class AdapterFood(var itemList: List<Category>, val onItemClick: (id: String) -> Unit) : RecyclerView.Adapter<AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return AdapterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item =itemList.get(position)
        holder.bind(item, onItemClick)
    }
    fun updateData(newData: List<Category>) {
        itemList =newData
        notifyDataSetChanged()
    }
}
class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title=itemView.findViewById<TextView>(R.id.title)
    val description=itemView.findViewById<TextView>(R.id.subtitle)
    val image=itemView.findViewById<ImageView>(R.id.image)
    val addFavorite=itemView.findViewById<ImageButton>(R.id.add_to_favorite)

    fun bind(category: Category, onItemClick: (id:String) -> Unit) {
        var addFav :Boolean =false
        title.text=category.strCategory
        description.text=category.strCategoryDescription
        image.load(category.strCategoryThumb)
        itemView.setOnClickListener {
            onItemClick(category.idCategory)
        }
        addFavorite.setOnClickListener {
            if (addFav==false) {
                addFavorite.setImageResource(R.drawable.baseline_favorite_24)
                addFav =true
            }else{
                addFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                addFav =false

            }
        }

    }


}