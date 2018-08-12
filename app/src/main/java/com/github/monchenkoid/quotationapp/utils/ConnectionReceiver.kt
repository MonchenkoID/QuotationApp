package com.github.monchenkoid.quotationapp.utils

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.github.monchenkoid.quotationapp.CustomApplication


class ConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (connectionReceiverListener != null) {
            connectionReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }


    interface ConnectionReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {

        var connectionReceiverListener: ConnectionReceiverListener? = null

        val isConnected: Boolean
            get() {
                val connectivityManager = CustomApplication.mInstance.applicationContext
                        .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connectivityManager.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
    }
}