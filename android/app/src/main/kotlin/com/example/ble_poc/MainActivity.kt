package com.example.ble_poc

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.telecom.TelecomManager
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.lang.Exception


class MainActivity : FlutterActivity(), IBluetooth {

    private val CHANNEL = "flutter.native/call"
    private var listOfAvailableDevices = mutableListOf<String>()



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

    @SuppressLint("MissingPermission")
    private fun getAvailableDevices(): HashMap<String,Any> {
        var bleStatus: Boolean = getBleStatus()
        val flutterDataJson: HashMap<String,Any>;
        var list: MutableList<String> = mutableListOf();
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter


        if (bluetoothAdapter?.isEnabled==true) {
            bluetoothAdapter.startDiscovery()
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(receiver, filter)
            Log.i("FOUND","FOUND DEVICE==>${filter}")





            flutterDataJson = hashMapOf("list" to listOfAvailableDevices)


            Log.i("Hello", "SS ${flutterDataJson}")

            return flutterDataJson;
        } else {

            return hashMapOf();

        }

    }


    override fun bluetoothEnable(
        isEnable: Boolean, call: MethodCall,
        result: MethodChannel.Result
    ) {
        if(call.method == "makePhoneCall"){

            val callStatus = makePhoneCall()

                if(callStatus != null) {

                    result.success(callStatus)
                } else {
                    result.error("UNAVAILABLE", "Some error occured", null)
                }
        }else{
            result.notImplemented()

        }

//        if (isEnable) {
//            if (call.method == "getBleStatus") {
//
//                val bleStatus = getBleStatus()
//
//                if (bleStatus != null) {
//                    result.success(bleStatus)
//                } else {
//                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
//                }
//            } else if (call.method == "changeBleStatus") {
//                val bleStatus = changeBleStatus()
//
//                if (bleStatus != null) {
//
//                    result.success(bleStatus)
//                } else {
//                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
//                }
//
//            } else if (call.method == "getPairedDevices") {
//                val bleStatus = getPairedDevices()
//
//                if (bleStatus != null) {
//
//                    result.success(bleStatus)
//                } else {
//                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
//                }
//
//            }else if(call.method == "getAvailableDevices"){
//                val bleStatus = getAvailableDevices()
//
//                if (bleStatus != null) {
//
//                    result.success(bleStatus)
//                } else {
//                    result.error("UNAVAILABLE", "Bluetooth not available.", null)
//                }
//
//            } else {
//                result.notImplemented()
//            }
//
//        }
    }

    @SuppressLint("MissingPermission")
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName :String = device?.name.toString()
                    listOfAvailableDevices.add(deviceName);
                    val deviceHardwareAddress = device?.address // MAC address
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    private fun makePhoneCall():Boolean{
        return try{



            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+91 " +9354994778)
            startActivity(intent)

            true

        }catch (e: Exception){

            false

        }


    }


}

