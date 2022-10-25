package com.astep.bookoid.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingAndToastFragment<T : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding: T? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun toast(text: String, isLong: Boolean = false) {
        Toast.makeText(
            this.context,
            text,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT,
        ).show()
    }

    protected fun toast(@StringRes stringRes: Int, isLong: Boolean = false) {
        Toast.makeText(
            this.context,
            stringRes,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT,
        ).show()
    }

}
