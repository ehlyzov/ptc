import controlP5.*;
import com.nootropic.processing.layers.*;

ControlP5 controlP5;
controlP5.Label l;
controlP5.Textfield valueLabel;
PAppletLayers layers;
Crosshair ch;
Holder h;

ProjectionPlane pp;
ProjectionPlane pp_a;
ProjectionPlane pp_b;
ProjectionPlane pp_c;
Function f;
FAc f_a;
FBc f_b;
float a;

void setup() {
  size(600,600);
  controlP5 = new ControlP5(this);  
  layers = new PAppletLayers(this);
  Slider s = controlP5.addSlider("update",0,2,-5,0,0,100,10);
  valueLabel = controlP5.addTextfield("value",50, 160, 100, 15);
  controlP5.controller("value").setLabel("");
  l = s.captionLabel();
  a = 1.0;
  f = new TestFunc(a);
  f_a = new FAc(1.0);
  f_b = new FBc(1.0);
  pp = new ProjectionPlane(100,100, 10, 50, f);
  h = new Holder();
  pp_a = new ProjectionPlane(100, 100, 0, 50, f_a);  
  pp_b = new ProjectionPlane(100, 100, 0, 50, f_b);
  ch = new Crosshair(this, pp, 5.0);
  h.addPP(pp, 50, 50, ch);
  h.addPP(pp_a, 50, 200); 
  h.addPP(pp_b, 50, 350);
  layers.addLayer(ch);
  s.setValue(a); 
  // test
  pp_a.applyFunction(-2.0,2.0,0.05);
  pp_b.applyFunction(-2.0,2.0,0.05);  
  h.updatePP(1);
  h.updatePP(2); 
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
  //pushMatrix();
  //translate(50,50);
  //stroke(0);
  //pp.reset();
  f.setParam(a);
  pp.applyFunction(-2.0, 2.0, 0.05);
  //pp.drawFunction();
  h.updatePP(0);
  //ch.changeFocus(ch.x);
  updateV(ch.x);
  //popMatrix();  
}

void updateV(float relX) {
  valueLabel.setValue("V="+f.getValue((float)pp.relXtoI(relX)*pp.step()));  
}

