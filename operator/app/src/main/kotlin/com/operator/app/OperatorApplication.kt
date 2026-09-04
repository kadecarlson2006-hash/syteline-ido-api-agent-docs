package com.operator.app

import android.app.Application
import com.operator.app.di.OperatorContainer

class OperatorApplication : Application() {
    lateinit var container: OperatorContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = OperatorContainer(this)
    }
}
