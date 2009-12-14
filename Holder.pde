class Holder {
 
  ArrayList pps; // array of projection planes params
  
  Holder() {
    pps = new ArrayList();
  }
  
  int addPP(ProjectionPlane pp, float start_x, float start_y) {
    pps.add(new PPParams(pp, start_x, start_y));
    return pps.size()-1;
  }
  
 int addPP(ProjectionPlane pp, float start_x, float start_y, Crosshair ch) {
    pps.add(new PPParams(pp, start_x, start_y, ch));
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
      p.ch.changeFocus(ch.x);
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
  
  PPParams(ProjectionPlane pp, float start_x, float start_y) {
    this.pp = pp;
    this.start_x = start_x;
    this.start_y = start_y;
    hasCh = false;
  }

  PPParams(ProjectionPlane pp, float start_x, float start_y, Crosshair ch) {
    this.pp = pp;
    this.start_x = start_x;
    this.start_y = start_y;
    this.ch = ch;
    hasCh = true;
  }

}
