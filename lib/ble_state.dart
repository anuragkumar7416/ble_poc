part of 'ble_cubit.dart';

abstract class BleState extends Equatable {
  final bool isSwitch;
  const BleState(this.isSwitch);
}

class BleInitial extends BleState {
  const BleInitial(super.isSwitch);

  @override
  List<Object> get props => [isSwitch];
}

class BleChangedStatus extends BleState {
  const BleChangedStatus(super.isSwitch);

  @override
  List<Object> get props => [isSwitch];
}
