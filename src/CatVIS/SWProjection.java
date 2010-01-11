/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CatVIS;

/**
 *
 * @author Евгений
 */
public interface SWProjection extends IParametricGraph {

    float q(float r);

    void setT(float c_0);

    void setZero(float step);

}

class STProjAB implements SWProjection {

    float t, zero;

    STProjAB(float c_0) {
        setT(c_0);
    }

    public void setT(float c_0) {
        t = c_0;
    }

    public float q(float r) {
        if (Math.abs(r) < zero) {
            r = r > 0 ? zero : -zero;
        }
        return (float) (t + 15.0 * r * r * r * r / (3.0 * r * r * r));
    }

    public float x(float r) {
        return (float) (3.0 * q(r) - 6.0 * r * r);
    }

    public float y(float r) {
        return (float) (-6.0 * r * q(r) + 8.0 * r * r * r);
    }

    public void setZero(float zero) {
        this.zero = zero;
    }
}

class STProjAC implements SWProjection {

    float t, zero;

    STProjAC(float b_0) {
        setT(b_0);
    }

    public void setT(float b_0) {
        t = b_0;
    }

    public float q(float r) {
        if (Math.abs(r) < zero) {
            r = r > 0 ? zero : -zero;
        }
        return (float) ((t - 8.0 * r * r * r) / (-6.0 * r));
    }

    public float x(float r) {
        return (float) (3.0 * q(r) - 6.0 * r * r);
    }

    public float y(float r) {
        return (float) (3.0 * r * r * q(r) - 15.0 * r * r * r * r);
    }

    public void setZero(float zero) {
        this.zero = zero;
    }

}

class STProjBC implements SWProjection {

    float t, zero;

    STProjBC(float c_0) {
        setT(c_0);
    }

    public void setT(float c_0) {
        t = c_0;
    }

    public float q(float r) {
        return (float) (t + 6.0 * r * r / 3.0);
    }

    public float x(float r) {
        return (float) (-6.0 * r * q(r) + 8.0 * r * r * r);
    }

    public float y(float r) {
        return (float) (3.0 * r * r * q(r) - 15.0 * r * r * r * r);
    }

    public void setZero(float zero) {
        this.zero = zero;
    }

}

class STProjGraph extends GeneralGraph {

    float[] t_values;    
    SWProjection p;
    float t_0, t_n, step;

    STProjGraph(Main app, SWProjection p, float t_0, float t_n, float step) {
        super(app);
        this.p = p;
        this.t_0 = t_0;
        this.t_n = t_n;
        this.step = step;
        p.setZero(app.constrain(step, step, (float)0.05));
        int length = (int) ((t_n - t_0) / step) + 1;
        x_values = new float[length];
        y_values = new float[length];
        t_values = new float[length];
        reload();
    }

    STProjGraph(Main app, SWProjection p, float t_0, float t_n) {
        this(app, p, t_0, t_n, (float)((t_n-t_0)/100.0));
    }

    void reload() {
        float t_i;
        for (int i = 0; t_0 + i * step <= t_n; i += 1) {
            t_i = t_0 + i * step;
            x_values[i] = p.x(t_i);
            y_values[i] = p.y(t_i);
            t_values[i] = t_i;
        }
    }

    void reload(float t_0, float t_n) {
        this.t_0 = t_0;
        this.t_n = t_n;
        this.step = (float)((t_n-t_0)/100.0);
        reload();
    }
}