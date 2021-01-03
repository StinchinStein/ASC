package com.mercy194.main.gui.experimental;

import java.util.ArrayList;

import com.mercy194.main.CFG;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.screen.AdvContextMenu;
import com.mercy194.main.gui.screen.AdvSkinScreen;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.AbstractButton;

public class GUILabel extends GUIText {

	public GUILabel(GUISnap snap, String txt, int x, int y) {
		super(snap, txt, x, y);
	}
	public GUILabel(GUISnap snap, String[] txt, int x, int y) {
		super(snap, txt, x, y);
	}

	public void render() {
		//drawGUIBox((int) getX(), (int) getY(), (int) w, (txt.length*18)+3);
		if(CFG.gui.getBool("showBackground")) AbstractGui.fill(getX(), getY(), getX() + (int) w, (int) (getY() + h), 0x000000 + (80 << 24));
		GlStateManager.disableBlend();
		//String lStr = MainGUI.longest(txt);
		for(int i = 0; i < txt.length; i++) {
			Minecraft.getInstance().fontRenderer.drawString(txt[i], getX()+2, getY() + (i*18)+2, 0xFFFFFF);
		}
		GlStateManager.enableBlend();
		//drawText(txt[0], (int) getX()+5, (int) getY(), Color.white);
	}
	public ArrayList<AbstractButton> getContextButtons() {
		ArrayList<AbstractButton> btns = new ArrayList<AbstractButton>();
		AdvContextMenu ctx = AdvSkinScreen.ctxMenu;
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "Visible", button -> {
			GUILabel.this.setVisible(!GUILabel.this.isVisible);
        }));
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "Align", button -> {
			if(GUILabel.this.snap == GUISnap.TOPRIGHT) {
				GUILabel.this.setSnap(GUISnap.TOPLEFT);
			} else if(GUILabel.this.snap == GUISnap.TOP) {
				GUILabel.this.setSnap(GUISnap.TOPRIGHT);
			} else {
				GUILabel.this.setSnap(GUISnap.TOP);
			}
        }));
		return btns;
	}
}
