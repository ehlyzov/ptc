/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CatVIS;

/**
 *
 * @author Евгений
 */
class Coords {

    public float center_x, center_y, shift_x, shift_y;

    Coords(float shift_x, float shift_y, float center_x, float center_y) {
        this.center_x = center_x;
        this.center_y = center_y;
    }

    float abs2relX(float x) {
        return x-shift_x;
    }

    float abs2relY(float y) {
        return y-shift_y;
    }

    float int2absX(float x) {
        return shift_x + x;
    }

    float int2absY(float y) {
        return shift_y + 2*center_y-y;
    }


    float transform_x_rel(float x) {
        return x + center_x;
    }

    float transform_y_rel(float y) {
        return center_y - y;
    }

    float transform_x_abs(float x) {
        return transform_x_rel(x - shift_x);
    }

    float transform_y_abs(float y) {
        return transform_y_rel(y - shift_y);
    }

    float decode_x_rel(float x) {
        return x + center_x;
    }

    float decode_y_rel(float y) {
        return center_y - y;
    }

    float decode_y_abs(float y) {
        return decode_y_rel(y) + shift_y;
    }

    float decode_x_abs(float x) {
        return decode_x_rel(x) + shift_x;
    }

    boolean isInside(float x, float y) {
        return (x > shift_x)
                && (x < shift_x + 2*center_x)
                && (y > shift_y)
                && (y < shift_y + 2*center_y);
    }
}
