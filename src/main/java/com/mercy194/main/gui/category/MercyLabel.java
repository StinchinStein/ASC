package com.mercy194.main.gui.category;

import java.util.ArrayList;
import java.util.List;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.CFG;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

public class MercyLabel {

	public int x, y;
	public String dispName = "missingno";
	public int align = 0;
	public boolean hovering = false;

	public MercyLabel(String name, int x, int y, int align) {
		this.x = x;
		this.y = y;
		this.dispName = name;
		this.align = 0;
	}
	public MercyLabel(String name, int x, int y) {
		this(name, x, y, 0);
	}
	
	private boolean clickOnce = false;
	public void render() {
    	MouseHelper mH = Minecraft.getInstance().mouseHelper;
    	
        GlStateManager.disableBlend();
        int hClr = (hovering?114:96);
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(dispName) + 3;
        
		switch(align) {
			case 0:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x, y, x + Minecraft.getInstance().fontRenderer.getStringWidth(dispName) + 3, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x+2, y+2, 0xFFFFFF);
			break;
			case 1:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w, y, x, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x-w+2, y+2, 0xFFFFFF);
			break;
			case 2:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w / 2, y, x + w / 2+1, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x- w / 2+2, y+2, 0xFFFFFF);
			break;
		}
		
		if(!AdvSkinMod.MOUSE_PRESSED) clickOnce = false;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setX(int v) {
		this.x = v;
	}
	public void setY(int v) {
		this.y = v;
	}
}
