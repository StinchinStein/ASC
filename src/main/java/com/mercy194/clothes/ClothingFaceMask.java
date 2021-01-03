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
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ClothingFaceMask extends AdvClothing {
	

	private SteinSkinnedBox faceMask;
	public ClothingFaceMask() {
		super("face_mask");
    	faceMask = new SteinSkinnedBox(32, 16, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.55f, false);
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
    	this.getRenderer().bindTexture(ClothingTextures.FACE_MASK[plr.getAccessory("facemask_var")]);
    	//this.getRenderer().bindTexture(this.getVariations().get(plr.getAccessory("facemask_var")).getTexutre());

    	if(!shouldRender(plr)) return;
        
        if(plr.getAccessory("facemask") == 1) { //TODO: Move somewhere outside this
        	pushMatrix(model.bipedHead, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            
        	faceMask.render(b, f7);
			GlStateManager.popMatrix();

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
