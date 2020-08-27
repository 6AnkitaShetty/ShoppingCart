package com.example.shoppingcart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.CartItemBinding
import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.models.CartItemViewModel


class CartItemAdapter(private var mContext: Context, private var mCartItems: MutableList<CartItem>
) : RecyclerView.Adapter<CartItemAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding: CartItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.cart_item, parent, false
        )
        return BindingHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val cartItem: CartItem = mCartItems[position]
        val viewModel = CartItemViewModel()
        viewModel.cartItem = (cartItem)
        holder.binding.cartItemView = viewModel
        holder.binding.executePendingBindings()
    }

    fun updateCartItems(cartItems: List<CartItem>) {
        mCartItems.clear()
        mCartItems.addAll(cartItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mCartItems.size
    }


    class BindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: CartItemBinding = DataBindingUtil.bind(itemView)!!

    }

    companion object {
        private const val TAG = "CartItemAdapter"
    }


}