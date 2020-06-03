package me.sherbaev.gpsservice

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

const val LOCATION_SERVICE_ID = 123456
const val ACTION_START_LOCATION = "me.sherbaev.gpstracker.ACTION_START_LOCATION"
const val ACTION_STOP_LOCATION = "me.sherbaev.gpstracker.ACTION_STOP_LOCATION"
const val LONGLAT = "me.sherbaev.gpstracker.LONGLAT"

fun ArrayList<String>.average(): String {
    var long = 0.0
    var lat = 0.0
    this.forEach {
        long += it.substringBefore("/").toDouble()
        lat += it.substringAfter("/").toDouble()
    }
    return "${long/this.size}/${lat/this.size}"
}