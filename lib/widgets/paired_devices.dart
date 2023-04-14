

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../Hello.dart';

class PairedDevices extends StatefulWidget {
  const PairedDevices({Key? key}) : super(key: key);

  @override
  State<PairedDevices> createState() => _PairedDevicesState();
}

class _PairedDevicesState extends State<PairedDevices> {
  static const platform = MethodChannel('flutter.native/ble');


  Future<List<String>> _getPairedDevice() async {
    List<String> listOfPairedDevices = [];
    try {

      var list = await platform.invokeMethod('getPairedDevices');

      Hello value = Hello.fromJson(list);

      listOfPairedDevices.addAll(value.list??[]);

      setState(() {});
    } on PlatformException catch (e) {
      throw Exception('Something Wrong Occurred----> $e');
    }
    return listOfPairedDevices;
  }

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 30,),
          const Text('Paired Devices',style: TextStyle(fontWeight: FontWeight.w500,fontSize: 20),),
          const SizedBox(height: 30,),

          Expanded(
            child: FutureBuilder(
                future: _getPairedDevice(),
                builder: (context, snapshot) {
                  return ListView.separated(
                      itemCount: snapshot.data?.length ?? 0,
                      itemBuilder: (context, index) {
                        return SizedBox(
                          height: 50,
                            width: MediaQuery.of(context).size.width,
                            child: Text(snapshot.data?[index] ?? '',style: const TextStyle(fontSize: 15),));
                      }, separatorBuilder: (BuildContext context, int index) {
                        return const Divider(thickness: 1,);
                  },);
                }),
          ),
        ],
      ),
    );
  }
}
