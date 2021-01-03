package com.mercy194.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class SteinPlayerRenderer extends PlayerRenderer {

	@SuppressWarnings("rawtypes")
	public SteinPlayerRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
		super(renderManager, useSmallArms);
		this.layerRenderers.remove(0); //armor layer
		this.layerRenderers.remove(0); //held item layer
		this.layerRenderers.remove(0); //arrow layer
		this.layerRenderers.remove(2); //head layer
		this.addLayer(new SteinArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
		this.addLayer(new LayerClothing(this));
		this.addLayer(new SteinHeldItemLayer<>(this));
		
	}
	
}
