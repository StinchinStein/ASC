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
import net.minecraft.util.math.MathHelper;

public class ClothingTFEngine extends AdvClothing {
    RendererModel Front1;
    RendererModel Front2;
    RendererModel Front3;
    RendererModel Back1;
    RendererModel Back2;

    RendererModel Shape1;
    RendererModel Shape2;
    RendererModel Shape3;
    RendererModel Shape4;
    RendererModel Shape5;
    RendererModel Shape6;
    RendererModel Shape7;
    RendererModel Shape8;
    RendererModel Shape9;
    RendererModel Shape10;
    RendererModel Shape11;
    RendererModel Shape12;
    RendererModel Shape13;
	
	public ClothingTFEngine() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {

		Front1 = new RendererModel(this.getEntityModel(), 3, 17).setTextureSize(64, 32);
	    Front1.addBox(-1F, 0F, -4F, 2, 7, 1);
	    Front1.setRotationPoint(0F, 1F, 0F);
	    Front1.setTextureSize(64, 32);
	    Front1.mirror = true;
	    Front2 = new RendererModel(this.getEntityModel(), 3, 26).setTextureSize(64, 32);
	    Front2.addBox(-1F, 6F, -3F, 2, 4, 1);
	    Front2.setRotationPoint(0F, 1F, 0F);
	    Front2.setTextureSize(64, 32);
	    Front2.mirror = true;
	    Front3 = new RendererModel(this.getEntityModel(), 1, 9).setTextureSize(64, 32);
	    Front3.addBox(-2F, 0F, -2F, 4, 6, 1);
	    Front3.setRotationPoint(0F, 1F, -1F);
	    Front3.setTextureSize(64, 32);
	    Front3.mirror = true;
	    Back1 = new RendererModel(this.getEntityModel(), 12, 17).setTextureSize(64, 32);
	    Back1.addBox(-2F, 0F, 2F, 4, 6, 2);
	    Back1.setRotationPoint(0F, 1F, 0F);
	    Back1.setTextureSize(64, 32);
	    Back1.mirror = true;
	    Back2 = new RendererModel(this.getEntityModel(), 15, 26).setTextureSize(64, 32);
	    Back2.addBox(-1F, 6F, 2F, 2, 4, 1);
	    Back2.setRotationPoint(0F, 1F, 0F);
	    Back2.setTextureSize(64, 32);
	    Back2.mirror = true;
	      
	    
	    Shape1 = new RendererModel(this.getEntityModel(), 12, 15).setTextureSize(64, 32);
	      Shape1.addBox(0F, 0F, 0F, 3, 7, 2);
	      Shape1.setRotationPoint(-7F, -1F, 2F);
	      Shape1.mirror = true;
	      Shape2 = new RendererModel(this.getEntityModel(), 44, 15).setTextureSize(64, 32);
	      Shape2.addBox(0F, 0F, 0F, 3, 7, 2);
	      Shape2.setRotationPoint(4F, -1F, 2F);
	      Shape2.mirror = true;
	      Shape3 = new RendererModel(this.getEntityModel(), 45, 2).setTextureSize(64, 32);
	      Shape3.addBox(0F, 0F, 0F, 3, 1, 1);
	      Shape3.setRotationPoint(4F, -10F, 1F);
	      Shape3.mirror = true;
	      Shape4 = new RendererModel(this.getEntityModel(), 13, 4).setTextureSize(64, 32);
	      Shape4.addBox(0F, 0F, 0F, 3, 10, 1);
	      Shape4.setRotationPoint(-7F, -10F, 2F);
	      Shape4.mirror = true;
	      Shape5 = new RendererModel(this.getEntityModel(), 21, 4).setTextureSize(64, 32);
	      Shape5.addBox(0F, 0F, 0F, 1, 10, 1);
	      Shape5.setRotationPoint(-5F, -10F, 1F);
	      Shape5.mirror = true;
	      Shape6 = new RendererModel(this.getEntityModel(), 13, 2).setTextureSize(64, 32);
	      Shape6.addBox(0F, 0F, 0F, 3, 1, 1);
	      Shape6.setRotationPoint(-7F, -10F, 1F);
	      Shape6.mirror = true;
	      Shape7 = new RendererModel(this.getEntityModel(), 14, 24).setTextureSize(64, 32);
	      Shape7.addBox(0F, 0F, 0F, 2, 2, 1);
	      Shape7.setRotationPoint(-6F, 6F, 2F);
	      Shape7.mirror = true;
	      Shape8 = new RendererModel(this.getEntityModel(), 45, 4).setTextureSize(64, 32);
	      Shape8.addBox(0F, 0F, 0F, 3, 10, 1);
	      Shape8.setRotationPoint(4F, -10F, 2F);
	      Shape8.mirror = true;
	      Shape9 = new RendererModel(this.getEntityModel(), 46, 24).setTextureSize(64, 32);
	      Shape9.addBox(0F, 0F, 0F, 2, 2, 1);
	      Shape9.setRotationPoint(4F, 6F, 2F);
	      Shape9.mirror = true;
	      Shape10 = new RendererModel(this.getEntityModel(), 22, 18).setTextureSize(64, 32);
	      Shape10.addBox(0F, 0F, 0F, 8, 2, 3);
	      Shape10.setRotationPoint(-4F, 2F, 0F);
	      Shape10.mirror = true;
	      Shape11 = new RendererModel(this.getEntityModel(), 41, 4).setTextureSize(64, 32);
	      Shape11.addBox(0F, 0F, 0F, 1, 10, 1);
	      Shape11.setRotationPoint(4F, -10F, 1F);
	      Shape11.mirror = true;
	      Shape12 = new RendererModel(this.getEntityModel(), 53, 4).setTextureSize(64, 32);
	      Shape12.addBox(0F, 0F, 0F, 1, 10, 1);
	      Shape12.setRotationPoint(6F, -10F, 1F);
	      Shape12.mirror = true;
	      Shape13 = new RendererModel(this.getEntityModel(), 9, 4).setTextureSize(64, 32);
	      Shape13.addBox(0F, 0F, 0F, 1, 10, 1);
	      Shape13.setRotationPoint(-7F, -10F, 1F);
	      Shape13.mirror = true;
	      
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

    	ResourceLocation TXTR = ClothingTextures.STARSCREAM_WINGS;
    	if(plr.skinPack == 1) TXTR = ClothingTextures.STARSCREAM_WINGS;
    	if(plr.skinPack == 2) TXTR = ClothingTextures.SKYWARP_WINGS;
    	if(plr.skinPack == 3) TXTR = ClothingTextures.THUNDER_WINGS;
    	rend.bindTexture(TXTR);
    	
        float rPointX = model.bipedBody.rotationPointX;
        float rPointY = model.bipedBody.rotationPointY;
        float rPointZ = model.bipedBody.rotationPointZ;
        float rAngleX = (float) Math.toDegrees(model.bipedBody.rotateAngleX);
        float rAngleY = (float) Math.toDegrees(model.bipedBody.rotateAngleY);
        float rAngleZ = (float) Math.toDegrees(model.bipedBody.rotateAngleZ);

    	if(!shouldRender(plr) && plr.getAccessory("torso") != 1) return;
    	
    	rend.bindTexture(ClothingTextures.WINGS[plr.getAccessory("torso_var")]);
        pushMatrix(getEntityModel().bipedBody, f7);
        	if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);

		    Front1.render(f7);
		    Front2.render(f7);
		    Front3.render(f7);
		    Back1.render(f7);
		    Back2.render(f7);
	
	    	rend.bindTexture(ClothingTextures.ENGINES[plr.getAccessory("torso_var")]);
	
		    Shape3.render(f7);
		    Shape4.render(f7);
		    Shape5.render(f7);
		    Shape6.render(f7);
		    Shape8.render(f7);
		    Shape10.render(f7);
		    Shape11.render(f7);
		    Shape12.render(f7);
		    Shape13.render(f7);
		    

			if(ent.isInvisible()) {
				GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		GlStateManager.popMatrix();
	    GlStateManager.color3f(1f, 1f, 1f);

	}
}
