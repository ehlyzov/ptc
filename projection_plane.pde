class Point {
  float x;
  float y;
  float t; // stored value of parameter

  Point(float[] coords) {
    this.x = coords[0];
    this.y = coords[1];
  } 
   
  Point(float t, float x, float y) {
    this.t = t;
    this.x = x;
    this.y = y; 
  }
   
}

class ProjectionPlane {
 int ppWidth;
 int ppHeight;
 int ppCenterX;
 int ppCenterY;
 Function f;
 Point[] fData;
 boolean isTargetingEnable;
 
 ProjectionPlane(int w,int h,int c_x,int c_y, Function x) {
   this.ppWidth = w;
   this.ppHeight = h;
   this.ppCenterX = c_x;
   this.ppCenterY = c_y;      
   this.f = x;
   this.isTargetingEnable = false;
 }
 
 void draw() {
  rect(0,0,this.ppWidth,this.ppHeight); 
 }
 
 int relXtoI(float x) {
   println(x);
   println(round(x/step()));
   return round(x/step());
 }
 
 float iToRelX(int i) {
   return fData[i].x*scaleX();
 }
 
 float iToRelY(int i) {
   return fData[i].y*scaleY()+this.ppCenterY;
 }
 
 float[] iToCoords(int i) {
   float[] p = new float[2];
   p[0] = iToRelX(i);
   p[1] = iToRelY(i);
   return p;
 }
  
 float start() {
   println("start");
   return fData[0].t;
 }

 float end() {
   return fData[fData.length-1].t;
 }

 float step() {   
   //return (fData[fData.length-1].t - fData[0].t)/((float)fData.length);
   return ppWidth/((float)fData.length);
 }
 
 Point[] fData() {
   return this.fData; 
 } 
 
 void applyFunction(float start, float end, float step) {
   fData = new Point[(int)((end-start)/step)+1];
   
   float maxValueX = 0.0;
   float maxValueY = 0.0;
   float scaleX = this.ppWidth/(end-start);
   
   int maxIndexX = 0;
   int maxIndexY = 0;
   
   for(int i=0;start + i*step<=end;i+=1) {   
     fData[i] = f.getPoint(i*step+start);      
     if(abs(fData[i].y) > abs(fData[maxIndexY].y)) {     
       maxIndexY = i;
     }
     if(abs(fData[i].x) > abs(fData[maxIndexX].x)) {     
       maxIndexX = i;
     }     
   }
   
   maxValueX = abs(fData[maxIndexX].x);
   maxValueY = abs(fData[maxIndexY].y);   
   println("MAX Y: " + fData[maxIndexY].y);
   for(int i=0;i < fData.length;i+=1) {
     println("Before: (" + fData[i].x + "," + fData[i].y + ")");
     fData[i].y = fData[i].y/maxValueY;
     fData[i].x = fData[i].x/maxValueX; 
     println("After: (" + fData[i].x + "," + fData[i].y + ")");
   }
 }
 
 void reset() {
   fill(255);
   rect(0,0,this.ppWidth,this.ppHeight);
 }
 
 float scaleX() {
   return this.ppWidth/2;
 }
 
 float scaleY() {
   return this.ppHeight / 2.0;
 }
 
 void drawFunction() {
   println("scaleX: "+scaleX());
   println("scaleY: "+scaleY());
   for(int i=0;i < fData.length;i+=1) {     
     point(fData[i].x*scaleX()+this.ppCenterX,fData[i].y*scaleY()+this.ppCenterY);
   }
 }
 
}
