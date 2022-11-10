package com.astep.bookoid.ui

import android.app.Application
import com.astep.bookoid.data.db.Database

class AppBookoid : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}