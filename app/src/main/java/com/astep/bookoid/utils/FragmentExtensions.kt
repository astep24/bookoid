package com.astep.bookoid.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String, isLong: Boolean = false) {
    Toast.makeText(
        this.context,
        text,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT,
    ).show()
}

fun Fragment.toast(@StringRes stringRes: Int, isLong: Boolean = false) {
    Toast.makeText(
        this.context,
        stringRes,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT,
    ).show()
}