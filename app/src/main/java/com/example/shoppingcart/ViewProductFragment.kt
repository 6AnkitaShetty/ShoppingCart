package com.example.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.shoppingcart.databinding.FragmentViewProductBinding
import com.example.shoppingcart.models.Product
import com.example.shoppingcart.models.ProductViewModel

class ViewProductFragment : Fragment() {
    // Data binding
    lateinit var mBinding: FragmentViewProductBinding
    private  var mProduct : Product? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            mProduct = bundle.getParcelable(getString(R.string.intent_product))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentViewProductBinding.inflate(inflater)
        mBinding.iMainActivity = activity as IMainActivity?
        val viewProductModel = ProductViewModel()
        viewProductModel.product = (mProduct)
        viewProductModel.quantity = (1)

        mBinding.productView = viewProductModel
        return mBinding.root
    }

    companion object {
        private const val TAG = "ViewProductFragment"
    }
}
