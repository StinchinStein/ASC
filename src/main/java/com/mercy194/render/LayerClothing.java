package com.mercy194.render;

import com.mercy194.main.AdvSkinMod;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;

public class LayerClothing extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

	public LayerClothing(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> player) {
		super(player);
	}

	@Override
	public void render(AbstractClientPlayerEntity ent, float x, float y, float z, float f4, float f5, float f6, float f7) {
		if(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(ent.getGameProfile()).toString()) != null) {
			if(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(ent.getGameProfile()).toString()).usingMod) {
	        	GlStateManager.pushMatrix();
		        if (ent.shouldRenderSneaking()) GlStateManager.translatef(0.0F, 0.2F, 0.0F);
	
	        	for(int adv = 0; adv < AdvSkinMod.CLOTHING.size(); adv++) {
        			AdvSkinMod.CLOTHING.get(adv).render(ent, x, y, z, f4, f5, f6, f7);
	        	}
	        	GlStateManager.popMatrix();
	        }
        }
	}

	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
