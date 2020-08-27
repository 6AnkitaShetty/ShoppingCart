package com.example.shoppingcart

import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.shoppingcart.databinding.ActivityMainBinding
import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.models.CartViewModel
import com.example.shoppingcart.models.Product
import com.example.shoppingcart.util.PreferenceKeys
import com.example.shoppingcart.util.Products
import java.util.*

class MainActivity : AppCompatActivity(), IMainActivity {

    private val TAG = "MainActivity"
    private lateinit var mBinding: ActivityMainBinding

    //vars
    private var mClickToExit = false
    private var mCheckoutRunnable: Runnable? = null
    private var mCheckoutHandler: Handler? = null
    private var mCheckoutTimer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.cart.setOnTouchListener(CartTouchListener())
        mBinding.proceedToCheckout.setOnClickListener(mCheckOutListener)

        getShoppingCart()
        init()
    }

    private fun init() {
        val fragment = MainFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment, getString(R.string.fragment_main))
        transaction.commit()
    }

    override fun inflateViewProductFragment(product: Product) {
        Log.d(TAG, "inflateViewProductFragment: called.")

        val fragment = ViewProductFragment()

        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.intent_product), product)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.main_container,
            fragment,
            getString(R.string.fragment_view_product)
        )
        transaction.addToBackStack(getString(R.string.fragment_view_product))
        transaction.commit()
    }

    override fun showQuantityDialog() {
        Log.d(TAG, "showQuantityDialog: showing Quantity Dialog.")
        val dialog = ChooseQuantityDialog()
        dialog.show(supportFragmentManager, getString(R.string.dialog_choose_quantity))
    }

    override fun setQuantity(quantity: Int) {
        Log.d(TAG, "selectQuantity: selected quantity: $quantity")

        val fragment =
            supportFragmentManager.findFragmentByTag(getString(R.string.fragment_view_product)) as ViewProductFragment?
        fragment?.mBinding?.productView?.quantity = (quantity)
    }

    override fun addToCart(product: Product?, quantity: Int) {
        Log.d(TAG,
            "addToCart: adding " + quantity + " " + product?.title + "to cart."
        )

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()

        //add the new products serial number (if it hasn't already been added)

        //add the new products serial number (if it hasn't already been added)
        val serialNumbers = preferences.getStringSet(
            PreferenceKeys.shopping_cart,
            HashSet()
        )
        serialNumbers!!.add((product?.serial_number).toString())
        editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers)
        editor.commit()

        //add the quantity

        //add the quantity
        val currentQuantity =
            preferences.getInt((product?.serial_number).toString(), 0)

        //commit the updated quantity

        //commit the updated quantity
        editor.putInt(
            (product?.serial_number).toString(),
            currentQuantity + quantity
        )
        editor.commit()

        //reset the quantity in ViewProductFragment

        //reset the quantity in ViewProductFragment
        setQuantity(1)

        //update the bindings

        //update the bindings
        getShoppingCart()

        // notify the user

        // notify the user
        Toast.makeText(this, "added to cart", Toast.LENGTH_SHORT).show()
    }

    override fun inflateViewCartFragment() {
        var fragment =
            supportFragmentManager.findFragmentByTag(getString(R.string.fragment_view_cart)) as ViewCartFragment?
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment == null) {
            fragment = ViewCartFragment()
            transaction.replace(
                R.id.main_container,
                fragment,
                getString(R.string.fragment_view_cart)
            )
            transaction.addToBackStack(getString(R.string.fragment_view_cart))
            transaction.commit()
        }
    }

    override fun setCartVisibility(visibility: Boolean) {
        mBinding.cartView!!.isCartVisible = (visibility)

    }

    override fun updateQuantity(product: Product?, quantity: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()

        //add the quantity

        //add the quantity
        val currentQuantity =
            preferences.getInt((product?.serial_number).toString(), 0)

        //commit the updated quantity

        //commit the updated quantity
        editor.putInt(
            (product?.serial_number).toString(),
            currentQuantity + quantity
        )
        editor.commit()

        getShoppingCart()
    }

    override fun removeCartItem(cartItem: CartItem?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()

        editor.remove((cartItem?.product?.serial_number).toString())
        editor.commit()

        val serialNumbers = preferences.getStringSet(
            PreferenceKeys.shopping_cart,
            HashSet()
        )
        if (serialNumbers!!.size == 1) {
            editor.remove(PreferenceKeys.shopping_cart)
            editor.commit()
        } else {
            serialNumbers.remove((cartItem?.product?.serial_number).toString())
            editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers)
            editor.commit()
        }

        getShoppingCart()

        //remove the item from the list in ViewCartFragment

        //remove the item from the list in ViewCartFragment
        val fragment =
            supportFragmentManager.findFragmentByTag(getString(R.string.fragment_view_cart)) as ViewCartFragment?
        fragment?.updateCartItems()
    }

    private fun getShoppingCart() {
        Log.d(TAG, "getShoppingCart: getting shopping cart.")
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val serialNumbers = preferences.getStringSet(
            PreferenceKeys.shopping_cart,
            HashSet()
        )

        // Retrieve the quantities of each item from the cart
        val products = Products()
        val cartItems: MutableList<CartItem> = ArrayList()
        for (serialNumber in serialNumbers!!) {
            val quantity = preferences.getInt(serialNumber, 0)
            products.PRODUCT_MAP.get(serialNumber)?.let { CartItem(it, quantity) }?.let {
                cartItems.add(
                    it
                )
            }
        }
        val viewModel = CartViewModel()
        viewModel.setCart(cartItems)
        try {
            viewModel.isCartVisible = (mBinding.cartView!!.isCartVisible)
        } catch (e: NullPointerException) {
            Log.e(
                TAG,
                "getShoppingCart: NullPointerException: " + e.message
            )
        }
        mBinding.cartView = viewModel
    }

    fun checkout() {
        Log.d(TAG, "checkout: checking out.")
        mBinding.progressBar.visibility = View.VISIBLE
        mCheckoutHandler = Handler()
        mCheckoutRunnable = Runnable {
            mCheckoutHandler!!.postDelayed(mCheckoutRunnable, 200)
            mCheckoutTimer += 200
            if (mCheckoutTimer >= 1600) {
                emptyCart()
                mBinding.progressBar.visibility = View.GONE
                mCheckoutHandler!!.removeCallbacks(mCheckoutRunnable)
                mCheckoutTimer = 0
            }
        }
        mCheckoutRunnable!!.run()
    }

    private fun emptyCart() {
        Log.d(TAG, "emptyCart: emptying cart.")
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val serialNumbers = preferences.getStringSet(
            PreferenceKeys.shopping_cart,
            HashSet()
        )
        val editor = preferences.edit()
        for (serialNumber in serialNumbers!!) {
            editor.remove(serialNumber)
            editor.commit()
        }
        editor.remove(PreferenceKeys.shopping_cart)
        editor.commit()
        Toast.makeText(this, "thanks for shopping!", Toast.LENGTH_SHORT).show()
        removeViewCartFragment()
        getShoppingCart()
    }

    var mCheckOutListener =
        View.OnClickListener { checkout() }

    class CartTouchListener : OnTouchListener {
        override fun onTouch(
            view: View,
            motionEvent: MotionEvent
        ): Boolean {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                view.setBackgroundColor(view.context.resources.getColor(R.color.blue4))
                view.performClick()
                val iMainActivity = view.context as IMainActivity
                iMainActivity.inflateViewCartFragment()
            } else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(view.context.resources.getColor(R.color.blue6))
            }
            return true
        }
    }

    fun removeViewCartFragment() {
        supportFragmentManager.popBackStack()
        val fragment =
            supportFragmentManager.findFragmentByTag(getString(R.string.fragment_view_cart)) as ViewCartFragment?
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            transaction.remove(fragment)
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        Log.d(TAG, "onBackPressed: backstack count: $backStackCount")
        if (backStackCount == 0 && mClickToExit) {
            super.onBackPressed()
        }
        if (backStackCount == 0 && !mClickToExit) {
            Toast.makeText(this, "1 more click to exit.", Toast.LENGTH_SHORT).show()
            mClickToExit = true
        } else {
            mClickToExit = false
            super.onBackPressed()
        }
    }
}