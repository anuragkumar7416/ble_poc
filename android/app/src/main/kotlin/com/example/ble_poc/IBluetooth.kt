package com.example.ble_poc

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

interface IBluetooth {

    fun bluetoothEnable(isEnable : Boolean, call: MethodCall,
                        result: MethodChannel.Result)
}