public interface Function {
  void setParam(float value);
  float getValue(float r);
}

public abstract class ComplexP1Func implements Function {
  Function q;
  
  ComplexP1Func(float q_c) {
    this.q = new FQc(q_c);
  }

  void setParam(float value) {
    q.setParam(value); 
  }  
  
  abstract float getValue(float r);
}

public abstract class SimpleP1Func implements Function {
 
  float a;
  
  SimpleP1Func(float a) {
    this.a = a;
  }
  
  void setParam(float value) {
    this.a = value; 
  }
  
  abstract float getValue(float fParam);
}

class TestFunc extends SimpleP1Func {
  
  TestFunc(float p) {
    super(p);
  } 
  
  float getValue(float fParam) {
    return fParam*fParam*fParam - this.a*fParam;
  }   
}


class FQc extends SimpleP1Func {
  // a = c_0  
  FQc(float a) {
    super(a);
  }
    
  float getValue(float fParam) {
    return (a+15.0*fParam*fParam*fParam*fParam)/(3.0*fParam*fParam*fParam);
  }
}

class FAc extends ComplexP1Func {

  FAc(float q_c) {
    super(q_c);    
  }
  
  float getValue(float r) {
    return 3.0*q.getValue(r)-6.0*r*r;
  }  
}

class FBc extends ComplexP1Func {
  
  FBc(float q_c) {
    super(q_c);
  }
  
  float getValue(float r) {
    return -6.0*r*q.getValue(r)+8.0*r*r*r;
  }  
}
