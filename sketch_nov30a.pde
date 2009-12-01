import controlP5.*;

ControlP5 cp5;
controlP5.Label l;
ProjectionPlane pp;
Function f;
float a;
void setup() {
  size(200,200,P2D);
  cp5 = new ControlP5(this);  
  Slider s = cp5.addSlider("update",-10,0,-5,0,0,100,10);
  smooth();
  translate(50,50);
  a = -1;
  f = new Function(a);
  pp = new ProjectionPlane(100,100, 10, 50, f);
  pp.draw();  
}

void draw() {
  background(0);
  cp5.draw();
}

void update(float a) {
  println("A: " + a);
  translate(50,50);
  pp.reset();
  f.setA(a);
  pp.applyFunction(-2.0, 2.0, 0.05);
  pp.drawFunction();  
}


