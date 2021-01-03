package com.mercy194.main.gui.experimental;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;

import net.minecraft.client.Minecraft;

public class GUIText extends GUIComponent {

	public String[] txt;
	public GUIText(GUISnap snap, String txt, int x, int y) {
		super(snap, x, y, Minecraft.getInstance().fontRenderer.getStringWidth(txt)+10, 11);
		this.txt = new String[] { txt };
	}

	public GUIText(GUISnap snap, String[] txt2, int x, int y) {
		super(snap, x, y, Minecraft.getInstance().fontRenderer.getStringWidth(AdvSkinHelper.longest(txt2))+10, 20);
		this.txt = txt2;
	}

	public void render() {
		//drawText(txt[0], (int) getX()+6, (int) getY()+1, Color.black);
		//drawText(txt[0], (int) getX()+5, (int) getY(), Color.white);
	}

	public void setText(String t) {
		
		this.txt[0] = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(txt[0])+4);
	}
	public void setText(String[] t) {
		this.txt = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(AdvSkinHelper.longest(txt))+4);
	}
	public void setText(int ind, String t) {
		this.txt[ind] = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(AdvSkinHelper.longest(txt))+4);
	}
	
	
}
