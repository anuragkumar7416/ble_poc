

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../Hello.dart';

class AvailableDevices extends StatefulWidget {
  const AvailableDevices({Key? key}) : super(key: key);

  @override
  State<AvailableDevices> createState() => _AvailableDevicesState();
}

class _AvailableDevicesState extends State<AvailableDevices> {
  static const platform = MethodChannel('flutter.native/ble');


  Future<Set<String>> _getAvailableDevice() async {
    Set<String> listOfAvailableDevices = {};
    try {

      var list = await platform.invokeMethod('getAvailableDevices');

      Hello value = Hello.fromJson(list);

      listOfAvailableDevices.addAll(value.list??[]);

      setState(() {});
    } on PlatformException catch (e) {
      throw Exception('Something Wrong Occurred----> $e');
    }
    return listOfAvailableDevices;
  }

  @override
  Widget build(BuildContext context) {
    return Flexible(
      fit: FlexFit.loose,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 30,),
          const Text('Available Devices',style: TextStyle(fontWeight: FontWeight.w500,fontSize: 20),),
          const SizedBox(height: 30,),
          Flexible(
            fit: FlexFit.loose,
            child: FutureBuilder(
                future: _getAvailableDevice(),
                builder: (context, snapshot) {
                  return ListView.separated(
                    shrinkWrap: true,
                    padding: EdgeInsets.zero,
                    //physics: const NeverScrollableScrollPhysics(),
                    itemCount: snapshot.data?.length ?? 0,
                    itemBuilder: (context, index) {
                      return SizedBox(
                          height: 50,
                          width: MediaQuery.of(context).size.width,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(snapshot.data?.elementAt(index) ?? '',style: const TextStyle(fontSize: 15),),
                            ],
                          ));
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
