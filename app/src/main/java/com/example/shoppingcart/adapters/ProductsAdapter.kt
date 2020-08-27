package com.example.shoppingcart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.IMainActivity
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ProductItemBinding
import com.example.shoppingcart.models.Product

class ProductsAdapter(private var mContext: Context, private var mProducts:MutableList<Product>) :RecyclerView.Adapter<ProductsAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding: ProductItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.product_item, parent, false
        )
        return BindingHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return mProducts.size
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
       val product = mProducts[position]
        holder.binding.product = product
        holder.binding.iMainActivity = mContext as IMainActivity
        holder.binding.executePendingBindings()
    }

    fun refreshList(products: MutableList<Product>) {
        mProducts.clear()
        mProducts.addAll(products)
        notifyDataSetChanged()
    }

    class BindingHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding: ProductItemBinding = DataBindingUtil.bind(itemView)!!
    }
}