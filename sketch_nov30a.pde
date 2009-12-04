import controlP5.*;
import com.nootropic.processing.layers.*;

ControlP5 controlP5;
controlP5.Label l;
controlP5.Textfield valueLabel;
PAppletLayers layers;
Crosshair ch;

ProjectionPlane pp;
Function f;
float a;

void setup() {
  size(200,200);
  controlP5 = new ControlP5(this);  
  layers = new PAppletLayers(this);
  Slider s = controlP5.addSlider("update",0,2,-5,0,0,100,10);
  valueLabel = controlP5.addTextfield("value",50, 160, 100, 15);
  controlP5.controller("value").setLabel("");
  l = s.captionLabel();
  a = 1.0;
  f = new Function(a);
  pp = new ProjectionPlane(100,100, 10, 50, f);
  ch = new Crosshair(this, pp, 5.0);
  layers.addLayer(ch);
  s.setValue(a);  
}

void paint() {
  if (layers != null) {
    layers.paint(this);
  } 
  else {
    super.paint();
  }
}


void draw() {
}

// Mouse events

void mousePressed() {
  println("hi");
  layers.mousePressed();
}

//void mouseDragged() {
//  layers.mouseDragged();
//}

void mouseReleased() {
  layers.mouseReleased();
}

void update(float a) {
  this.a = a;
  println("A: " + a);
  pushMatrix();
  translate(50,50);
  stroke(0);
  pp.reset();
  f.setA(a);
  pp.applyFunction(-2.0, 2.0, 0.05);
  pp.drawFunction();
  ch.changeFocus(ch.x);
  updateV(ch.x);
  popMatrix();  
}

void updateV(float relX) {
  valueLabel.setValue("V="+f.getValue((float)pp.relXtoI(relX)*pp.step()));  
}

