package com.example.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shoppingcart.adapters.ProductsAdapter
import com.example.shoppingcart.databinding.FragmentMainBinding
import com.example.shoppingcart.models.Product
import com.example.shoppingcart.util.Products
import java.util.*

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mBinding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater)
        mBinding.swipeRefreshLayout.setOnRefreshListener(this)

        setupProductsList()
        return mBinding.root

    }

    private fun setupProductsList() {
        val products = Products()
        val productList: MutableList<Product> = ArrayList<Product>()
        productList.addAll((products.PRODUCTS))
        mBinding.products = productList
    }

    override fun onRefresh() {
        val products = Products()
        val productList: MutableList<Product> = ArrayList()
        productList.addAll((products.PRODUCTS))
        (mBinding.recyclervView.adapter as ProductsAdapter?)!!.refreshList(productList)
        onItemsLoadComplete()
    }

    fun onItemsLoadComplete() {
        mBinding.recyclervView.adapter!!.notifyDataSetChanged()
        mBinding.swipeRefreshLayout.isRefreshing = false
    }
}