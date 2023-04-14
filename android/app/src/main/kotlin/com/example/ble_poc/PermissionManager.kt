package com.example.ble_poc

import android.Manifest
import android.os.Build
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

object PermissionManager {

    fun wifiPermission(
        activity: MainActivity,
        callbacks: IBluetooth,
        call: MethodCall,
        result: MethodChannel.Result
    ) {

        Log.i("EEEEEEEEEE", "SDK ${android.os.Build.VERSION.SDK_INT}")

        val permission : MutableList<String> = ArrayList()
        permission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permission.add(Manifest.permission.BLUETOOTH)
        permission.add(Manifest.permission.BLUETOOTH_ADMIN)

        Log.i("SSSS", "SS ${android.os.Build.VERSION.SDK_INT}")
        // Android 12
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
            permission.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission.add(Manifest.permission.NEARBY_WIFI_DEVICES)
        }

        Dexter.withContext(activity).withPermissions(permission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    var d = report.deniedPermissionResponses

                    d?.forEach {
                        Log.i("AAAAA", "IS ALL ${it.permissionName}")
                    }

                    var d2 = report.grantedPermissionResponses
                    Log.i("AAAAA", "IS SS")
                    d2?.forEach {
                        Log.i("EEEEEEEEEE", "IS ALL ${it.permissionName}")
                    }

                    val isAll = report.areAllPermissionsGranted()

                    Log.i("AAAAA", "IS ALL ${isAll}")
                   callbacks?.bluetoothEnable(isAll, call, result)

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {

                    Log.i("AAAAA", "onPermissionRationaleShouldBeShown")
                    token?.continuePermissionRequest()

                }
            }).withErrorListener {

            }
            .onSameThread()
            .check()
    }



}