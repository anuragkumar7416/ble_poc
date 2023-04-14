class Hello {
  Hello({
      this.list,});

  Hello.fromJson(dynamic json) {
    list = json['list'] != null ? json['list'].cast<String>() : [];
  }
  List<String>? list;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['list'] = list;
    return map;
  }

}