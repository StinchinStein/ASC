package com.mercy194.clothes.transformer;

import org.lwjgl.opengl.GL11;

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
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ClothingTFWings extends AdvClothing {

    RendererModel LWing1;
    RendererModel LWing2;
    RendererModel LWing3;
    RendererModel LWing4;
    RendererModel LWing5;
    RendererModel LWing6;
    RendererModel LWing7;
    RendererModel LWing8;
    RendererModel LWing9;
    RendererModel LWing10;
    
    RendererModel RWing1;
    RendererModel RWing2;
    RendererModel RWing3;
    RendererModel RWing4;
    RendererModel RWing5;
    RendererModel RWing6;
    RendererModel RWing7;
    RendererModel RWing8;
    RendererModel RWing9;
    RendererModel RWing10;
	
    float wingRotation = 0;
	public ClothingTFWings() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {

		LWing1 = new RendererModel(this.getEntityModel(), 10, 0).setTextureSize(64, 32);
	    LWing1.addBox(0F, 0F, 0F, 3, 12, 1);
	    LWing1.setRotationPoint(-4F, 0F, 2F);
	    LWing1.mirror = true;
	      LWing2 = new RendererModel(this.getEntityModel(), 18, 0).setTextureSize(64, 32);
	      LWing2.addBox(0F, 0F, 0F, 2, 13, 1);
	      LWing2.setRotationPoint(-6F, -2F, 2F);
	      LWing2.mirror = true;
	      LWing3 = new RendererModel(this.getEntityModel(), 24, 0).setTextureSize(64, 32);
	      LWing3.addBox(0F, 0F, 0F, 1, 13, 1);
	      LWing3.setRotationPoint(-7F, -3F, 2F);
	      LWing3.mirror = true;
	      LWing4 = new RendererModel(this.getEntityModel(), 28, 0).setTextureSize(64, 32);
	      LWing4.addBox(0F, 0F, 0F, 1, 12, 1);
	      LWing4.setRotationPoint(-8F, -3F, 2F);
	      LWing4.mirror = true;
	      LWing5 = new RendererModel(this.getEntityModel(), 32, 0).setTextureSize(64, 32);
	      LWing5.addBox(0F, 0F, 0F, 1, 11, 1);
	      LWing5.setRotationPoint(-9F, -3F, 2F);
	      LWing5.mirror = true;
	      LWing6 = new RendererModel(this.getEntityModel(), 36, 0).setTextureSize(64, 32);
	      LWing6.addBox(0F, 0F, 0F, 2, 10, 1);
	      LWing6.setRotationPoint(-11F, -3F, 2F);
	      LWing6.mirror = true;
	      LWing7 = new RendererModel(this.getEntityModel(), 42, 0).setTextureSize(64, 32);
	      LWing7.addBox(0F, 0F, 0F, 2, 9, 1);
	      LWing7.setRotationPoint(-13F, -3F, 2F);
	      LWing7.mirror = true;
	      LWing8 = new RendererModel(this.getEntityModel(), 48, 0).setTextureSize(64, 32);
	      LWing8.addBox(0F, 0F, 0F, 2, 8, 1);
	      LWing8.setRotationPoint(-15F, -3F, 2F);
	      LWing8.mirror = true;
	      LWing9 = new RendererModel(this.getEntityModel(), 54, 0).setTextureSize(64, 32);
	      LWing9.addBox(0F, 0F, 0F, 1, 8, 1);
	      LWing9.setRotationPoint(-16F, -4F, 2F);
	      LWing9.mirror = true;
	      LWing10 = new RendererModel(this.getEntityModel(), 58, 0).setTextureSize(64, 32);
	      LWing10.addBox(0F, 0F, 0F, 1, 8, 1);
	      LWing10.setRotationPoint(-17F, -5F, 2F);
	      LWing10.mirror = true;

	      RWing1 = LWing1;
	      RWing2 = LWing2;
	      RWing3 = LWing3;
	      RWing4 = LWing4;
	      RWing5 = LWing5;
	      RWing6 = LWing6;
	      RWing7 = LWing7;
	      RWing8 = LWing8;
	      RWing9 = LWing9;
	      RWing10 = LWing10;

		super.initialize();
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

    	rend.bindTexture(ClothingTextures.WINGS[plr.getAccessory("wings_var")]);
    	
        float rPointX = model.bipedBody.rotationPointX;
        float rPointY = model.bipedBody.rotationPointY;
        float rPointZ = model.bipedBody.rotationPointZ;
        float rAngleX = (float) Math.toDegrees(model.bipedBody.rotateAngleX);
        float rAngleY = (float) Math.toDegrees(model.bipedBody.rotateAngleY);
        float rAngleZ = (float) Math.toDegrees(model.bipedBody.rotateAngleZ);

    	if(!shouldRender(plr) && plr.getAccessory("wings") != 1) return;
    	 
        pushMatrix(getEntityModel().bipedBody, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);

			
			//GlStateManager.translatef(0, 0, 0.0625f*4f); //shift down to correct position
	        
        	wingRotation = 0;
			if(ent.shouldRenderSneaking()) {
				wingRotation = -30; 
		    	//GL11.glRotatef((float) Math.toDegrees(0.5f), 1.0F, 0.0F, 0.0F);
		        //GL11.glTranslatef(0, 0.15f, -0.1f);
		    }
			GL11.glPushMatrix();
		    	GL11.glRotatef(wingRotation, 0, 0, 1);
		    	GL11.glRotatef(10, 0, 1, 0);
				LWing1.render(f7);
				LWing2.render(f7);
				LWing3.render(f7);
				LWing4.render(f7);
				LWing5.render(f7);
				LWing6.render(f7);
				LWing7.render(f7);
				LWing8.render(f7);
				LWing9.render(f7);
				LWing10.render(f7);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
				GL11.glScalef(-1f, 1f, 1f);
		    	GL11.glRotatef(wingRotation, 0, 0, 1);
		    	GL11.glRotatef(10, 0, 1, 0);
				RWing1.render(f7);
				RWing2.render(f7);
				RWing3.render(f7);
				RWing4.render(f7);
				RWing5.render(f7);
				RWing6.render(f7);
				RWing7.render(f7);
				RWing8.render(f7);
				RWing9.render(f7);
				RWing10.render(f7);
			GL11.glPopMatrix();

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		GlStateManager.popMatrix();

	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
