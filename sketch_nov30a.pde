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
FABc f_ab;
float a;

class TestChCallback implements ChCallback {
  void run(float x, float y) {
    updateV(x);
  } 
}

class HolderChCallback implements ChCallback {
  ChCallback child;
  PPParams p;
  HolderChCallback(ChCallback child, PPParams p) {
    this.child = child; 
    this.p = p;
  }
  
  void run(float x, float y) {
    pushMatrix();    
    translate(p.start_x, p.start_y);
    stroke(0);
    child.run(x,y);
    popMatrix();     
  }
}

class FakeChCallback implements ChCallback {
  
  String label;
  
  void run(float x, float y) {
    println("["+label+"] X: " + x);
    println("["+label+"] Y: " + y);
  } 
  
  FakeChCallback(String label) {
    this.label = label;
  }
}


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
  f_a = new FAc(0.0);
  f_ab = new FABc(0.0);
  pp = new ProjectionPlane(100,100, 50, 50, f);
  h = new Holder(this);
  pp_a = new ProjectionPlane(100, 100, 50, 50, f_a);  
  pp_b = new ProjectionPlane(100, 100, 50, 50, f_ab);
  //ch = new Crosshair(this, pp, 5.0);
  h.addPP(pp, 50, 50, new TestChCallback(), true);
  h.addPP(pp_a, 50, 200, new FakeChCallback("FA"), true); 
  h.addPP(pp_b, 50, 350, new FakeChCallback("FAB"), true);

  s.setValue(a); 
  // test
  pp_a.applyFunction(-1.0,1.0,0.05);
  pp_b.applyFunction(-1,4,0.05);  
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

