package com.example.shoppingcart.databinding

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.shoppingcart.R.drawable

object GlideBindingAdapters {
    private const val TAG = "GlideBindingAdapters"
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImage(view: ImageView, imageUrl: String?) {
        val context = view.context
        val options: RequestOptions = RequestOptions()
            .placeholder(drawable.ic_launcher_background)
            .error(drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImage(view: ImageView, imageUrl: Int) {
        val context = view.context
        val options: RequestOptions = RequestOptions()
            .placeholder(drawable.ic_launcher_background)
            .error(drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(*["requestListener", "imageResource"])
    fun bindRequestListener(view: ImageView, listener: RequestListener<Drawable>, imageResource: Int
    ) {
        Log.d(TAG, "bindRequestListener: setting image resource.")
        val context = view.context
        val options = RequestOptions()
            .placeholder(drawable.ic_launcher_background)
            .error(drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(imageResource)
            .listener(listener)
            .into(view)
    }
}