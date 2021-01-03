package com.mercy194.main.gui.experimental;

import java.util.ArrayList;

import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.screen.AdvContextMenu;
import com.mercy194.main.gui.screen.AdvSkinScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;

public class GUIComponent {

	public GUIComponent parent;
	
	public ArrayList<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();
	
	public float x, y, w, h;
	public GUISnap snap;
	public boolean isVisible = true;
	public boolean isDragging = false;
	public GUIComponent(GUISnap snap, int x, int y, int w, int h) {
		this.snap = snap;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public GUIComponent(int x, int y, int w, int h) {
		this.snap = GUISnap.CENTER;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void hardRender() {
		if(isVisible) {
			render();
			for(GUIComponent c : COMPONENTS) {
				c.render();
			}
		}
	}
	
	public void render() {}

	
	public int getX() {
		float tX = 0;
		if(parent == null) {
			float WIDTH = Minecraft.getInstance().mainWindow.getScaledWidth();
			switch(snap) {
				case CENTER:
					tX = (WIDTH / 2) - ((w / 2));
				break;
				case LEFT: case TOPLEFT: case BOTTOMLEFT:
					tX = x;
				break;
				case RIGHT: case TOPRIGHT: case BOTTOMRIGHT:
					tX = WIDTH - x - w;
				break;
				case TOP:
					tX = (WIDTH / 2) - x - ((w / 2));
				break;
				case BOTTOM:
					tX = (WIDTH / 2) - x - ((w / 2));
				break;
			}
		} else {
			switch(snap) {
				case TOP: case CENTER:  case BOTTOM:
					tX = parent.getX() + parent.w / 2 - ((w / 2));
				break;
				case LEFT: case TOPLEFT: case BOTTOMLEFT:
					tX = parent.getX() + (x);
				break;
				case RIGHT: case TOPRIGHT: case BOTTOMRIGHT:
					tX = ((parent.getX() + parent.w) - x) - w;
				break;
			}
		}
		return (int) tX;
	}
	public int getY() {
		float tY = 0;
		if(parent == null) {
			float HEIGHT = Minecraft.getInstance().mainWindow.getScaledHeight();
			
			switch(snap) {
				case LEFT: case CENTER: case RIGHT:
					tY = (HEIGHT / 2) - ((h / 2));
				break;
				case BOTTOMLEFT: case BOTTOM: case BOTTOMRIGHT:
					tY = HEIGHT - y - h;
				break;
				case TOPLEFT: case TOP: case TOPRIGHT:
					tY = y;
				break;
			}
		} else {
			switch(snap) {
				case LEFT: case CENTER: case RIGHT:
					tY = parent.getY() + (parent.h / 2) - (h / 2);
				break;
				case BOTTOMLEFT: case BOTTOM: case BOTTOMRIGHT:
					tY = (parent.getY() + parent.h) - y - h;
				break;
				case TOPLEFT: case TOP: case TOPRIGHT:
					tY = parent.getY() + y;
				break;
			}
		}
		return (int) tY;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int w) {
		this.w = w;
	}
	public void setHeight(int h) {
		this.h = h;
	}
	public void setSnap(GUISnap s) {
		this.snap = s;
	}
	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}
	
	public void setVisible(boolean v) {
		this.isVisible = v;
	}
	public void add(GUIComponent c) {
		c.setParent(this);
		COMPONENTS.add(c);
	}
	public ArrayList<AbstractButton> getContextButtons() {
		ArrayList<AbstractButton> btns = new ArrayList<AbstractButton>();
		AdvContextMenu ctx = AdvSkinScreen.ctxMenu;
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "Visible", button -> {
			GUIComponent.this.setVisible(!GUIComponent.this.isVisible);
        }));
		return btns;
	}
}
