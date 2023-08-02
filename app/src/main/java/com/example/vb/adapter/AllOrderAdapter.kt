package com.example.ecomvb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomvb.databinding.AllOrderItemLayoutBinding
import com.example.ecomvb.model.AllOrderModel

class AllOrderAdapter(val list: ArrayList<AllOrderModel>, val context: Context) :
    RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context,ProductDetailsActivity::class.java)
//            intent.putExtra("id",list[position].productId)
//            context.startActivity(intent)
//        }

        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price
        holder.binding.orderId.text = list[position].orderId
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.productImage)

//        holder.binding.productImage. = list[position].productCoverImg

        when (list[position].status) {
            "Ordered" -> {
                holder.binding.productStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"

            }
            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"

            }
            "Canceled" -> {
                holder.binding.productStatus.text = "Canceled"
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}