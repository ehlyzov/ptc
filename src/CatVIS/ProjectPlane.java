/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CatVIS;

/**
 *
 * @author Евгений
 */

class ProjectionPlane {

    int ppWidth;
    int ppHeight;
    int ppCenterX;
    int ppCenterY;
    //Function f;
// Point[] fData;
    boolean isTargetingEnable;
    boolean isGraphDisplayed;
    Main app;
    IGraph g;
    Crosshair ch;
    Coords coords;

    ProjectionPlane(Main app, int w, int h) {
        this.app = app;
        ppWidth = w;
        ppHeight = h;
        ppCenterX = w/2;
        ppCenterY = h/2;
        isGraphDisplayed = false;
        coords = new Coords(0,0,ppCenterX, ppCenterY);
        //ch = new Crosshair(app, this, (0.5, null, isTargetingEnable);
    }

    void addGraph(IGraph g) {
        this.g = g;
        g.setContainerDim(ppWidth, ppHeight);
    }

    void addCrosshair(CHController controller, boolean isTargetingEnable) {
        this.ch = new Crosshair(app, this, (float)5.0, controller, isTargetingEnable);
    }

    void place(float shift_x, float shift_y) {
        coords.shift_x = shift_x;
        coords.shift_y = shift_y;
    }

    public void draw() {
        app.rect(coords.shift_x, coords.shift_y, this.ppWidth, this.ppHeight);
    }

    public void displayCoord() {
        app.pushMatrix();
        app.translate(coords.shift_x, coords.shift_y);
        app.stroke(200,0,0);
        app.line(coords.center_x,0,coords.center_x,ppHeight);
        app.line(0,coords.center_y,ppWidth,coords.center_y);
        app.stroke(0,0,0);
        app.popMatrix();
    }

    public void displayGraph() {
        if (g == null) {
            throw new NullPointerException("Graph object is null");
        }
        if (coords == null) {
            throw new NullPointerException("Goords object is null");
        }
        Point[] points = g.scale();
        //app.pushMatrix();
        //app.translate(coords.shift_x, coords.shift_y);
        float old_x, old_y, new_x, new_y;
        app.beginShape();
        app.noFill();
        old_x = coords.int2absX(app.constrain(points[0].x,0,ppWidth));
        old_y = coords.int2absY(app.constrain(points[0].y,0,ppHeight));
        app.curveVertex(old_x, old_y);
        for (int i = 1; i < points.length; i++) {
            new_x = coords.int2absX(app.constrain(points[i].x,0,ppWidth));
            new_y = coords.int2absY(app.constrain(points[i].y,0,ppHeight));
            if ((app.abs(new_x - old_x) > ppWidth/2) ||
                    (app.abs(new_y - old_y) > ppHeight/2)) {
                 app.curveVertex(old_x, old_y);
                 app.endShape();
                 app.beginShape();
                 app.curveVertex(new_x, new_y);
            }
            old_x = new_x;
            old_y = new_y;
            app.curveVertex(old_x, old_y);

        }
        app.endShape();
        
        //app.popMatrix();

    }

    void reset() {
        app.fill(255);
        app.rect(coords.shift_x, coords.shift_y, this.ppWidth, this.ppHeight);
        displayCoord();
    }
}
