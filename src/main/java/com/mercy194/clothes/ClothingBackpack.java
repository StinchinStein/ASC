package com.mercy194.clothes;

import com.mercy194.gfx.SteinSkinnedBox;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.ClothingTextures;
import com.mercy194.render.SteinPlayerRenderer;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ClothingBackpack extends AdvClothing {
	
	public SteinSkinnedBox b1, b2, b3;
	
	public ClothingBackpack() {
		super("backpack");
		b1 = new SteinSkinnedBox(32, 32, 0, 0, -4.0F, 0.0F, -2.0F, 8, 10, 3, 0.0F, false);
    	b2 = new SteinSkinnedBox(32, 32, 0, 13, -3.0F, 1.0F, 1.0F, 6, 9, 1, 0.0F, false);
    	b3 = new SteinSkinnedBox(32, 32, 14, 14, -3.0F, 10.0F, -2.0F, 6, 1, 3, 0.0F, false);
	}
	
	public void render(AbstractClientPlayerEntity ent, float x, float y, float z, float f4, float f5, float f6, float f7) {
		BufferBuilder b = Tessellator.getInstance().getBuffer();
		ItemStack itemstack = ent.inventory.armorItemInSlot(2);
		if(itemstack.getItem() == Items.ELYTRA) return;
		boolean isFirstPerson = (this.getRenderer() instanceof SteinPlayerRenderer);
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) model = this.getEntityModel();

    	if(!shouldRender(plr)) return;
    	rend.bindTexture(ClothingTextures.BACKPACK[plr.getAccessory("backpack_var")]);
    	
        float rPointX = model.bipedBody.rotationPointX;
        float rPointY = model.bipedBody.rotationPointY;
        float rPointZ = model.bipedBody.rotationPointZ;
        float rAngleX = (float) Math.toDegrees(model.bipedBody.rotateAngleX);
        float rAngleY = (float) Math.toDegrees(model.bipedBody.rotateAngleY);
        float rAngleZ = (float) Math.toDegrees(model.bipedBody.rotateAngleZ);
        
        if(plr.getAccessory("backpack") == 1) { //TODO: Move somewhere outside this
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);

			GlStateManager.pushMatrix();
	
	        GlStateManager.translatef(rPointX*0.06125f, rPointY*0.06125f, rPointZ*0.06125f);
			GlStateManager.rotatef(rAngleY, 0, 1, 0);
			GlStateManager.rotatef(rAngleX, 1, 0, 0);
			GlStateManager.rotatef(rAngleZ, 0, 0, 1);
	        GlStateManager.translatef(-rPointX*0.05f, -rPointY*0.06125f, -rPointZ*0.06125f);
			
			
			GlStateManager.translatef(0, 0, 0.0625f*4f); //shift down to correct position
			b1.render(b, f7);
			b2.render(b, f7);
			b3.render(b, f7);
			GlStateManager.popMatrix();
			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    GlStateManager.color3f(1f, 1f, 1f);
	}
}
