import 'package:ble_poc/ble_cubit.dart';
import 'package:ble_poc/widgets/ble_switch.dart';
import 'package:ble_poc/widgets/paired_devices.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class BLEPage extends StatefulWidget {
  const BLEPage({Key? key}) : super(key: key);

  @override
  State<BLEPage> createState() => _BLEPageState();
}

class _BLEPageState extends State<BLEPage> {
  bool? isSwitch;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => BleCubit(),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Bluetooth'),
        ),
        body: BlocBuilder<BleCubit, BleState>(
          builder: (context, state) {
            return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Column(
                children:  [
                  const BLESwitch(),
                  state.isSwitch?PairedDevices():SizedBox(),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}
