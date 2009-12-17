public interface ChCallback {
  void run(float x, float y); 
}

class Holder {
 
  ArrayList pps; // array of projection planes params 
  PApplet applet;
  
  Holder(PApplet applet) {
    this.applet = applet;
    pps = new ArrayList();
  }
  
 int addPP(ProjectionPlane pp, float start_x, float start_y, ChCallback callback, boolean lockedToGraph) {
    PPParams p = new PPParams(pp, start_x, start_y);    
    p.addCrosshair(applet, 5.0, new HolderChCallback(callback, p), lockedToGraph);
    pps.add(p);
    return pps.size()-1;
  }  
  
  void updatePP(int i) {
    PPParams p = (PPParams) pps.get(i);
    pushMatrix();
    translate(p.start_x,p.start_y);    
    stroke(0);
    p.pp.reset();
    p.pp.drawFunction();
    if (p.hasCh) {
      p.ch.updateFocus();
    }
    popMatrix();  
  }  
}

class PPParams {
  ProjectionPlane pp;
  float start_x;
  float start_y;
  boolean hasCh;
  Crosshair ch;
  //int ch_dim;
 
  void addCrosshair(PApplet applet, float r, ChCallback callback, boolean lockedToGraph) {
    ch = new Crosshair(applet, this, r, callback, lockedToGraph);
    hasCh = true;
    //this.ch_dim = ch_dim;
  }
  
  float absCoordX(float rel_x) {
    return rel_x + start_x;
  }
 
  float absCoordY(float rel_y) {
    return rel_y + start_y;
  }
  
  PPParams(ProjectionPlane pp, float start_x, float start_y) {
    this.pp = pp;
    this.start_x = start_x;
    this.start_y = start_y;
    hasCh = false;
  }

}
