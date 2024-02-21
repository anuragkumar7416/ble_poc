


import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class CallScreen extends StatefulWidget {

  const CallScreen({Key? key}) : super(key: key);

  @override
  State<CallScreen> createState() => _CallScreenState();
}

class _CallScreenState extends State<CallScreen> {

  static const platform = MethodChannel('flutter.native/call');

  void makePhoneCall() async{
    await platform.invokeMethod('makePhoneCall');

  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      

      body: Stack(
        children:[
          SizedBox(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
              child: Image.asset('assets/images/app_bg.png',fit: BoxFit.fill,)),
          Container(
            width: MediaQuery.of(context).size.width,
            padding: const EdgeInsets.only(top: 60),
            child: Column(

              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                const Text('VOICE CALL',
                  style: TextStyle(
                    color: Colors.deepPurple,
                    fontSize: 18,

                ),),
                const SizedBox(
                  height: 15,
                ),
                const Text('Anurag Kumar',
                  style: TextStyle(
                    color: Colors.deepPurple,
                    fontSize: 23,
                    fontWeight: FontWeight.bold

                ),),
                const SizedBox(
                  height: 25,
                ),
                ClipOval(
                  child: SizedBox(
                      width: 170,
                      height: 170,
                      child: Image.asset('assets/images/anurag.jpeg',fit: BoxFit.cover,)),
                ),
                const SizedBox(
                  height: 225,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    FloatingActionButton(onPressed: (){
                      makePhoneCall();
                    },backgroundColor: Colors.green,child: const Icon(Icons.call),)
                  ],
                )







              ],
            ),
          ),
        ]
      ),


    );
  }
}
