package com.example.ecomvb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomvb.R
import com.example.ecomvb.databinding.SearchItemProductLayoutBinding
import com.example.ecomvb.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductAdapter2(var context : Context, var list : ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter2.ProductViewHolder>(){
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding = SearchItemProductLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item_product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.itemProductTextView.text = list[position].productName
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.itemProductImageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}