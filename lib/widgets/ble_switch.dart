import 'package:ble_poc/ble_cubit.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class BLESwitch extends StatefulWidget {
  const BLESwitch({Key? key}) : super(key: key);

  @override
  State<BLESwitch> createState() => _BLESwitchState();
}

class _BLESwitchState extends State<BLESwitch> {



  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<BleCubit, BleState>(
      builder: (context, state) {
        return Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            const Text(
              'Bluetooth',
              style: TextStyle(fontWeight: FontWeight.w400, fontSize: 18),
            ),
            CupertinoSwitch(
              value: state.isSwitch,
              onChanged: (bool value) {
             BlocProvider.of<BleCubit>(context).changeBleStatus();
              },
            ),
          ],
        );
      },
    );
  }
}
