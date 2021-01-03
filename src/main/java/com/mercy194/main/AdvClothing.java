package com.mercy194.main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mercy194.gfx.SteinModelBox;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AdvClothing {
	
	
	public ArrayList<SteinModelBox> models = new ArrayList<SteinModelBox>();
	private PlayerRenderer renderer;
	private int[] skinPack;
	private boolean INITIALIZED = false;
	
	private ArrayList<ClothingVariation> VARIATIONS = new ArrayList<ClothingVariation>();
	
	
	@SuppressWarnings("unchecked")
	public AdvClothing(String filename) {
		this.skinPack = new int[] { 0 };
		if(!filename.contentEquals("")) {
			try {
				ResourceLocation l = new ResourceLocation(AdvSkinMod.MODID, "clothes/" + filename + "/info.json");
				InputStream stream = Minecraft.getInstance().getResourceManager().getResource(l).getInputStream();
				InputStreamReader r = new InputStreamReader(stream);
				JSONObject jsonData = (JSONObject) new JSONParser().parse(r);
		
				JSONArray ELEMENTS = (JSONArray) jsonData.get("variations");
				ELEMENTS.forEach(e -> {
					JSONObject obj = (JSONObject) e;
					VARIATIONS.add(new ClothingVariation(obj.get("name").toString(), new ResourceLocation(AdvSkinMod.MODID, obj.get("texture").toString())));
				});
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			VARIATIONS.add(new ClothingVariation("missingno", null));
		}
	}
	public AdvClothing(int[] skinPack) {
		this.skinPack = skinPack;
	}

	public void initialize() {
		//AdvSkinMod.log("Initialized Clothing Item");
		this.INITIALIZED = true;
	}
	
	public void render(AbstractClientPlayerEntity ent, float x, float y, float z, float f4, float f5, float f6, float f7) {}
	
	public void addModel(SteinModelBox box) {
		this.models.add(box);
	}


	public ArrayList<ClothingVariation> getVariations() {
		return this.VARIATIONS;
	}
	

	public PlayerModel<AbstractClientPlayerEntity> getEntityModel() {
		return this.getRenderer().getEntityModel();
	}

	public void setRenderer(PlayerRenderer renderer) {
		this.renderer = renderer;
	}

	public PlayerRenderer getRenderer() {
		return renderer;
	}
	
	

	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @javax.annotation.Nullable String type) {
	   if(stack.getItem() instanceof ArmorItem) {
		ArmorItem item = (ArmorItem)stack.getItem();
      	String texture = item.getArmorMaterial().getName();
      	String domain = "minecraft";
      	int idx = texture.indexOf(':');
      	if (idx != -1) {
    	  domain = texture.substring(0, idx);
         	texture = texture.substring(idx + 1);
      	}
      	String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, 1, type == null ? "" : String.format("_%s", type));

      	s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
      	ResourceLocation resourcelocation = null;

      	if (resourcelocation == null) {
    	  resourcelocation = new ResourceLocation(s1);
      	}
      	return resourcelocation;
	   }
	return null;
   	}
	

	public boolean shouldRender(ClothingPlayer plr) {
		for(int i = 0; i < this.skinPack.length; i++) {
			if(this.skinPack[i] == plr.skinPack) return true;
		}
		
		return false;
	}
	
   protected void unsetBrightness() {
      GlStateManager.activeTexture(GLX.GL_TEXTURE0);
      GlStateManager.enableTexture();
      GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, GLX.GL_TEXTURE0);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PRIMARY_COLOR);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 8448);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, GLX.GL_TEXTURE0);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_ALPHA, GLX.GL_PRIMARY_COLOR);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND1_ALPHA, 770);
      GlStateManager.activeTexture(GLX.GL_TEXTURE1);
      GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, 5890);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PREVIOUS);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 8448);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, 5890);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.activeTexture(GLX.GL_TEXTURE2);
      GlStateManager.disableTexture();
      GlStateManager.bindTexture(0);
      GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, 5890);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PREVIOUS);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 8448);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, 5890);
      GlStateManager.activeTexture(GLX.GL_TEXTURE0);
   }

   public void pushMatrix(RendererModel mdl, float f7) {

       float rPointX = mdl.rotationPointX;
       float rPointY = mdl.rotationPointY;
       float rPointZ = mdl.rotationPointZ;
       float rAngleX = (float) Math.toDegrees(mdl.rotateAngleX);
       float rAngleY = (float) Math.toDegrees(mdl.rotateAngleY);
       float rAngleZ = (float) Math.toDegrees(mdl.rotateAngleZ);
       
       GlStateManager.pushMatrix();

       GlStateManager.translatef(rPointX * f7, rPointY * f7, rPointZ * f7);
       if (rAngleZ != 0.0F) {
          GlStateManager.rotatef(rAngleZ, 0.0F, 0.0F, 1.0F);
       }

       if (rAngleY != 0.0F) {
          GlStateManager.rotatef(rAngleY, 0.0F, 1.0F, 0.0F);
       }

       if (rAngleX != 0.0F) {
          GlStateManager.rotatef(rAngleX, 1.0F, 0.0F, 0.0F);
       }
   }
   
	public boolean isInitialized() {
		return INITIALIZED;
	}


	
}
