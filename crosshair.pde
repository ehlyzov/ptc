class Crosshair extends PLayer {
 
  float r;
  ProjectionPlane pp;
  float x;
  float y;
  boolean isLocked;
  
  Crosshair(PApplet parent, ProjectionPlane pp, float r) {
    super(parent);
    this.r = r;
    this.pp = pp;
    isLocked = false;
  } 
  
  void setup() {
    stroke(0);
    noFill(); 
  }
  
  void draw() {
    if (isLocked && isOver()) {
      println("MouseX: "+mouseX);
      updateV(mouseX-50);
      changeFocus((float)(mouseX-50));
    }
  }
  
  void drawCrosshair(float x, float y) {
    background(0, 0);
    float abs_x = pp.absCoordX(x);
    float abs_y = pp.absCoordY(y);
    ellipse(abs_x,abs_y, r, r);
  }

  // START Mouse events  
  boolean isOver() {
    return (mouseX > 50) && (mouseX < pp.ppWidth + 50) && (mouseY > 50) && (mouseY < pp.ppHeight + 50);
  }

  void mousePressed() {
    if(isOver()) {
      isLocked = true;
    } else {
      isLocked = false;
    }
  }

  void mouseReleased() {
    isLocked = false;
  }
  // END Mouse events  
  
  // lock the focus to the point (x,f(x))
  void changeFocus(float x) {
    println("X: "+x);
    changeFocus(x, pp.iToRelY(pp.relXtoI(x)));
  }
 
 // move the focus to the given point
 void changeFocus(float x, float y) {
   this.x = x;
   this.y = y;
   println("changeFocus(" +x+", "+y+")");
   //ch.remove();
   drawCrosshair(x,y);
 }  
}
