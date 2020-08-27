package com.example.shoppingcart

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoppingcart.databinding.FragmentViewCartBinding
import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.models.CartViewModel
import com.example.shoppingcart.util.PreferenceKeys
import com.example.shoppingcart.util.Products
import java.util.*

class ViewCartFragment : Fragment() {
    //data binding
    lateinit var mBinding: FragmentViewCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentViewCartBinding.inflate(inflater)
        mBinding.iMainActivity = activity as IMainActivity?
        mBinding.iMainActivity?.setCartVisibility(true)
        getShoppingCartList()
        return mBinding.root
    }



    private fun getShoppingCartList() {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(activity)
        val serialNumbers = preferences.getStringSet(
            PreferenceKeys.shopping_cart,
            HashSet()
        )
        val products = Products()
        val cartItems: MutableList<CartItem> = ArrayList()
        for (serialNumber in serialNumbers!!) {
            val quantity = preferences.getInt(serialNumber, 0)
            cartItems.add(CartItem(products.PRODUCT_MAP[serialNumber]!!, quantity))
        }
        val cartViewModel = CartViewModel()
        cartViewModel.setCart(cartItems)
        mBinding.cartView = cartViewModel
    }

    fun updateCartItems() {
        getShoppingCartList()
    }

    override fun onDestroy() {
        mBinding.iMainActivity?.setCartVisibility(false)
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ViewCartFragment"
    }
}