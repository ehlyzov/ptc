/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CatVIS;

import java.util.HashMap;
import processing.core.PApplet;

/**
 *
 * @author Евгений
 */
class Holder {

    public HashMap pps; // array of projection planes params
    PApplet app;

    Holder(PApplet applet) {
        this.app = applet;
        pps = new HashMap();
    }

    int addPP(String pp_name, ProjectionPlane pp, float start_x, float start_y) {
        pp.place(start_x, start_y);
        pps.put(pp_name, pp);
        //p.addCrosshair(app, (float)5.0, new HolderChCallback(callback, p), lockedToGraph);
        return pps.size() - 1;
    }

    void updatePP(String name) {
        ProjectionPlane pp = (ProjectionPlane) pps.get(name);
        app.stroke(0);
        pp.reset();
        pp.displayGraph();
        //pp.ch.updateFocus();
    }
}

