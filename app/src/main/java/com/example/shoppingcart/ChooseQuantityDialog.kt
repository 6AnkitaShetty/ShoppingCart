package com.example.shoppingcart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.DialogFragment
import com.example.shoppingcart.databinding.DialogChooseQuantityBinding

class ChooseQuantityDialog : DialogFragment() {
    // data binding
    private lateinit var mBinding: DialogChooseQuantityBinding


    override fun onCreateView(
        inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
    ): View {
        mBinding = DialogChooseQuantityBinding.inflate(inflater)
        mBinding.iMainActivity = activity as IMainActivity?
        mBinding.listView.onItemClickListener = mOnItemClickListener
        mBinding.closeDialog.setOnClickListener(mCloseDialogListener)
        return mBinding.root
    }

    private var mOnItemClickListener =
        OnItemClickListener { adapterView, view, i, l ->
            Log.d(
                TAG,
                "onItemSelected: selected: " + adapterView.getItemAtPosition(i)
            )
            mBinding.iMainActivity!!.setQuantity(adapterView.getItemAtPosition(i).toString().toInt ())
            dialog?.dismiss()
        }
    private var mCloseDialogListener =
        View.OnClickListener { dialog?.dismiss() }

    companion object {
        private const val TAG = "ChooseQuantityDialog"
    }
}
