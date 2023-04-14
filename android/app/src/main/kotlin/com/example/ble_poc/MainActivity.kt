package com.example.ble_poc

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity(), IBluetooth {

    private val CHANNEL = "flutter.native/ble"
    private val REQUEST_ENABLE_BT = 0
    private val REQUEST_DISABLE_BT = 1;

    @RequiresApi(Build.VERSION_CODES.S)
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->

            PermissionManager.wifiPermission(this, this, call, result)

        }
    }

    private fun getBleStatus(): Boolean {
        val bleStatus: Boolean
        val mBluetoothAdapter: BluetoothAdapter
        val bluetoothManager = (applicationContext).getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter
        bleStatus = mBluetoothAdapter.isEnabled;

        return bleStatus
    }


    @SuppressLint("MissingPermission")
    private fun changeBleStatus(): Boolean {

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter






        return if (bluetoothAdapter?.isEnabled == false) {

            bluetoothAdapter?.enable()
            true


        }else{
            bluetoothAdapter?.disable()
            false


        }

    }

    @SuppressLint("MissingPermission")
    private fun getPairedDevices(): HashMap<String,Any> {
        var bleStatus: Boolean = getBleStatus()
        val flutterDataJson: HashMap<String,Any>;
        var list: MutableList<String> = mutableListOf();
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter


        if (bluetoothAdapter?.isEnabled==true) {
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            if (pairedDevices != null) {
                for (btDevice in pairedDevices) {
                    list.add(btDevice.name)
                }
            }


           flutterDataJson = hashMapOf("list" to list)


            Log.i("Hello", "SS ${flutterDataJson}")

            return flutterDataJson;
        } else {
            bluetoothAdapter?.enable()
            return hashMapOf();

        }

    }

    override fun bluetoothEnable(
        isEnable: Boolean, call: MethodCall,
        result: MethodChannel.Result
    ) {
        if (isEnable) {
            if (call.method == "getBleStatus") {

                val bleStatus = getBleStatus()

                if (bleStatus != null) {
                    result.success(bleStatus)
                } else {
                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
                }
            } else if (call.method == "changeBleStatus") {
                val bleStatus = changeBleStatus()

                if (bleStatus != null) {

                    result.success(bleStatus)
                } else {
                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
                }

            } else if (call.method == "getPairedDevices") {
                val bleStatus = getPairedDevices()

                if (bleStatus != null) {

                    result.success(bleStatus)
                } else {
                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
                }

            } else {
                result.notImplemented()
            }

        }
    }


}

