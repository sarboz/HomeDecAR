package com.sarboz.homedecar.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sarboz.homedecar.Models.Product
import com.sarboz.homedecar.R
import com.sarboz.homedecar.Utils.OnItemClickListener
import pl.droidsonroids.gif.GifImageView

class ProductsAdapter(
    options: FirebaseRecyclerOptions<Product>,
    val clickListener: OnItemClickListener
) : FirebaseRecyclerAdapter<
        Product, ProductsAdapter.Holder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int, model: Product) {
        holder.price.text = model.price
        holder.name.text = model.name
        Glide.with(holder.pictire.context)
            .load(model.pictureUrl)
            .into(holder.pictire)

        val cardView = holder.name.parent.parent as CardView
        cardView.setOnClickListener {
            clickListener.OnItemClickListener(model)
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.price)
        val name: TextView = view.findViewById(R.id.name)
        val pictire: GifImageView = view.findViewById(R.id.imageView)
    }
}