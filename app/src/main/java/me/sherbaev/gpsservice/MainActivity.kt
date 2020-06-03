package me.sherbaev.gpsservice

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val permissions: Array<String>?
        get() = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun permissionGranted() {
        startLocationService()
    }

    override fun permissionDenied() {
        this.toast("Permission Denied")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart.setOnClickListener {
            if (checkPermission()) {
                startLocationService()
            } else {
                this.toast("Permission Denied")
            }
        }
        btnStop.setOnClickListener {
            stopLocationService()
        }
    }

    private fun isLocating(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.getRunningServices(Int.MAX_VALUE)?.forEach { info ->
            if (LocationService::class.java.name == info.service.className) {
                if (info.foreground) {
                    return true
                }
            }
        }
        return false
    }

    private fun startLocationService() {
        if (isLocating().not()) {
            val intent = Intent(applicationContext, LocationService::class.java)
            intent.action = ACTION_START_LOCATION
            startService(intent)
            this.toast("Service started")
        }
    }

    private fun stopLocationService() {
        if (isLocating()) {
            val intent = Intent(applicationContext, LocationService::class.java)
            intent.action = ACTION_STOP_LOCATION
            startService(intent)
            this.toast("Service stopped")
        }
    }

}