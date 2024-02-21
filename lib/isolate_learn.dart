
import 'dart:isolate';

void main(){
  ReceivePort myReceivePort = ReceivePort();


  Isolate.spawn<IsolateModel>(heavyTask, IsolateModel(355000, 500,myReceivePort.sendPort),);

  myReceivePort.listen((message) {
    //print('hjbhj$message');
  });

}





void heavyTask(IsolateModel model) {
  int total = 0;


  /// Performs an iteration of the specified count
  for (int i = 1; i < model.iteration; i++) {

    /// Multiplies each index by the multiplier and computes the total
    total += (i * model.multiplier);
  }

  model.sendPort.send(total);

}



class IsolateModel {

  final int iteration;
  final int multiplier;
  final SendPort sendPort ;

  IsolateModel(
      this.iteration,
      this.multiplier,
      this.sendPort);


}
