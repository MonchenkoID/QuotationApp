package com.github.monchenkoid.quotationapp

import android.app.Application
import com.github.monchenkoid.quotationapp.utils.ConnectionReceiver

class CustomApplication : Application()  {

    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }

    companion object {
        lateinit var mInstance: CustomApplication
            private set
    }

    fun setConnectionListener(listener: ConnectionReceiver.ConnectionReceiverListener) {
        ConnectionReceiver.connectionReceiverListener = listener
    }
}