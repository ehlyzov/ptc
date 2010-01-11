/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CatVIS;

import com.nootropic.processing.layers.PLayer;
import processing.core.*;

/**
 *
 * @author Евгений
 */
interface CHController {

    void run(Main app, float x, float y);
}

class Crosshair extends PLayer {

    float r;
    ProjectionPlane pp;
    float x;
    float y;
    float int_x, int_y;
    boolean isLocked, isEditMode;
    boolean lockedToGraph;
    CHController callback;
    Main app;
    //Coords coords;

    Crosshair(Main app, ProjectionPlane pp, float r, CHController callback, boolean lockedToGraph) {
        super(app);
        //this.coords = coords;
        this.r = r;
        this.pp = pp;
        this.callback = callback;
        this.lockedToGraph = lockedToGraph;
        this.app = app;
        isLocked = false;
        isEditMode = false;
        app.layers.addLayer(this);
    }

    @Override
    public void setup() {
        stroke(0);
        noFill();
    }

    @Override
    public void draw() {
        if (isLocked && isOver()) {
            app.println("Ch Mouse X: " + app.mouseX);
            app.println("Ch Mouse Y: " + app.mouseY);
            changeFocus(app.mouseX, app.mouseY);
        }
    }

    void changeFocus(float x, float y) {
        Point interpolated = getPointOnGraph(
                new Point(x, y));


        if (lockedToGraph) {
            Point closestPoint = pp.g.findClosestPoint(interpolated.x, interpolated.y);
            changeFocusInt(pp.g.findClosestPoint(interpolated.x, interpolated.y));
        } else {
            int_x = interpolated.x;
            int_y = interpolated.y;
            callback.run(app, interpolated.x, interpolated.y);
            changeFocusExt((float) app.mouseX, (float) app.mouseY);
        }
    }

    Point getPointOnGraph(Point pos) {
        return pp.g.interpolate(pp.coords.abs2relX(pos.x),
                pp.coords.abs2relY(pos.y));
    }

    void changeFocusInt(Point pos) {
//        Point ch = pp.g.scalePoint(new Point(app.constrain(pos.x, ((STProjGraph)pp.g).min_x, ((STProjGraph)pp.g).max_x),
//                app.constrain(pos.y, ((STProjGraph)pp.g).min_y, ((STProjGraph)pp.g).max_y)));
        int_x = pos.x;
        int_y = pos.y;

        Point ch = pp.g.scalePoint(pos);

        callback.run(app, pos.x, pos.y);
        changeFocusExt(pp.coords.int2absX(ch.x), pp.coords.int2absY(ch.y));
    }

    void hide() {
        background(0, 0);
    }

    void drawCrosshair(float x, float y) {
        hide();
        ellipse(x, y, r, r);
    }

    // START Mouse events
    boolean isOver() {
        return pp.coords.isInside(app.mouseX, app.mouseY);
    }

    @Override
    public void mousePressed() {
        if (isOver()) {
            if (app.mouseButton == app.RIGHT) {
                isEditMode = !isEditMode;
            }
            PApplet.println("Inside");
            isLocked = true;
        } else {
            PApplet.println("Outside");
            isLocked = false;
        }
    }

    @Override
    public void mouseReleased() {
        isLocked = false;
    }
    // END Mouse events

    // move the focus to the given point
    void changeFocusExt(float x, float y) {
        this.x = x;
        this.y = y;
//        if (this.x != x) {
//            callback.updateX(pp.coords.transform_x_abs(app.mouseX));
//        }
//        if (this.y != y) {
//            callback.updateY(pp.coords.transform_y_abs(app.mouseY));
//        }
        drawCrosshair(x, y);
    }
// void updateFocus() {
//   if (lockedToGraph) {
//     changeFocus(x);
//   } else {
//     changeFocus(x, y);
//   }
// }
}
