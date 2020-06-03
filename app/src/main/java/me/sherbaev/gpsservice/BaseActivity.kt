package me.sherbaev.gpsservice

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class BaseActivity : AppCompatActivity() {
    private val REQUEST_CODE = 102
    fun checkPermission(): Boolean {
        return if (checkAllPermission()) {
            permissionGranted()
            true
        } else {
            requestPermission()
            false
        }
    }

    private fun requestPermission() {
        if (permissions != null && permissions!!.isNotEmpty()) ActivityCompat.requestPermissions(
            this,
            permissions!!,
            REQUEST_CODE
        )
    }

    private fun checkAllPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissions != null && permissions!!.isNotEmpty()) {
                val permissions = permissions
                val permissionLength = permissions!!.size
                for (i in 0 until permissionLength) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            permissions[i]
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
                true
            } else {
                false
            }
        } else true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                permissionGranted()
            } else {
                permissionDenied()
            }
        }
    }

    abstract val permissions: Array<String>?
    abstract fun permissionGranted()
    protected abstract fun permissionDenied()
}