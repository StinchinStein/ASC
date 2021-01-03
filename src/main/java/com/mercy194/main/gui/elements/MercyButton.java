package com.mercy194.main.gui.elements;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MercyButton extends AbstractButton {
   protected final MercyButton.IPressable onPress;
   
   public MercyButton(int widthIn, int heightIn, int p_i51141_3_, int p_i51141_4_, String text, MercyButton.IPressable onPress) {
      super(widthIn, heightIn, p_i51141_3_, p_i51141_4_, text);
      this.onPress = onPress;
   }

   @Override
   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
	  Minecraft minecraft = Minecraft.getInstance();
      FontRenderer font = minecraft.fontRenderer;
      if(this.visible) {
	      this.isHovered = p_render_1_ >= this.x && p_render_2_ >= this.y && p_render_1_ < this.x + this.width && p_render_2_ < this.y + this.height;
	      
	      int clr = 0x000000 + (50 << 24);
	      if(this.active) clr = 0x000000 + (50 << 24);
	      if(this.isHovered()) clr = 0x000000 + (75 << 24);
	      fill(x+1, y+1, x + getWidth()-1, y + getHeight()-1, clr);
	      //fill(x+1, y+1, x + getWidth()-1, y + getHeight()-1, clr);
	      
	      int txtClr = 0x555555;
	      if(isHovered()&&active) {
	    	  txtClr = 0xFFFF66;
	      } else if(active) {
	    	  txtClr = 0xFFFFFF;
	      }
	      font.drawString(this.getMessage(), x + (this.width / 2) - (font.getStringWidth(this.getMessage()) / 2), y + getHeight() / 2 - 4, txtClr);
      }
      GlStateManager.color3f(1f, 1f, 1f);
   }
   public void onPress() {
      this.onPress.onPress(this);
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPressable {
      void onPress(MercyButton p_onPress_1_);
   }
}