/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CatVIS;

import processing.core.PApplet;

/**
 *
 * @author Евгений
 */
interface IGraph {

    void setContainerDim(float width, float height);

    Point[] scale();

    Point scalePoint(Point p);

    Point findClosestPoint(float x, float y);

    Point interpolate(float x, float y);
}

public class GeneralGraph implements IGraph {

    float[] x_values, y_values;
    public float min_x, min_y, max_x, max_y, width, height;
    PApplet app;

    public void setContainerDim(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public GeneralGraph(PApplet app) {
        this.app = app;
    }

    public Point[] scale() {
        Point[] res = new Point[x_values.length];
        min_x = app.min(x_values);
        app.println("Min X: " + min_x);
        max_x = app.max(x_values);
        app.println("Max X: " + max_x);
        min_y = app.min(y_values);
        app.println("Min y: " + min_y);
        max_y = app.max(y_values);
        app.println("Max y: " + max_y);
        for (int i = 0; i < x_values.length; i++) {
            res[i] = scalePoint(new Point(x_values[i],y_values[i]));
        }
        return res;
    }

    public Point scalePoint(Point p) {
        return new Point(app.map(p.x, min_x, max_x, 0, width),
            app.map(p.y, min_y, max_y, 0, height));
    }

    /* returns closest to the given coordinates point from the dataset */
    public Point findClosestPoint(float x, float y) {
        //return new Point(x,y);
        int closestPoint = -1;
        int size = x_values.length;
        for (int i = 1; i < size; i++) {
            if (Math.signum(x_values[i - 1] - x) != Math.signum(x_values[i] - x)) {
                if (closestPoint < 0) {
                    closestPoint = i - 1;
                } else {
                    if (Math.abs(y_values[closestPoint] - y) > Math.abs(y_values[i - 1] - y)) {
                        closestPoint = i - 1;
                    }
                }
            }
        }
        app.println("Closest I: "+closestPoint + " ("+ x_values[closestPoint]+","+y_values[closestPoint]+")");
        int[] fourNearPoints = new int[4];
        fourNearPoints[0] = app.constrain(closestPoint-1, 0, closestPoint);
        fourNearPoints[1] = closestPoint;
        fourNearPoints[2] = closestPoint + 1;
        fourNearPoints[3] = app.constrain(closestPoint+2, 0, size-1);
        Point res = new Point(
            app.curvePoint(x_values[fourNearPoints[0]],
                x_values[fourNearPoints[1]],
                x_values[fourNearPoints[2]],
                x_values[fourNearPoints[3]],
                (float)0.5),
            app.curvePoint(y_values[fourNearPoints[0]],
                y_values[fourNearPoints[1]],
                y_values[fourNearPoints[2]],
                y_values[fourNearPoints[3]],
                (float)0.5));
        app.println("Interpolated point: ("+ res.x+","+res.y+")");
        return res;
    }

    public Point interpolate(float x, float y) {
        return new Point(PApplet.map(x, 0, width, min_x, max_x),
                PApplet.map(y, 0, height, min_y, max_y));
    }
}

