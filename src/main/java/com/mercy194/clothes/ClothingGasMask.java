package com.mercy194.clothes;

import com.mercy194.gfx.SteinSkinnedBox;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.render.SteinPlayerRenderer;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ClothingGasMask extends AdvClothing {
	
	private ResourceLocation TEXTURE = new ResourceLocation(AdvSkinMod.MODID, "clothes/gas_mask/variant0.png");
	
	private SteinSkinnedBox cBreather, lVent, rVent;
	public ClothingGasMask() {
		super("gas_mask");
		cBreather = new SteinSkinnedBox(16, 16, 0, 0, -1.0F, -2.75F, -5.2F, 2, 3, 1, 0.13F, false);
		lVent = new SteinSkinnedBox(16, 16, 5, 6, -3.0F, -2.0F, -5.0F, 2, 2, 1, 0.13F, false);
		rVent = new SteinSkinnedBox(16, 16, 0, 4, 1.0F, -2.0F, -5.0F, 2, 2, 1, 0.13F, false);
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
    	this.getRenderer().bindTexture(TEXTURE);

    	if(!shouldRender(plr)) return;
        
        if(plr.getAccessory("mask") == 1 /*&& ent.isWearing(PlayerModelPart.HAT)*/) { //TODO: Move somewhere outside this
			
        	pushMatrix(model.bipedHead, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            
			cBreather.render(b, f7);
			
			GlStateManager.pushMatrix();
				GlStateManager.translatef(0.12f, 0, -0.08f);
	            GlStateManager.rotatef(24, 0.0F, 1.0F, 0.0F);
				lVent.render(b, f7);
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
				GlStateManager.translatef(-0.12f, 0, -0.08f);
	            GlStateManager.rotatef(-24, 0.0F, 1.0F, 0.0F);
				rVent.render(b, f7);
			GlStateManager.popMatrix();
			
			GlStateManager.popMatrix();

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
