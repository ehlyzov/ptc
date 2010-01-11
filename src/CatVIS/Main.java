// NOTE: You need to manually change the package name on the next line and in the line within the main function.
// This will be automated in future versions once my Netbeans powers have developed...
package CatVIS;

import processing.core.*;
import controlP5.*;
import com.nootropic.processing.layers.*;

class VGraphController implements CHController {

    public void run(Main app, float x, float y) {
        app.valueLabel.setValue("X=" + x + " V=" + y);
    }
}


public class Main extends PApplet {

    ControlP5 controlP5;
    controlP5.Label l;
    controlP5.Textfield valueLabel, aLabel, bLabel, cLabel, rStart, rEnd;
    controlP5.Button btnApplyA, btnApplyB, btnApplyC, btnApplyR, btnShow3D, btnReset;
    controlP5.Slider sliderA, sliderB, sliderC, sliderX;
    PAppletLayers layers;
    Crosshair ch;
    Holder h;
    ProjectionPlane pp;
    ProjectionPlane pp_ab;
    ProjectionPlane pp_bc;
    ProjectionPlane pp_ac;
    SwallowtailV f;
    float a;

    // r0 and rn - used in parametrization
    float r0, rn;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{"--present", "CatVIS.Main"});
    }

    @Override
    public void setup() {
        size(600, 600);
        controlP5 = new ControlP5(this);
        layers = new PAppletLayers(this);
        //s Slider s = controlP5.addSlider("update",0,2,-5,0,0,100,10);
        valueLabel = controlP5.addTextfield("value", 20, 360, 200, 15);
        controlP5.controller("value").setLabel("");

        // Projection params UI

        rStart = controlP5.addTextfield("r_start", 430, 520, 35, 15);
        controlP5.controller("r_start").setLabel("R0");
        rStart.setValue(""+1.0);

        rEnd = controlP5.addTextfield("r_end", 545, 520, 35, 15);
        controlP5.controller("r_end").setLabel("Rn");
        rEnd.setValue(""+2.0);

        btnApplyR = controlP5.addButton("apply_r", 0, 470, 520, 70, 15);
        btnApplyR.captionLabel().style().marginLeft = 15;

        btnShow3D = controlP5.addButton("show_3d", 0, 430, 550, 150, 15);
        btnShow3D.captionLabel().style().marginLeft = 50;

        aLabel = controlP5.addTextfield("a_value", 20, 390, 100, 15);
        controlP5.controller("a_value").setLabel("First parameter");
        btnApplyA = controlP5.addButton("apply_a", 0, 130, 390, 45, 15);

        sliderA = controlP5.addSlider("slider_a", 0, 0, 190, 390, 220, 15);
        controlP5.controller("slider_a").setLabel("");

        bLabel = controlP5.addTextfield("b_value", 20, 420, 100, 15);
        controlP5.controller("b_value").setLabel("Second parameter");
        btnApplyB = controlP5.addButton("apply_b", 0, 130, 420, 45, 15);

        sliderB = controlP5.addSlider("slider_b", 0, 0, 190, 420, 220, 15);
        controlP5.controller("slider_b").setLabel("");

        cLabel = controlP5.addTextfield("c_value", 20, 450, 100, 15);
        controlP5.controller("c_value").setLabel("Third parameter");
        btnApplyC = controlP5.addButton("apply_c", 0, 130, 450, 45, 15);

        sliderC = controlP5.addSlider("slider_c", 0, 0, 190, 450, 220, 15);
        controlP5.controller("slider_c").setLabel("");

        //s l = s.captionLabel();
//        f = new SwallowtailV(1.0, -1.0, 1.0);
//        f_a = new FAc(0.0);
//        f_ab = new FABc(0.0);
        h = new Holder(this);

        SwallowtailV cat = new SwallowtailV((float) 2.0, (float) 2.0, (float) 2.0);
        CatastropheGraph g = new CatastropheGraph(this, cat, (float) -1, (float) 1.0);
        pp = new ProjectionPlane(this, 390, 300);
        pp.addGraph(g);
        pp.addCrosshair(new VGraphController(), true);
        h.addPP("main", pp, 20, 20);
        h.updatePP("main");
        aLabel.setValue(""+cat.a);
        bLabel.setValue(""+cat.b);
        cLabel.setValue(""+cat.c);

        STProjAB stp_ab = new STProjAB(cat.c);
        STProjGraph stpg_ab = new STProjGraph(this, stp_ab, (float) 1.0, (float) 2.0);
        pp_ab = new ProjectionPlane(this, 150, 150);
        pp_ab.addCrosshair(new ABProjController(), false);
        //pp_ab.ch.changeFocusInt(new Point(cat.a, cat.b));
        pp_ab.addGraph(stpg_ab);
        h.addPP("ab", pp_ab, 430, 20);
        h.updatePP("ab");


        STProjAC stp_ac = new STProjAC(cat.b);
        STProjGraph stpg_ac = new STProjGraph(this, stp_ac, (float) 1.0, (float) 2.0);
        pp_ac = new ProjectionPlane(this, 150, 150);
        pp_ac.addCrosshair(new ACProjController(), false);
        //pp_ac.ch.changeFocus(pp_ac.coords.int2absX(cat.a), pp_ac.coords.int2absY(cat.c));
        pp_ac.addGraph(stpg_ac);
        h.addPP("ac", pp_ac, 430, 190);
        h.updatePP("ac");

        STProjBC stp_bc = new STProjBC(cat.b);
        STProjGraph stpg_bc = new STProjGraph(this, stp_bc, (float) 1.0, (float) 2.0);
        pp_bc = new ProjectionPlane(this, 150, 150);
        pp_bc.addCrosshair(new BCProjController(), false);
        //pp_bc.ch.changeFocus(pp_bc.coords.int2absX(cat.b), pp_bc.coords.int2absY(cat.c));
        pp_bc.addGraph(stpg_bc);
        h.addPP("bc", pp_bc, 430, 360);
        h.updatePP("bc");

        apply_r(0);

    }

    @Override
    public void draw() {
        // TODO: handle each frame of drawing
    }

    @Override
    public void paint() {
        if (layers != null) {
            layers.paint(this);
        } else {
            super.paint();
        }
    }

    // Mouse events
    @Override
    public void mousePressed() {
        println("mousePressed");
        layers.mousePressed();
    }

