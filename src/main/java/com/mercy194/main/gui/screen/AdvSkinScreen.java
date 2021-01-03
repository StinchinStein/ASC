package com.mercy194.main.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.experimental.GUIComponent;
import com.mercy194.main.gui.experimental.GUIDoll;
import com.mercy194.main.gui.experimental.GUILabel;
import com.mercy194.main.gui.experimental.GUISnap;
import com.mercy194.main.proxy.AdvSkinClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.overlay.BossOverlayGui;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class AdvSkinScreen {


	//public static AdvGuiLabel lblBiome, lblFPS;
	//public static AdvGuiDoll paperDoll;
	
	public static ArrayList<GUIComponent> ELEMENTS = new ArrayList<GUIComponent>();

	public static GUILabel lblBiome, lblFPS, lblTime, lblCoords;
	public static GUIDoll paperDoll;
	
	public static AdvContextMenu ctxMenu = null;
	//public static ArrayList<AdvGuiElement> ELEMENTS = new ArrayList<AdvGuiElement>();
	public AdvSkinScreen() {
		//ELEMENTS.add(lblBiome = new AdvGuiLabel("", 1, 1));
		//ELEMENTS.add(lblFPS = new AdvGuiLabel("", 1, 1));
		//ELEMENTS.add(paperDoll = new AdvGuiDoll(1, 13));
		lblBiome = new GUILabel(GUISnap.TOPLEFT, "Biome", 1, 1);
		ELEMENTS.add(lblBiome);

		lblFPS = new GUILabel(GUISnap.TOPLEFT, "FPS", 20, 1);
		ELEMENTS.add(lblFPS);

		lblTime = new GUILabel(GUISnap.TOP, "Time", 0, 1);
		ELEMENTS.add(lblTime);
		
		lblCoords = new GUILabel(GUISnap.TOPLEFT, "Coordinates", 1, 82);
		ELEMENTS.add(lblCoords);
		
		paperDoll = new GUIDoll(GUISnap.TOPLEFT, 1, 13, 70, 68);
		ELEMENTS.add(paperDoll);
	}
	
	
	public void render(float partialTicks) {
		if(!Minecraft.getInstance().gameSettings.showDebugInfo) {
			Minecraft mc = Minecraft.getInstance();
			FontRenderer fR = mc.fontRenderer;
			MouseHelper h = Minecraft.getInstance().mouseHelper;
			double mX = h.getMouseX() / Minecraft.getInstance().mainWindow.getGuiScaleFactor();
			double mY = h.getMouseY() / Minecraft.getInstance().mainWindow.getGuiScaleFactor();
			/*
	    	if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
			
				if(!(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen)) {
					ctxMenu = null;
				} else {
		    		for(int i = 0; i < ELEMENTS.size(); i++) {
		    			GUIComponent c = ELEMENTS.get(i);
		    			if(!c.isVisible) {
		    				AbstractGui.fill(c.getX(), (int) c.getY(), (int)  (c.getX() + c.w), (int) (c.getY() + c.h), 0x22000000);
		    			}
		    		}
				}
	    		for(int i = 0; i < ELEMENTS.size(); i++) {
	    			GUIComponent c = ELEMENTS.get(i);
	    			if(mX > c.getX() && mY > c.getY() && mX < c.getX() + c.w && mY < c.getY() + c.h) {
	    				AbstractGui.fill((int) c.getX(), (int) c.getY(), (int) (c.x + c.w), (int) (c.y + c.h), 0x4400FFFF);
	    			}
	    		}
	    	}
			*/
    		String biome = "Biome: " + Minecraft.getInstance().world.getBiome(Minecraft.getInstance().player.getPosition()).getDisplayName().getFormattedText();
			if(CFG.gui.getBool("showBiomeText") ) {
				lblBiome.setText(biome);
				lblBiome.hardRender();
    		}

			if(CFG.gui.getBool("showFPSText")) {
	    		String fpsStr = Minecraft.getDebugFPS() + "fps";
				if(CFG.gui.getBool("showBiomeText")) {
					lblFPS.setX((int) (lblBiome.getX() + lblBiome.w + 1));
				} else {
					lblFPS.setX(1);
				}
	    		lblFPS.setText(fpsStr);
				lblFPS.hardRender();
			}

			if(CFG.gui.getBool("showTimeText")) {
	        	BossOverlayGui bGui = Minecraft.getInstance().ingameGUI.getBossOverlay();
	        	Map<UUID, ClientBossInfo> mapBossInfos = ObfuscationReflectionHelper.getPrivateValue(BossOverlayGui.class, bGui, AdvSkinHelper.Obfuscation.BOSS_BAR_OVERLAY);

	    		long time = Minecraft.getInstance().world.getDayTime() % 24000;
	    		int hours = (int) (time / 1000 + 6);
	    		String minutes = "" + ((time % 1000) * 60 / 1000);
	    		if(((time % 1000) * 60 / 1000) < 10) {
	    			minutes = "0" + ((time % 1000) * 60 / 1000);
	    		}
	    		String ampm = "AM";
	    		if(hours >= 12) { hours-=12; ampm = "PM"; }
	    		if(hours >= 12) { hours-=12; ampm = "AM"; }
	            if (hours == 0) hours = 12;
	            lblTime.setY(1 + (19*mapBossInfos.size()));
	            lblTime.setText("" + hours + ":" + minutes + " " + ampm);
	            lblTime.hardRender();
			}
    		if(CFG.gui.getBool("showCoordText")) {
	    		BlockPos pos = Minecraft.getInstance().player.getPosition();
	    		String posStr = pos.getX() + ", " + pos.getY() + ", " + pos.getZ();

				if(CFG.gui.getBool("showPaperDoll")) {
					lblCoords.setY((int) (paperDoll.getY() + paperDoll.h) + 1);
				} else {
					lblCoords.setY(13);
				}
	    		lblCoords.setText(posStr);
	    		lblCoords.hardRender();
	    	}
			
			
    		if(CFG.gui.getBool("showPaperDoll")) {
    			paperDoll.render(partialTicks);
    			if(!CFG.gui.getBool("showBiomeText") && !CFG.gui.getBool("showFPSText")) {
    				paperDoll.setY(1);
    			} else {
    				paperDoll.setY(13);
    			}
    		}
    		
    		if(CFG.gui.getBool("showPUM")) {
	    		List<ClothingPlayer> l = AdvSkinMod.getPlayersUsingMod();
	    		for(int i = 0; i < l.size(); i++) {
		    		if(!CFG.gui.getBool("showPUMSelf") && l.get(i).username.contains(Minecraft.getInstance().player.getUniqueID().toString())) {
		    			l.remove(i);
		    		}
	    		}
	    		String t = "Players Using ASC: " + l.size();
	    		//fR.drawString(t, mc.mainWindow.getScaledWidth() - 2 - fR.getStringWidth(t), 3, 0xFFFFFF);
	    		AdvSkinClient.drawRightTextLabel(t, mc.mainWindow.getScaledWidth() - 1, 1);
	    		if(CFG.gui.getBool("showPUML")) {
		    		if(Minecraft.getInstance().world != null) {
			    		for(int i = 0; i < l.size(); i++) {
			    			try {
				    			String txt = Minecraft.getInstance().world.getPlayerByUuid(UUID.fromString(l.get(i).username)).getDisplayName().getFormattedText();
				    			//fR.drawString(txt, mc.mainWindow.getScaledWidth() - 2 - fR.getStringWidth(txt), 14 + (i*12), 0xFFFFFF);	
				    			AdvSkinClient.drawRightTextLabel(txt, mc.mainWindow.getScaledWidth() - 1, 12 + (i*11));
					    	} catch(Exception e) {}
			    		}
		    		}
	    		}
    		}
		}
		
		if(ctxMenu != null) ctxMenu.render(partialTicks);
	}

    	
    public void mouseClicked(double mX, double mY, int btn) {
    	/*
    	if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
			for(int i = 0; i < ELEMENTS.size(); i++) {
				GUIComponent c = ELEMENTS.get(i);
				if(mX > c.getX() && mY > c.getY() && mX < c.getX() + c.w && mY < c.getY() + c.h) {
					if(btn == 1) {
						ctxMenu = new AdvContextMenu((int) mX, (int) mY, c);
	
						if(c.getContextButtons() != null) {
							ctxMenu.ELEMENTS = c.getContextButtons();
						}
					}
				}
			}
			if(ctxMenu != null) ctxMenu.mouseClicked(mX, mY, btn);
    	}
    	*/
    }
	public void mouseDragged(double mouseX, double mouseY, double dragX, double dragY, int btn) {
    	/*
		if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
			if(btn == 0) {
				for(int i = 0; i < ELEMENTS.size(); i++) {
					GUIComponent c = ELEMENTS.get(i);
					if(mouseX > c.getX() && mouseY > c.getY() && mouseX < c.getX() + c.w && mouseY < c.getY() + c.h) {
						c.x += dragX;
						c.y += dragY;
					}
				}
			}
    	}*/
	}
}
