package com.mercy194.main.gui.screen;

import com.mercy194.main.CFG;
import com.mercy194.main.gui.category.MercyLabel;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SteinGUISettingsScreen extends Screen {

	
    private Screen lastScreen;
    private AbstractSlider scalSlider;
    public SteinGUISettingsScreen(Screen inst) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.lastScreen = inst;
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    protected void init() {
        int j = this.height / 2 - 48;
        
        
        this.addButton(new Button(2, 95, 98, 20, "Paper Doll: " + (CFG.gui.getBool("showPaperDoll")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPaperDoll", !CFG.gui.getBool("showPaperDoll"));
        	CFG.gui.save();
        	button.setMessage("Paper Doll: " + (CFG.gui.getBool("showPaperDoll")?"ON":"OFF"));
        }));
        
        this.addButton(new Button(2, 95 + (22*1), 98, 20, "Biome: " + (CFG.gui.getBool("showBiomeText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showBiomeText", !CFG.gui.getBool("showBiomeText"));
        	CFG.gui.save();
        	button.setMessage("Biome: " + (CFG.gui.getBool("showBiomeText")?"ON":"OFF"));
        }));
        
        this.addButton(new Button(2, 95 + (22*2), 98, 20, "Coordinates: " + (CFG.gui.getBool("showCoordText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showCoordText", !CFG.gui.getBool("showCoordText"));
        	CFG.gui.save();
        	button.setMessage("Coordinates: " + (CFG.gui.getBool("showCoordText")?"ON":"OFF"));
        }));

        this.addButton(new Button(2, 95 + (22*3), 98, 20, "Frame Rate: " + (CFG.gui.getBool("showFPSText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showFPSText", !CFG.gui.getBool("showFPSText"));
        	CFG.gui.save();
        	button.setMessage("Frame Rate: " + (CFG.gui.getBool("showFPSText")?"ON":"OFF"));
        }));

        this.addButton(new Button(2, 95 + (22*4), 98, 20, "Background: " + (CFG.gui.getBool("showBackground")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showBackground", !CFG.gui.getBool("showBackground"));
        	CFG.gui.save();
        	button.setMessage("Background: " + (CFG.gui.getBool("showBackground")?"ON":"OFF"));
        }));

        this.addButton(new Button(this.width / 2 - 98/2, 16, 98, 20, "Time of Day: " + (CFG.gui.getBool("showTimeText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showTimeText", !CFG.gui.getBool("showTimeText"));
        	CFG.gui.save();
        	button.setMessage("Time of Day: " + (CFG.gui.getBool("showTimeText")?"ON":"OFF"));
        }));


        Button tmp = this.addButton(new Button(this.width - 98 - 2, 32 + (22*1), 98, 20, "Player List: " + (CFG.gui.getBool("showPUML")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUML", !CFG.gui.getBool("showPUML"));
        	CFG.gui.save();
        	button.setMessage("Player List: " + (CFG.gui.getBool("showPUML")?"ON":"OFF"));
        }));
    	tmp.visible = CFG.gui.getBool("showPUM");
    	
        Button tmp2 = this.addButton(new Button(this.width - 98 - 2, 32 + (22*2), 98, 20, "Show Self: " + (CFG.gui.getBool("showPUMSelf")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUMSelf", !CFG.gui.getBool("showPUMSelf"));
        	CFG.gui.save();
        	button.setMessage("Show Self: " + (CFG.gui.getBool("showPUMSelf")?"ON":"OFF"));
        }));
    	tmp2.visible = CFG.gui.getBool("showPUM");

        this.addButton(new Button(this.width - 98 - 2, 32, 98, 20, "Player Count: " + (CFG.gui.getBool("showPUM")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUM", !CFG.gui.getBool("showPUM"));
        	CFG.gui.save();
        	tmp.visible = CFG.gui.getBool("showPUM");
        	tmp2.visible = CFG.gui.getBool("showPUM");
        	button.setMessage("Player Count: " + (CFG.gui.getBool("showPUM")?"ON":"OFF"));
        }));
         

		float scal = Float.valueOf(CFG.gui.getParameter("doll_scale").toString());
		scalSlider = new AbstractSlider(104, 95, 64, 20, scal) {
			
			@Override
			protected void updateMessage() {
				float fov = Float.valueOf(CFG.gui.getParameter("doll_scale").toString());
				scalSlider.setMessage("Scale: " + (int) (fov * 100));
			}
			
			@Override
			protected void applyValue() {
				CFG.gui.setParameter("doll_scale", (float) this.value);
				CFG.gui.save();
				//modelRotation = (float) this.value;
			}
		};
		scalSlider.setMessage("Scale: " + (int) (scal * 100));
		addButton(scalSlider);

        this.addButton(new Button(this.width / 2 - 98/2, j+66, 98, 20, "Done", button -> {
            Minecraft.getInstance().displayGuiScreen(this.lastScreen);
        }));
        
        
    }
    public void render(int f1, int f2, float f3) {
        //this.renderBackground();
        
        this.drawCenteredString(this.font, "Advanced Skin Customization - GUI Settings", this.width / 2, this.height / 2 - 40, 0xFFFFFF);

		//drawEntityOnScreen(xP, yP, 60, xP - f1, yP-100 - f2, Minecraft.getInstance().player);
        
        super.render(f1, f2, f3);
    }
}
