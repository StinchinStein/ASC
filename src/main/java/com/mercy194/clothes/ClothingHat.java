package com.mercy194.clothes;

import com.mercy194.gfx.SteinSkinnedBox;
import com.mercy194.gfx.SteinSkinnedPlane;
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
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ClothingHat extends AdvClothing {
	

	private SteinSkinnedBox hatTop;
	private SteinSkinnedPlane hatBottom;
	public ClothingHat() {
		super("hat");
		hatTop = new SteinSkinnedBox(48, 16, 16, 0, -4.0F, -9.0F, -4.0F, 8, 8, 8, 0.56F, false);
		hatBottom = new SteinSkinnedPlane(48, 16, 0, 0, -8.0F, -4.5F, -8.0F, 16, 16, 0.56F, false);
	}

	public void initialize() {

		super.initialize();
	}
	public void render(AbstractClientPlayerEntity ent, float x, float y, float z, float f4, float f5, float f6, float f7) {

		BufferBuilder b = Tessellator.getInstance().getBuffer();
		
		boolean isFirstPerson = (this.getRenderer() instanceof SteinPlayerRenderer);
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) return;
    	this.getRenderer().bindTexture(ClothingTextures.HAT[plr.getAccessory("hat_var")]);

    	if(!shouldRender(plr)) return;
        
    	
        if(plr.getAccessory("hat") == 1 /*&& ent.isWearing(PlayerModelPart.HAT)*/) { //TODO: Move somewhere outside this
        	pushMatrix(model.bipedHead, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        	
        		GlStateManager.translatef(0, 0.0625f, 0);
        	
        	hatTop.render(b, f7);
        	hatBottom.render(b, f7);
			GlStateManager.popMatrix();

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
