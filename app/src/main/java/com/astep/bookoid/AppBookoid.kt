package com.astep.bookoid

import android.app.Application
import com.astep.bookoid.utils.ResourcesUtils

class AppBookoid : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourcesUtils.registerApplication(this)
//        Database.init(this)
//        AndroidThreeTen.init(this)
    }


}