package com.astep.bookoid.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object DebugLogger {
    fun v(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.VERBOSE, classNameFrom, tag, msg)
    fun d(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.DEBUG, classNameFrom, tag, msg)
    fun i(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.INFO, classNameFrom, tag, msg)
    fun w(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.WARN, classNameFrom, tag, msg)
    fun e(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.ERROR, classNameFrom, tag, msg)
    fun a(tag: String? = null, msg: String, classNameFrom: Any? = null) =
        printlnIfDebug(Log.ASSERT, classNameFrom, tag, msg)

    private fun printlnIfDebug(priority: Int, classNameFrom: Any?, tag: String?, msg: String) {
        if (isShowing()) {
            Log.println(
                priority,
                listOfNotNull(
                    TAG_DEFAULT,
                    classNameFrom?.let { it::class.simpleName },
                    tag?.trim(),
                ).joinToString(separator = "/", postfix = "/"),
                msg
            )
        }
    }

    fun toast(context: Context, text: String, isLong: Boolean = false) {
        if (isShowing()) {
            Toast.makeText(
                context,
                "[Debug] " + text,
                if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun isShowing(): Boolean = true // BuildConfig.DEBUG

    private const val TAG_DEFAULT = "AStep"

}
