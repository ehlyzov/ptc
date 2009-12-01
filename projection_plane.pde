class ProjectionPlane {
 int ppWidth;
 int ppHeight;
 int ppCenterX;
 int ppCenterY;
 Function f;
 float[] fData;
 ProjectionPlane(int w,int h,int c_x,int c_y, Function x) {
   this.ppWidth = w;
   this.ppHeight = h;
   this.ppCenterX = c_x;
   this.ppCenterY = c_y;      
   this.f = x;
 }
 
 void draw() {    
  rect(0,0,this.ppWidth,this.ppHeight); 
 }
 
 float[] fData() {
   return this.fData; 
 }
 
 void applyFunction(float start, float end, float step) {
   this.fData = new float[(int)((end-start)/step)+3];
   this.fData[0] = start;
   this.fData[1] = end;
   this.fData[2] = step;
   float maxElement = 0.0;
   float scaleX = this.ppWidth/(end-start);
   
   int maxIndex = 0;
   for(int i=3;start + (i-3.0)*step<end;i+=1) {    
     this.fData[i] = this.f.getValue((i-3.0)*step+start);
     if(abs(this.fData[i]) > maxElement) {     
       maxElement = abs(this.fData[i]);
     }
   }
   
   println("MAX Y: " + maxElement);
   for(int i=3;start + (i-3.0)*step<end;i+=1) {
     //println("Before: " + this.fData[i]);
     this.fData[i] = this.fData[i]/maxElement;
     //println("After: " + this.fData[i]);
   }
 }
 
 void reset() {
   fill(255);
   rect(0,0,this.ppWidth,this.ppHeight);
 }
 
 void drawFunction() {
   float start = fData[0];
   float end = fData[1];
   float step = fData[2];   
   float scaleX = this.ppWidth/(end-start);   
   float scaleY = this.ppHeight / 2;
   for(int i=3;start + (i-3.0)*step<end;i+=1) {
     //println("Point x=" + (start+step*(i-3.0))+ " y="+this.fData[i]);
     point((i-3.0)*step*scaleX,this.fData[i]*scaleY+this.ppCenterY);
   }
 }
 
}
