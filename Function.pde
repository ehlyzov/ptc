class Function {
 
  float a;
  
  Function(float a) {
    this.a = a;
  }
  
  void setA(float value) {
    this.a = value; 
  }
  
  float getValue(float fParam) {
    return fParam*fParam*fParam - this.a*fParam;
  }   
}
