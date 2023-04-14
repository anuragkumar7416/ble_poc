import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/services.dart';

part 'ble_state.dart';

class BleCubit extends Cubit<BleState> {
  BleCubit() : super(BleInitial(false)){
    getBLEStatus();

  }




  static const platform = MethodChannel('flutter.native/ble');

  Future<void> getBLEStatus() async {
    try {
     bool isSwitch = await platform.invokeMethod('getBleStatus');
      emit(BleChangedStatus(isSwitch));

    } on PlatformException catch (e) {
      throw Exception('Something Wrong Occurred----> $e');
    }
  }
  void changeBleStatus() async {
    try {
      bool isSwitch = await platform.invokeMethod('changeBleStatus');
      emit(BleChangedStatus(isSwitch));
    } on PlatformException catch (e) {
      throw Exception('Something Wrong Occurred----> $e');
    }
  }

}
