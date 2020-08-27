package com.example.shoppingcart.models

import android.graphics.drawable.Drawable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shoppingcart.BR

class ProductViewModel : BaseObservable() {
    @get:Bindable
    var product: Product? = null
        set(product) {
            field = product
            notifyPropertyChanged(BR.product)
        }

    @get:Bindable
    var quantity = 0
        set(quantity) {
            field = quantity
            notifyPropertyChanged(BR.quantity)
        }

    @get:Bindable
    var imageVisibility = false
        private set

    fun setImageVisible(imageVisible: Boolean) {
        imageVisibility = imageVisible
        notifyPropertyChanged(BR.imageVisibility)
    }

    fun getCustomRequestListener(): RequestListener<Drawable>? {
       return (object : RequestListener<Drawable> {
           override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
               return false
           }
           override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
               setImageVisible(true)
               return false
           }
       })
    }
}







