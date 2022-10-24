package com.astep.bookoid.utils

import android.content.Context
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.astep.bookoid.R

private fun MenuItem.setColor(context: Context, @ColorRes color: Int) {
    this?.icon?.let {
        DrawableCompat.setTint(
            it,
            ContextCompat.getColor(context, color)
        )
    }
}


fun MenuItem.setColorOn(context: Context) {
    this.setColor(context, R.color.menu_item_on)
}


fun MenuItem.setColorOff(context: Context) {
    this.setColor(context, R.color.menu_item_off)
}


