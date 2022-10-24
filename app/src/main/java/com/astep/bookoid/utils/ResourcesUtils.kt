package com.astep.bookoid.utils

import android.app.Application
import androidx.annotation.ArrayRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

object ResourcesUtils {

    private var _application: Application? = null
    private val application: Application
        get() = _application!!


    fun registerApplication(app: Application) {
        _application = app
    }


    fun getStringFromResArrayByID(
        @ArrayRes
        arrayRes: Int,
        id: Int,
        defaultId: Int = -1, // is in indices -> specified item is default
        // is out of indices -> last item is default
        substitution: String = ""
    ): String {
        val array: Array<String> = application.applicationContext.resources.getStringArray(arrayRes)
        val defaultIdOrLast = if (defaultId in array.indices) defaultId else array.lastIndex
        val defaultValue = array.getOrElse(defaultIdOrLast, { "" }) // if array.count == 0

        return array.getOrElse(id, { defaultValue }).replace("_", substitution)
    }


    fun getIntResource(@IntegerRes res: Int): Int =
        application.applicationContext.resources.getInteger(res)


    fun getStringResource(
        @StringRes res: Int,
        vararg substitution: String
    ) = application.applicationContext.resources.getString(res, *substitution)


    fun getStringArray (@ArrayRes arrayRes: Int): Array<String> =
        application.applicationContext.resources.getStringArray(arrayRes)


/*
    fun getMoneyAsString(sum: Money): String =
        if (sum.asLong100() % 100 == 0L) {
            getStringResource(R.string.money__as_whole_number, (sum.asLong100() / 100L).toString())
        } else {
            val whole = (sum.asLong100() / 100L).toString()
            val reminder = (sum.asLong100() % 100L).toString().let {
                if (it.length == 1) "0${it}" else it
            }
            getStringResource(R.string.money__as_fractional_number, whole, reminder)
        }


 */


}