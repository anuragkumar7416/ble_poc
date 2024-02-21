void main() {
  String demo = "Hello World";
  List<String> rev = demo.split('');
  String revString = '';
  String result = '';

  for(int i= (rev.length-1); i>=0;i--){
    revString = revString + rev[i];

  }

  rev.clear();
  rev = revString.split('');
  for(int i= 0; i<rev.length; i+=2){
    if(rev[i]!= rev.last){
      String temp = rev[i];
      rev[i] = rev[i+1];
      rev[i+1] = temp;

      result = result + rev[i];
      result = result + rev[i+1];

      continue;



    }else{

      result = result + rev[i];
      continue;


    }


  }
  print(result);


}


