package com.mercy194.main.proxy;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lwjgl.glfw.GLFW;

import com.mercy194.clothes.ClothingBackpack;
import com.mercy194.clothes.ClothingFaceMask;
import com.mercy194.clothes.ClothingFemale;
import com.mercy194.clothes.ClothingGasMask;
import com.mercy194.clothes.ClothingHat;
import com.mercy194.clothes.transformer.ClothingTFBoosters;
import com.mercy194.clothes.transformer.ClothingTFEngine;
import com.mercy194.clothes.transformer.ClothingTFWings;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.AdvSkinSounds;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.SteinEventHandler;
import com.mercy194.render.CustomVillagerRenderer;
import com.mercy194.render.LayerClothing;
import com.mercy194.render.LayerSteinCape;
import com.mercy194.render.SteinPlayerRenderer;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class AdvSkinClient extends AdvSkinServer {

	public static final KeyBinding toggleFirstPerson = new KeyBinding(AdvSkinMod.MODID + ".key.firstperson", GLFW.GLFW_KEY_V, "key.categories." + AdvSkinMod.MODID);
	public static final KeyBinding toggleEditGUI = new KeyBinding(AdvSkinMod.MODID + ".key.gui", GLFW.GLFW_KEY_G, "key.categories." + AdvSkinMod.MODID);
	public static final KeyBinding releaseCamera = new KeyBinding(AdvSkinMod.MODID + ".key.cam", GLFW.GLFW_KEY_H, "key.categories." + AdvSkinMod.MODID);
	//public static final KeyBinding reloadModel = new KeyBinding("Reload Model", GLFW.GLFW_KEY_H, "key.categories." + AdvSkinMod.MODID);
    public static SteinPlayerRenderer fpsRenderer;
    	
	public void register() {
		
		new AdvSkinSounds().init();
    	ClientRegistry.registerKeyBinding(toggleEditGUI);
    	ClientRegistry.registerKeyBinding(toggleFirstPerson);
    	//ClientRegistry.registerKeyBinding(releaseCamera);
    	
    	//ClientRegistry.registerKeyBinding(toggleFirstPersonV2);
    	AdvSkinMod.syncServerOnline = AdvSkinMod.getServerStatus();
    	
    	//update version on database
    	try {
	    	String uuid = PlayerEntity.getUUID(Minecraft.getInstance().getSession().getProfile()).toString();
	    	ClothingPlayer PLAYER = AdvSkinMod.loadSQLForPlayerSync(uuid);
	    	if(PLAYER != null) {
	    		AdvSkinMod.updateStatus(PLAYER);
	    	}
    	} catch(Exception e) {}
    	AdvSkinMod.checkForUpdates();
    	

        //FML.register(new SteinEventHandler());
        MinecraftForge.EVENT_BUS.register(new SteinEventHandler());
        
        new CFG();
        
        AdvSkinMod.addClothingToMod(new ClothingFemale());
        AdvSkinMod.addClothingToMod(new ClothingBackpack());
        AdvSkinMod.addClothingToMod(new ClothingGasMask());
        AdvSkinMod.addClothingToMod(new ClothingFaceMask());
        AdvSkinMod.addClothingToMod(new ClothingHat());
        //AdvSkinMod.addClothingToMod(new ClothingProtonPack());
        
        //Transformers Models
        AdvSkinMod.addClothingToMod(new ClothingTFWings());
        AdvSkinMod.addClothingToMod(new ClothingTFEngine());
        AdvSkinMod.addClothingToMod(new ClothingTFBoosters());
        //AdvSkinMod.addClothingToMod(new ClothingOverlay());
    	try {
			ResourceLocation l = new ResourceLocation(AdvSkinMod.MODID, "clothes/skins_override.json");
			InputStream stream = Minecraft.getInstance().getResourceManager().getResource(l).getInputStream();
			InputStreamReader r = new InputStreamReader(stream);
			AdvSkinMod.SKIN_OVERRIDES = (JSONObject) new JSONParser().parse(r);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	

        for(PlayerRenderer r : Minecraft.getInstance().getRenderManager().getSkinMap().values()) {
        	LayerClothing clothingLayer = new LayerClothing(r);
            LayerSteinCape capeLayer = new LayerSteinCape(r);
        	r.addLayer(clothingLayer);
        	r.addLayer(capeLayer);
        }
        
        //This is experimental
        //EntityRendererManager rm = Minecraft.getInstance().getRenderManager();
        //rm.renderers.put(VillagerEntity.class, new CustomVillagerRenderer(rm, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager()));
	}
	


	public static void onChangeCustomization() {
		AdvSkinMod.enableButtons = false;
		AdvSkinMod.enableButtonsTimer = 0;
	}
	
    private static float rotationYaw = 0f;

	public static void drawPaperDoll(int posX, int posY, int scale, LivingEntity ent, float partialTicks) {
		  GlStateManager.enableColorMaterial();
		  GlStateManager.color3f(1f, 1f, 1f);
	      GlStateManager.pushMatrix();
	      GlStateManager.translatef((float)posX, (float)posY, 50.0F);
	      if(ent.shouldRenderSneaking()) {
		      GlStateManager.translatef(0, -5, 0);
	      }
	      if(ent.getRidingEntity() != null) { 
		      GlStateManager.translatef(-2, 8, 0);
	      }
		  GlStateManager.translatef(0, -20f*ent.getSwimAnimation(partialTicks), 0);
	      
	      GlStateManager.scalef((float)(-scale), (float)scale, (float)scale);
	      GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
	      float f = ent.renderYawOffset;
	      float f1 = ent.rotationYaw;
	      float f2 = ent.rotationPitch;
	      float f3 = ent.prevRotationYawHead;
	      float f4 = ent.rotationYawHead;
	      float f5 = ent.prevRenderYawOffset;
	      GlStateManager.rotatef(165.0F, 0.0F, 1.0F, 0.0F);
	      RenderHelper.enableStandardItemLighting();
	      GlStateManager.rotatef(-125.0F, 0.0F, 1.0F, 0.0F);
	      ent.renderYawOffset = 0;
	      ent.prevRenderYawOffset=0;
	      float headYaw = Minecraft.getInstance().player.getYaw(partialTicks);

	      ent.rotationYaw = 0;
	      //ent.prevRotationYaw = 0;
	      ent.prevRotationYawHead = Math.max(Math.min(headYaw-rotationYaw, 40), -40);
	      rotationYaw += (headYaw-rotationYaw) / 18f;
	      ent.rotationYawHead = Math.max(Math.min(headYaw-rotationYaw, 40), -40);
	      //ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
	      float rotPitch = ent.getPitch(partialTicks);
	      if(ent.isElytraFlying()) {
	    	  GlStateManager.translatef(-1F, 0.95f + (rotPitch / 90f), 0.0f + Math.abs(rotPitch / 90f));
	      }
	      EntityRendererManager rMgr = Minecraft.getInstance().getRenderManager();
	      rMgr.setPlayerViewY(180.0F);
	      rMgr.setRenderShadow(false);
	      rMgr.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
	      rMgr.setRenderShadow(true);
	      ent.renderYawOffset = f;
	      ent.rotationYaw = f1;
	      ent.rotationPitch = f2;
	      ent.prevRenderYawOffset = f5;
	      ent.prevRotationYawHead = f3;
	      ent.rotationYawHead = f4;
	      GlStateManager.popMatrix();
	      RenderHelper.disableStandardItemLighting();
	      GlStateManager.disableRescaleNormal();
	      GlStateManager.activeTexture(GLX.GL_TEXTURE1);
	      GlStateManager.disableTexture();
	      GlStateManager.activeTexture(GLX.GL_TEXTURE0);
	   }

	
	//This is still used for the main menu.

    public static void drawTextLabel(String txt, int x, int y) {
        GlStateManager.disableBlend();
    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x, y, x + Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x+2, y+2, 0xFFFFFF);
    }
    public static void drawRightTextLabel(String txt, int x, int y) {
        GlStateManager.disableBlend();
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3;
    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w, y, x, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x-w+2, y+2, 0xFFFFFF);
    }
    public static void drawCenterTextLabel(String txt, int x, int y) {
        GlStateManager.disableBlend();
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3;
    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w / 2, y, x + w / 2+1, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x- w / 2+2, y+2, 0xFFFFFF);
    }
    
}
