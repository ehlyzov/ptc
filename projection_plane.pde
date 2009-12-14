class ProjectionPlane {
 int ppWidth;
 int ppHeight;
 int ppCenterX;
 int ppCenterY;
 Function f;
 float[] fData;
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
   return round((x/scaleX())/step());
 }
 
 float iToRelX(int i) {
   return (float)i*step()*scaleX();
 }
 
 float iToRelY(int i) {
   return this.fData[i+3]*scaleY()+this.ppCenterY;
 }
 
 float[] iToCoords(int i) {
   float[] p = new float[2];
   p[0] = iToRelX(i);
   p[1] = iToRelY(i);
   return p;
 }
  
 float start() {
   println("start");
   return fData[0];
 }

 float end() {
   return fData[1];
 }

 float step() {
   return fData[2];
 }
 
 float[] fData() {
   return this.fData; 
 }
 
 float absCoordX(float rel_x) {
   return rel_x + 50.0;
 }
 
 float absCoordY(float rel_y) {
   return rel_y + 50.0;
 }
 
 
 void applyFunction(float start, float end, float step) {
   this.fData = new float[(int)((end-start)/step)+4];
   this.fData[0] = start;
   this.fData[1] = end;
   this.fData[2] = step;
   float maxElement = 0.0;
   float scaleX = this.ppWidth/(end-start);
   
   int maxIndex = 0;
   for(int i=3;start + (i-3.0)*step<=end;i+=1) {    
     this.fData[i] = this.f.getValue((i-3.0)*step+start);
     if(abs(this.fData[i]) > maxElement) {     
       maxElement = abs(this.fData[i]);
     }
   }
   
   println("MAX Y: " + maxElement);
   for(int i=3;start + (i-3.0)*step<=end;i+=1) {
     //println("Before: " + this.fData[i]);
     this.fData[i] = this.fData[i]/maxElement;
     //println("After: " + this.fData[i]);
   }
 }
 
 void reset() {
   fill(255);
   rect(0,0,this.ppWidth,this.ppHeight);
 }
 
 float scaleX() {
   return this.ppWidth/(end()-start());
 }
 
 float scaleY() {
   return this.ppHeight / 2.0;
 }
 
 void drawFunction() {
   for(int i=3;start() + (i-3.0)*step()<end();i+=1) {     
     point((i-3.0)*step()*scaleX(),this.fData[i]*scaleY()+this.ppCenterY);
   }
 }
 
}
