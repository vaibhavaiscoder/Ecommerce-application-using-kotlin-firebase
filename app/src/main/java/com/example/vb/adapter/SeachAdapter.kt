package com.example.ecomvb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomvb.R
import com.example.ecomvb.activity.ProductDetailsActivity
import com.example.ecomvb.databinding.SearchItemProductLayoutBinding
import com.example.ecomvb.model.ProductModel

class SeachAdapter(var searchList: List<ProductModel>) :
    RecyclerView.Adapter<SeachAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = SearchItemProductLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item_product_layout, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.itemProductTextView.text = searchList[position].productName
        Glide.with(holder.itemView.context)
            .load(searchList[position].productCoverImg)
            .into(holder.binding.itemProductImageView)
//        Glide.with(context).load(searchList[position].productCoverImg).into(holder.binding.itemProductImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ProductDetailsActivity::class.java)
            intent.putExtra("id",searchList[position].productId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}
