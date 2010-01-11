package CatVIS;
class ProjController {

    STProjGraph ap1, ap2;

    void updateA() {

    }

    public static void updateV(Main app) {
        Point old_v = app.pp.g.findClosestPoint(app.pp.ch.int_x, app.pp.ch.int_y);
        ((CatastropheGraph) app.pp.g).reload();
        Point new_v = app.pp.g.findClosestPoint(old_v.x, old_v.y);
        app.pp.ch.changeFocusInt(new_v);
        app.h.updatePP("main");
    }

    public static void updateAffectedProjs(Main app, String pp1_name, String pp2_name, float x, float y) {
        ProjectionPlane pp1, pp2;
        pp1 = (ProjectionPlane) app.h.pps.get(pp1_name);
        pp2 = (ProjectionPlane) app.h.pps.get(pp2_name);        
        ((STProjGraph) pp1.g).p.setT(x);
        ((STProjGraph) pp2.g).p.setT(y);
        ((STProjGraph) pp1.g).reload();
        ((STProjGraph) pp2.g).reload();                        
        app.h.updatePP(pp1_name);
        app.h.updatePP(pp2_name);
        updateV(app);
    }
}

class ABProjController extends ProjController implements CHController {

    public void run(Main app, float x, float y) {
//      app.println("CH Change X: " + x);
//      app.println("CH Change Y: " + y);
        ((CatastropheGraph) app.pp.g).p.setParamA(x);
        ((CatastropheGraph) app.pp.g).p.setParamB(y);
        updateAffectedProjs(app, "bc", "ac", x, y);
        app.aLabel.setValue(""+x);
        app.bLabel.setValue(""+y);
    }
}

class BCProjController extends ProjController implements CHController {

    public void run(Main app, float x, float y) {
//      app.println("CH Change X: " + x);
//      app.println("CH Change Y: " + y);
        ((CatastropheGraph) app.pp.g).p.setParamB(x);
        ((CatastropheGraph) app.pp.g).p.setParamC(y);
        updateAffectedProjs(app, "ac", "ab", x, y);
        app.cLabel.setValue(""+y);
        app.bLabel.setValue(""+x);
    }
}

class ACProjController extends ProjController implements CHController {

    public void run(Main app, float x, float y) {
//      app.println("CH Change X: " + x);
//      app.println("CH Change Y: " + y);
        ((CatastropheGraph) app.pp.g).p.setParamA(x);
        ((CatastropheGraph) app.pp.g).p.setParamC(y);
        updateAffectedProjs(app, "bc", "ab", x, y);
        app.aLabel.setValue(""+x);
        app.cLabel.setValue(""+y);
    }
}
