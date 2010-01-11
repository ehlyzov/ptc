/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CatVIS;

/**
 *
 * @author Евгений
 */
interface IParametricGraph {

    float x(float r);

    float y(float r);
}

/* V = x^5 + ax^3 + bx^2 + cx*/
class SwallowtailV implements IParametricGraph {

    float a;
    float b;
    float c;

    SwallowtailV(float a, float b, float c) {
        applyParams(a, b, c);
    }

    void applyParams(float a, float b, float c) {
        setParamA(a);
        setParamB(b);
        setParamC(c);
    }

    void setParamA(float x) {
        this.a = x;
    }

    void setParamB(float x) {
        this.b = x;
    }

    void setParamC(float x) {
        this.c = x;
    }

    public float apply(float r) {
        return (float)(Math.pow(5, r) + a * Math.pow(3, r) + b * Math.pow(2, r) + c * r);
    }

    public float x(float x) {
        return x;
    }

    public float y(float x) {
        return apply(x);
    }
//
//  public Point getPoint(float r) {
//    return new Point(r, apply(r));
//  }
}

class CatastropheGraph extends GeneralGraph {

    SwallowtailV p;
    float t_0, t_n, step;

    CatastropheGraph(Main app, SwallowtailV p, float t_0, float t_n, float step) {
        super(app);
        int length = (int) ((t_n - t_0) / step) + 1;
        this.p = p;
        this.t_0 = t_0;
        this.t_n = t_n;
        this.step = step;
        x_values = new float[length];
        y_values = new float[length];
        reload();
    }

    CatastropheGraph(Main app, SwallowtailV p, float t_0, float t_n) {
        this(app, p, t_0, t_n, (float) ((t_n - t_0) / 100.0));
    }

    void reload() {
        float t_i;
        for (int i = 0; t_0 + i * step <= t_n; i += 1) {
            t_i = t_0 + i * step;
            x_values[i] = p.x(t_i);
            y_values[i] = p.y(t_i);
        }       
    }

//    @Override
//    public Point findClosestPoint(float x, float y) {
//        return new Point(PApplet.map(x, min_x, max_x, 0, width),
//                PApplet.map(p.y(x), min_y, max_y, 0, height));
//    }
}
