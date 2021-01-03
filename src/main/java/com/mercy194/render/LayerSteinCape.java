package com.mercy194.render;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.ClothingTextures;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LayerSteinCape extends CapeLayer {

	//ResourceLocation CAPE = new ResourceLocation(AdvSkinMod.MODID, "textures/owner_cape.png");
	public LayerSteinCape(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> player) {
		super(player);
		
	}

   public void render(AbstractClientPlayerEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
       	String playerName = PlayerEntity.getUUID(entityIn.getGameProfile()).toString();
       	ClothingPlayer cPlr = AdvSkinMod.getPlayerByName(playerName);

       	if(cPlr != null) {
       		if(cPlr.usingMod) {
		       	if(entityIn != null) {
		       		if(entityIn.hasPlayerInfo()) {
			        	NetworkPlayerInfo networkPlayerInfo = ObfuscationReflectionHelper.getPrivateValue(AbstractClientPlayerEntity.class, entityIn, AdvSkinHelper.Obfuscation.NETWORK_PLAYER_INFO);
				       	Map<MinecraftProfileTexture.Type, ResourceLocation> texture = ObfuscationReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, networkPlayerInfo, AdvSkinHelper.Obfuscation.PLAYER_TEXTURES); // field_187107_a -> playerTextures
				       	if(cPlr.getAccessory("showCape")==1 && !cPlr.capeURL.equalsIgnoreCase("NO_CAPE") && !cPlr.capeURL.equalsIgnoreCase("")) {
			       			if(cPlr.preCapeURL != cPlr.capeURL) {
			       				cPlr.RES_CAPE = AdvSkinHelper.loadCape(cPlr.username, cPlr.capeURL); //load cape once
			       				cPlr.preCapeURL = cPlr.capeURL;
			       			}
				       		ResourceLocation cl = AdvSkinHelper.useCape(cPlr.username, cPlr.capeURL);
				   			if(cl != null) texture.put(MinecraftProfileTexture.Type.CAPE, cl);
				       	} else {
				   			texture.put(MinecraftProfileTexture.Type.CAPE, ClothingTextures.loadCape(entityIn));
				       	}
		       		}
		       	}
       		}
       	}
   }

	@Override
	public boolean shouldCombineTextures() {
		// TODO Auto-generated method stub
		return true;
	}

}
