/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CatVIS;

/**
 *
 * @author Евгений
 */
public class Point {
  float x;
  float y; 

  Point(float[] coords) {
    this.x = coords[0];
    this.y = coords[1];
  }

  Point(float x, float y) {   
    this.x = x;
    this.y = y;
  }

}