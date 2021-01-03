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

public class ClothingTFBoosters extends AdvClothing {
	
    RendererModel shape_0;
    RendererModel Shape2;
    RendererModel Shapew;
    RendererModel Shape3;
    RendererModel Shape1;
    RendererModel Shape4;
    RendererModel Shape5;
    RendererModel Shape6;
    RendererModel Shape7;
    RendererModel Shape8;
    RendererModel Shape9;
    RendererModel Shape10;
    RendererModel Shape11;
    RendererModel Shape12;
	
	public ClothingTFBoosters() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {
		
	      shape_0 = new RendererModel(this.getEntityModel(), 1, 9).setTextureSize(64, 32);
	      shape_0.addBox(-1F, 1F, 2F, 2, 5, 1);
	      shape_0.setRotationPoint(2F, 13F, 0F);
	      shape_0.mirror = true;
	      Shape2 = new RendererModel(this.getEntityModel(), 18, 16).setTextureSize(64, 32);
	      Shape2.addBox(-1F, 7F, 2F, 2, 5, 2);
	      Shape2.setRotationPoint(-2F, 12F, 0F);
	      Shape2.mirror = true;
	      Shapew = new RendererModel(this.getEntityModel(), 1, 16).setTextureSize(64, 32);
	      Shapew.addBox(-1F, 7F, 2F, 2, 5, 2);
	      Shapew.setRotationPoint(2F, 12F, 0F);
	      Shapew.mirror = true;
	      Shape3 = new RendererModel(this.getEntityModel(), 1, 27).setTextureSize(64, 32);
	      Shape3.addBox(-2F, 10F, -4F, 4, 2, 2);
	      Shape3.setRotationPoint(2F, 12F, 0F);
	      Shape3.mirror = true;
	      Shape1 = new RendererModel(this.getEntityModel(), 20, 9).setTextureSize(64, 32);
	      Shape1.addBox(-1F, 1F, 2F, 2, 5, 1);
	      Shape1.setRotationPoint(-2F, 13F, 0F);
	      Shape1.mirror = true;
	      Shape4 = new RendererModel(this.getEntityModel(), 14, 27).setTextureSize(64, 32);
	      Shape4.addBox(-2F, 10F, -4F, 4, 2, 2);
	      Shape4.setRotationPoint(-2F, 12F, 0F);
	      Shape4.mirror = true;
	      Shape5 = new RendererModel(this.getEntityModel(), 16, 2).setTextureSize(64, 32);
	      Shape5.addBox(1F, 3F, -3F, 1, 1, 1);
	      Shape5.setRotationPoint(-2F, 12F, 0F);
	      Shape5.mirror = true;
	      Shape6 = new RendererModel(this.getEntityModel(), 1, 24).setTextureSize(64, 32);
	      Shape6.addBox(-1F, 9F, -3F, 2, 1, 1);
	      Shape6.setRotationPoint(2F, 12F, 0F);
	      Shape6.mirror = true;
	      Shape7 = new RendererModel(this.getEntityModel(), 16, 0).setTextureSize(64, 32);
	      Shape7.addBox(-2F, 2F, -3F, 4, 1, 1);
	      Shape7.setRotationPoint(-2F, 12F, 0F);
	      Shape7.setTextureSize(64, 32);
	      Shape7.mirror = true;
	      Shape8 = new RendererModel(this.getEntityModel(), 22, 2).setTextureSize(64, 32);
	      Shape8.addBox(-2F, 3F, -3F, 1, 1, 1);
	      Shape8.setRotationPoint(-2F, 12F, 0F);
	      Shape8.mirror = true;
	      Shape9 = new RendererModel(this.getEntityModel(), 20, 24).setTextureSize(64, 32);
	      Shape9.addBox(-1F, 9F, -3F, 2, 1, 1);
	      Shape9.setRotationPoint(-2F, 12F, 0F);
	      Shape9.mirror = true;
	      Shape10 = new RendererModel(this.getEntityModel(), 1, 0).setTextureSize(64, 32);
	      Shape10.addBox(-2F, 2F, -3F, 4, 1, 1);
	      Shape10.setRotationPoint(2F, 12F, 0F);
	      Shape10.mirror = true;
	      Shape11 = new RendererModel(this.getEntityModel(), 1, 2).setTextureSize(64, 32);
	      Shape11.addBox(1F, 3F, -3F, 1, 1, 1);
	      Shape11.setRotationPoint(2F, 12F, 0F);
	      Shape11.mirror = true;
	      Shape12 = new RendererModel(this.getEntityModel(), 7, 2).setTextureSize(64, 32);
	      Shape12.addBox(-2F, 3F, -3F, 1, 1, 1);
	      Shape12.setRotationPoint(2F, 12F, 0F);
	      Shape12.mirror = true;
	      
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
    	
        float rPointX = model.bipedBody.rotationPointX;
        float rPointY = model.bipedBody.rotationPointY;
        float rPointZ = model.bipedBody.rotationPointZ;
        float rAngleX = (float) Math.toDegrees(model.bipedBody.rotateAngleX);
        float rAngleY = (float) Math.toDegrees(model.bipedBody.rotateAngleY);
        float rAngleZ = (float) Math.toDegrees(model.bipedBody.rotateAngleZ);

    	rend.bindTexture(ClothingTextures.BOOSTERS[plr.getAccessory("legs_var")]);
    	if(!shouldRender(plr) && plr.getAccessory("legs") != 1) return;
    	
        pushMatrix(getEntityModel().bipedLeftLeg, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);

	        GL11.glTranslatef(f7*2, -f7*12, 0);
	        Shape1.render(f7);
	        Shape2.render(f7);
	        Shape4.render(f7);
	        Shape5.render(f7);
	        Shape7.render(f7);
	        Shape8.render(f7);
	        Shape9.render(f7);
			GlStateManager.popMatrix();
	        pushMatrix(getEntityModel().bipedRightLeg, f7);
	        GL11.glTranslatef(-f7*2, -f7*12, 0);
	        Shape10.render(f7);
	        Shape11.render(f7);
	        Shape12.render(f7);
	        Shapew.render(f7);
	        shape_0.render(f7);
	        Shape3.render(f7);
	        Shape6.render(f7);
	        

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		GlStateManager.popMatrix();
	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
