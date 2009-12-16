class Crosshair extends PLayer {
 
  float r;
  ProjectionPlane pp;
  PPParams p;
  float x;
  float y;
  boolean isLocked;
  boolean lockedToGraph;
  ChCallback callback;
  
  
  Crosshair(PApplet parent, PPParams p, float r, ChCallback callback, boolean lockedToGraph) {
    super(parent);
    this.r = r;
    this.pp = p.pp;
    this.p = p;
    this.callback = callback;
    this.lockedToGraph = lockedToGraph;
    isLocked = false;
    layers.addLayer(this);    
  } 
  
  void setup() {
    stroke(0);
    noFill(); 
  }
  
  void draw() {
    if (isLocked && isOver()) {     
      callback.run(mouseX-p.start_x, mouseY-p.start_y); 
      if (lockedToGraph) {
        changeFocus((float)(mouseX-p.start_x));
      } else {
        changeFocus((float)(mouseX-p.start_x), (float)(mouseY-p.start_y));
      }         
    }
  }
  
  void drawCrosshair(float x, float y) {
    background(0, 0);
    float abs_x = p.absCoordX(x);
    float abs_y = p.absCoordY(y);
    ellipse(abs_x,abs_y, r, r);
  }

  // START Mouse events  
  boolean isOver() {
    return (mouseX > p.start_x) && (mouseX < pp.ppWidth + p.start_x) && (mouseY > p.start_y) && (mouseY < pp.ppHeight + p.start_y);
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
    changeFocus(pp.iToRelX(pp.relXtoI(x)), pp.iToRelY(pp.relXtoI(x)));
  }
 
 // move the focus to the given point
 void changeFocus(float x, float y) {
   this.x = x;
   this.y = y;
   println("changeFocus(" +x+", "+y+")");
   //ch.remove();
   drawCrosshair(x,y);
 }  
 
 void updateFocus() {
   if (lockedToGraph) {
     changeFocus(x);
   } else {
     changeFocus(x, y);
   }
 }
}