//void mouseDragged() {
//  layers.mouseDragged();
//}
    @Override
    public void mouseReleased() {
        layers.mouseReleased();
    }

    public void apply_a(float f) {
          if ((((STProjGraph)pp_ab.g).min_x <= f) && (((STProjGraph)pp_ab.g).max_x >= f)) {
              pp_ab.ch.changeFocusInt(new Point(f, pp_ab.ch.int_y));
          } else {
              pp_ab.ch.hide();
          }
          if ((((STProjGraph)pp_ac.g).min_x <= f) && (((STProjGraph)pp_ac.g).max_x >= f)) {
              pp_ac.ch.changeFocusInt(new Point(f, pp_ac.ch.int_y));
          } else {
              pp_ac.ch.hide();
          }
          ((CatastropheGraph) pp.g).p.setParamA(f);
          ProjController.updateV(this);
    }

    public void apply_c(float f) {
          if ((((STProjGraph)pp_ac.g).min_y <= f) && (((STProjGraph)pp_ac.g).max_y >= f)) {
              pp_ac.ch.changeFocusInt(new Point(pp_ac.ch.int_x, f));
          } else {
              pp_ac.ch.hide();
          }
          if ((((STProjGraph)pp_bc.g).min_y <= f) && (((STProjGraph)pp_bc.g).max_y >= f)) {
              pp_bc.ch.changeFocusInt(new Point(pp_bc.ch.int_x, f));
          } else {
              pp_bc.ch.hide();
          }
          ((CatastropheGraph) pp.g).p.setParamC(f);
          ProjController.updateV(this);
    }


    public void apply_b(float f) {
          if ((((STProjGraph)pp_ab.g).min_y <= f) && (((STProjGraph)pp_ab.g).max_y >= f)) {
              pp_ab.ch.changeFocusInt(new Point(pp_ab.ch.int_x, f));
          } else {
              pp_ab.ch.hide();
          }
          if ((((STProjGraph)pp_bc.g).min_x <= f) && (((STProjGraph)pp_bc.g).max_x >= f)) {
              pp_bc.ch.changeFocusInt(new Point(f, pp_bc.ch.int_y));
          } else {
              pp_bc.ch.hide();
          }
          ((CatastropheGraph) pp.g).p.setParamB(f);
          ProjController.updateV(this);
    }


    public void apply_r(float value) {
        float min_p, max_p;
        r0 = Float.parseFloat(rStart.getText());
        rn = Float.parseFloat(rEnd.getText());
        ((STProjGraph)pp_ab.g).reload(r0, rn);
        ((STProjGraph)pp_ac.g).reload(r0, rn);
        ((STProjGraph)pp_bc.g).reload(r0, rn);
        h.updatePP("ab");
        h.updatePP("ac");
        h.updatePP("bc");
        println(((STProjGraph)pp_ab.g).min_x);
        println(((STProjGraph)pp_ab.g).max_x);
        println(((STProjGraph)pp_ab.g).min_y);
        println(((STProjGraph)pp_ab.g).max_y);
        min_p = min(((STProjGraph)pp_ab.g).min_x, ((STProjGraph)pp_ac.g).min_x);
        max_p = max(((STProjGraph)pp_ab.g).max_x, ((STProjGraph)pp_ac.g).max_x);
        apply_a(min_p);
        sliderA.setMin(min_p);
        sliderA.setMax(max_p);
        min_p = min(((STProjGraph)pp_ab.g).min_y, ((STProjGraph)pp_bc.g).min_x);
        max_p = max(((STProjGraph)pp_ab.g).max_y, ((STProjGraph)pp_bc.g).max_x);
        //change_b(min_p);
        sliderB.setMin(min_p);
        sliderB.setMax(max_p);
        min_p = min(((STProjGraph)pp_bc.g).min_y, ((STProjGraph)pp_ac.g).min_y);
        max_p = max(((STProjGraph)pp_bc.g).max_y, ((STProjGraph)pp_ac.g).max_y);
        //change_c(min_p);
        sliderC.setMin(min_p);
        sliderC.setMax(max_p);

        ((CatastropheGraph)pp.g).reload();
        
        h.updatePP("main");
        println("Ro=" + r0 + " Rn=" + rn);
    }

    void slider_a(float f) {
        apply_a(f);
    }
    
    void slider_b(float f) {
        apply_b(f);
//        pp_ab.ch.changeFocusInt(new Point(pp_ab.ch.int_x, f));
//        pp_bc.ch.changeFocusInt(new Point(f, pp_bc.ch.int_y));
    }

    void slider_c(float f) {
        apply_c(f);
//        pp_ac.ch.changeFocusInt(new Point(pp_ac.ch.int_x, f));
//        pp_bc.ch.changeFocusInt(new Point(pp_bc.ch.int_x, f));
    }

}
