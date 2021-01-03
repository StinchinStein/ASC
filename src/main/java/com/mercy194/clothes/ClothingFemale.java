package com.mercy194.clothes;

import org.lwjgl.opengl.GL11;

import com.mercy194.gfx.SteinModelBox;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.render.SteinPlayerRenderer;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.fonts.providers.GlyphProviderTypes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ClothingFemale extends AdvClothing {
	//boobs.
	private SteinModelBox chest, chestwear, sBox;
	public boolean isFirstPerson = false;
	
	public ClothingFemale() {
		super("");

		chest = new SteinModelBox(64, 64, 17, 18, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		chestwear = new SteinModelBox(64, 64, 17, 34, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		sBox = new SteinModelBox(64, 32, 17, 19, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		
    }
	
	public void initialize() {

		super.initialize();
	}
	public void render(AbstractClientPlayerEntity ent, float x, float y, float z, float f4, float f5, float f6, float f7) {
		boolean isFirstPerson = (this.getRenderer() instanceof SteinPlayerRenderer);
		
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) model = this.getEntityModel();
    	if(plr != null) {
        	if(!shouldRender(plr)) return;
			if(plr.gender == false && CFG.generic.getBool("showLayer")) {
				BufferBuilder renderer = Tessellator.getInstance().getBuffer();
				
		        GlStateManager.color3f(1f, 1f, 1f);

				boolean isChestplateOccupied = 
					!ent.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.ELYTRA, 1), true) && 
					!(ent.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.AIR, 1), true));
				
				boolean teamSeeFriendly = false;
				if(ent.getTeam() != null) teamSeeFriendly = ent.getTeam().getSeeFriendlyInvisiblesEnabled();
				

				pushMatrix(model.bipedBody, f7);
				GlStateManager.translatef(0, 0.05625f, -0.0625f*2f); //shift down to correct position
				this.getRenderer().bindTexture(rend.getEntityTexture(ent));

				if(isChestplateOccupied && !CFG.generic.getBool("showArmor")) {
					if(isChestplateOccupied) GlStateManager.translatef(0, 0, 0.01f);
					GlStateManager.rotatef(-12, 1, 0, 0);
				} else if(plr.partSize == 1) {
					GlStateManager.rotatef(-20, 1, 0, 0); //size = 10=small,20=big
				} else if(plr.partSize == 2) {
					GlStateManager.rotatef(-25.3f, 1, 0, 0); //size = 10=small,20=big
				} else {
					if(isChestplateOccupied) GlStateManager.translatef(0, 0, 0.01f);
					GlStateManager.rotatef(-12, 1, 0, 0);
				}
				
	            if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
	            
	            
	            if((teamSeeFriendly && ent.isInvisible()) || !ent.isInvisible()) {
	            	
	            	chest.render(renderer, f7-0.00001f);
		        	
		        	
					if(ent.isWearing(PlayerModelPart.JACKET) && (!isChestplateOccupied || CFG.generic.getBool("showArmor"))) {
						GlStateManager.translatef(0, 0, -0.015f * 1f);
						GlStateManager.scalef(1.05f, 1.05f, 1.05f);
			        	chestwear.render(renderer, f7);
					}
					
	            }
			
	            if(CFG.generic.getBool("showArmor")) {
	            	GlStateManager.pushTextureAttributes();
		            if(!ent.inventory.armorItemInSlot(2).equals(new ItemStack(Items.AIR, 1), true)) {
		            	unsetBrightness();
		            	ItemStack itemstack = ent.inventory.armorItemInSlot(2);
			            ResourceLocation ARMOR_TXTR = getArmorResource(ent, ent.inventory.armorItemInSlot(2),  EquipmentSlotType.CHEST, null);
			            if(ARMOR_TXTR != null) {
				            if (itemstack.getItem() instanceof ArmorItem) {
				                ArmorItem armoritem = (ArmorItem)itemstack.getItem();
					            if (armoritem instanceof net.minecraft.item.IDyeableArmorItem) { // Allow this for anything, not only cloth
					               int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
					               float f = (float)(i >> 16 & 255) / 255.0F;
					               float f1 = (float)(i >> 8 & 255) / 255.0F;
					               float f2 = (float)(i & 255) / 255.0F;
					               GlStateManager.color4f(1f * f, 1f * f1, 1f * f2, 1f);
					            }
					            
								GlStateManager.translatef(0, 0, -0.015f * 1f);
					            this.getRenderer().bindTexture(ARMOR_TXTR);
					            sBox.render(renderer, f7+0.00002f);
					            
					        	if(ent.inventory.armorItemInSlot(2).isEnchanted()) {
						            ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");			            
						            float f = (float)ent.ticksExisted;
						            this.getRenderer().bindTexture(ENCHANTED_ITEM_GLINT_RES);
						            GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
						            gamerenderer.setupFogColor(true);
						            GlStateManager.enableBlend();
						            GlStateManager.depthFunc(514);
						            GlStateManager.depthMask(false);
						            GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
	
						            for(int i = 0; i < 2; ++i) {
						               GlStateManager.disableLighting();
						               GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
						               GlStateManager.color4f(0.38F, 0.19F, 0.608F, 1.0F);
						               GlStateManager.matrixMode(5890);
						               GlStateManager.loadIdentity();
						               GlStateManager.scalef(0.33333334F, 0.33333334F, 0.33333334F);
						               GlStateManager.rotatef(30.0F - (float)i * 60.0F, 0.0F, 0.0F, 1.0F);
						               GlStateManager.translatef(0.0F, f * (0.001F + (float)i * 0.003F) * 20.0F, 0.0F);
						               GlStateManager.matrixMode(5888);
							           sBox.render(renderer, f7+0.00002f);
						               GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						            }
	
						            GlStateManager.matrixMode(5890);
						            GlStateManager.loadIdentity();
						            GlStateManager.matrixMode(5888);
						            GlStateManager.enableLighting();
						            GlStateManager.depthMask(true);
						            GlStateManager.depthFunc(515);
						            GlStateManager.disableBlend();
						            gamerenderer.setupFogColor(false);
					        	}
				            }
			            }
		            }
	            	GlStateManager.popAttributes();
	            }
				if(ent.isInvisible()) {
					GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
				}
	        	GlStateManager.popMatrix();
			}
        }
        GlStateManager.color3f(1f, 1f, 1f);

	}
}
