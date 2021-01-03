package com.mercy194.main;

public class CFG {

	
	public static Configuration generic, gui;
	public CFG() {
 
        //Rendering Stuff
        generic = new Configuration("AdvancedSkinCustomization", "config");
        generic.setDefaultParameter("showArmor", true);
        generic.setDefaultParameter("showOffHand", true);
        generic.setDefaultParameter("showLayer", true);
        generic.setDefaultParameter("fp_enabled", false);
        generic.setDefaultParameter("fp_fov", 0.5f);
        generic.setDefaultParameter("fp_natural", true);
        generic.finish();
        
        //GUI Stuff
        gui = new Configuration("AdvancedSkinCustomization", "gui");
        gui.setDefaultParameter("showPaperDoll", true);
        gui.setDefaultParameter("doll_scale", 0.5f);

        gui.setDefaultParameter("showBiomeText", true);
        gui.setDefaultParameter("showBackground", true);
        gui.setDefaultParameter("showCoordText", true);
        gui.setDefaultParameter("showTimeText", true);
        gui.setDefaultParameter("showFPSText", true);
        gui.setDefaultParameter("showPUM", false);
        gui.setDefaultParameter("showPUML", false);
        gui.setDefaultParameter("showPUMSelf", false);
        gui.finish();
        
	}
}
