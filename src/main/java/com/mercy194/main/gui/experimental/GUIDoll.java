package com.mercy194.main.gui.experimental;

import com.mercy194.main.CFG;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class GUIDoll extends GUIComponent {

	public float scale = 1;
	public GUIDoll(GUISnap snap, int x, int y, int w, int h) {
		super(snap, x, y, w, h);
	}
	
	public void render(float partialTicks) {
		if(this.isVisible) {
			scale = 0.5f + Float.valueOf(CFG.gui.getParameter("doll_scale").toString());
			if(scale < 0.25f) scale = 0.25f;
			if(scale > 1.5f) scale = 1.5f;
			this.w = 70*scale;
			this.h = 68*scale;
	
			if(CFG.gui.getBool("showBackground")) AbstractGui.fill((int) getX(), (int) getY(), (int) (getX() + this.w), (int) (getY() + this.h), 0x000000 + (80 << 24));
			
			int height = Minecraft.getInstance().mainWindow.getHeight();
			double scaleFactor = Minecraft.getInstance().mainWindow.getGuiScaleFactor();
			
			
			float scissorX = this.x + 1;
			float scissorY = this.y + 1;
			float scissorW = this.x + 64;
			float scissorH = this.y + 53;
			//GL11.glScissor((int) (scissorX * scaleFactor), (int) (height - (scissorY + scissorH) * scaleFactor), (int) ((scissorW + scissorX) * scaleFactor), (int) (scissorH * scaleFactor));
	
			//GL11.glEnable(GL11.GL_SCISSOR_TEST);
			GlStateManager.color3f(1, 1, 1);
			AdvSkinClient.drawPaperDoll((int) (getX() + this.w / 2), (int) (getY() + this.h-5*scale), (int) (30*scale), Minecraft.getInstance().player, partialTicks);
			//GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}
		super.render();
	}

}
