package com.mercy194.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mercy194.main.gui.screen.AdvSkinScreen;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;
import com.mercy194.main.gui.screen.MercySkinPackScreen;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mercy194.render.SteinPlayerRenderer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseClickedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseDragEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class SteinEventHandler {

	List<PlayerEntity> prePlayers = new ArrayList<PlayerEntity>();
	
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent evt) {
        if(AdvSkinClient.toggleEditGUI.isPressed()) {
        	try {
            	Minecraft m = Minecraft.getInstance();
            	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
                ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
                if(plr.skinPack != 0) {
    	            Minecraft.getInstance().displayGuiScreen(new MercySkinPackScreen());
                } else {
    	            Minecraft.getInstance().displayGuiScreen(new MercyCustomizationScreen());
                }
        	} catch(Exception e) {
	            Minecraft.getInstance().displayGuiScreen(new MercyCustomizationScreen());
			}
        }
        if(AdvSkinClient.toggleFirstPerson.isPressed()) {
        	CFG.generic.setParameter("fp_enabled", !CFG.generic.getBool("fp_enabled"));
        	CFG.generic.save();
        }
        /*
        if(AdvSkinClient.releaseCamera.isPressed()) {
        	CompoundNBT nbt = Minecraft.getInstance().player.getPersistentData();
        	nbt.remove(AdvSkinMod.MODID + "_login");
    		
        }*/
    }

    private boolean justShownUpdate = false;
    private int showUpdateTimer = 0;
    
	@SubscribeEvent
	public void clientTickEvent(TickEvent.ClientTickEvent evt) {
		if(justShownUpdate && showUpdateTimer < 400) {
			showUpdateTimer++;
		}
		if(!AdvSkinMod.enableButtons) AdvSkinMod.enableButtonsTimer++;
		if(AdvSkinMod.enableButtonsTimer > 30) {
			AdvSkinMod.enableButtons = true;
			AdvSkinMod.enableButtonsTimer = 0;
		}

		World w = Minecraft.getInstance().world;
		
    	if(w != null) {
    		List<PlayerEntity> plrs = new ArrayList<PlayerEntity>(w.getPlayers());
    		if(!prePlayers.equals(plrs)) {
    			if(prePlayers.size() > plrs.size()) {

    				//System.out.println("Logout");
        			for(int i = 0; i < prePlayers.size(); i++) {
        				if(!plrs.contains(prePlayers.get(i))) {
        	    			//AdvSkinMod.log(prePlayers.get(i).getDisplayName().getString());
        	    			if(!prePlayers.get(i).getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
    	    	    			AdvSkinMod.getPlayers().remove(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(prePlayers.get(i).getGameProfile()).toString()));
    	    				}
        				}
        			}
        			
    			} else {
    				//System.out.println("Login");
    			}
    			prePlayers = plrs;
    		}
    		
    	} else {
    		if(AdvSkinMod.getPlayers().size() != 0) {
    			AdvSkinMod.getPlayers().clear(); //clear players in menu
    		}
    	}
	}
	

	@SubscribeEvent
	public void onRenderGUI(GuiScreenEvent.DrawScreenEvent.Post evt) {
		if(AdvSkinMod.UPDATE_OBJ != null) {
			if(AdvSkinMod.UPDATE_OBJ.get("currentVersionLegacy") != null && evt.getGui() instanceof MainMenuScreen) {
				if(!AdvSkinMod.VERSION.equals(AdvSkinMod.UPDATE_OBJ.get("currentVersion").toString())) {
					if(showUpdateTimer < 400) {
						AdvSkinClient.drawTextLabel("Advanced Skin Customization", 2, 2);
						AdvSkinClient.drawTextLabel("Update Available! (v" + AdvSkinMod.UPDATE_OBJ.get("currentVersion").toString() + ")", 2, 13);
					}
					if(!justShownUpdate) {
						justShownUpdate = true;
					}
				}
			} else {
				justShownUpdate = false;
				showUpdateTimer = 0;
			}
		}
	}
    
	
    
    public void onPlayerLeave(PlayerEntity e) {
    	
    }
    
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent evt) {
    	
    	if(evt.getEntity() instanceof PlayerEntity) {
    		PlayerEntity plr = (PlayerEntity) evt.getEntity();
    		String playerName = PlayerEntity.getUUID(plr.getGameProfile()).toString();
    		AdvSkinMod.loadSQLForPlayer(playerName);
    	}
    	
    }
    
    public boolean addedLayer = false;
    
    public ResourceLocation getSkinResourceLocation(GameProfile gProf) {
    	if (gProf != null) {
    		final SkinManager manager = Minecraft.getInstance().getSkinManager();
    		Map<Type, MinecraftProfileTexture> map = manager.loadSkinFromCache(gProf);

    		if (map.containsKey(Type.SKIN)) {
    			final MinecraftProfileTexture skin = map.get(Type.SKIN);
    			return manager.loadSkin(skin, Type.SKIN);
    		} else {
    			UUID uuid = PlayerEntity.getUUID(gProf);
    			return DefaultPlayerSkin.getDefaultSkin(uuid);
    		}
    	}

    	return null;
    }
     
    
    @SubscribeEvent
    public void onPlayerRenderPre(RenderPlayerEvent.Pre evt) {
//fucking kill self
    	/*
    	try {
			String playerName = PlayerEntity.getUUID(((AbstractClientPlayerEntity) evt.getPlayer()).getGameProfile()).toString();
	        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
	        
	        NetworkPlayerInfo networkPlayerInfo = ObfuscationReflectionHelper.getPrivateValue(AbstractClientPlayerEntity.class, (AbstractClientPlayerEntity) evt.getPlayer(), "field_175157_a");
	       	Map<MinecraftProfileTexture.Type, ResourceLocation> texture = ObfuscationReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, networkPlayerInfo, "field_187107_a"); // field_187107_a -> playerTextures
	   		
	        if(plr.skinPack != 0) {
		    	ClothingTextures.getSkinPackSkin(plr.skinPack);
		    	texture.put(MinecraftProfileTexture.Type.SKIN, new ResourceLocation("skins/" + plr.skinPack));
	        } else {
	        	GameProfile prof = ((AbstractClientPlayerEntity) evt.getPlayer()).getGameProfile();
	        	texture.put(MinecraftProfileTexture.Type.SKIN, getSkinResourceLocation(prof));
	        }
    	} catch(Exception e) {}
        */
    	
        for(int adv = 0; adv < AdvSkinMod.CLOTHING.size(); adv++) {
        	AdvSkinMod.CLOTHING.get(adv).setRenderer(evt.getRenderer());
        	if(!AdvSkinMod.CLOTHING.get(adv).isInitialized()) {
        		AdvSkinMod.CLOTHING.get(adv).initialize();
        	}
        }
        
        /*
        if (!addedLayer) {
            LayerClothing clothingLayer = new LayerClothing(evt.getRenderer());
            LayerSteinCape capeLayer = new LayerSteinCape(evt.getRenderer());
            for(PlayerRenderer r : Minecraft.getInstance().getRenderManager().getSkinMap().values()) {
            	r.addLayer(clothingLayer);
            	r.addLayer(capeLayer);
            }
            addedLayer = true;
        } */       
    }
    
    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(AdvSkinSounds.maleDamage);
        event.getRegistry().register(AdvSkinSounds.femaleDamage);
        event.getRegistry().register(AdvSkinSounds.femaleDamage2);
    }
    
    /*
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onHurtEvent(PlaySoundAtEntityEvent evt) {

    	if(evt.getEntity() == null) return;
    	if(!evt.getEntity().world.isRemote) return;
    	if(!(evt.getEntity() instanceof PlayerEntity)) return;

    	if(evt.getEntity() instanceof PlayerEntity) {
    		if(!evt.getSound().getName().toString().contains("hurt")) return;
	    	PlayerEntity pEntity = (PlayerEntity) evt.getEntity();
			ClothingPlayer plr = AdvSkinMod.getPlayerByName(pEntity.getUUID(pEntity.getGameProfile()).toString());
			SoundEvent sEvt = AdvSkinSounds.maleDamage;
			
			if(!plr.gender) {
				Random r = new Random();
				if(r.nextInt(10) < 5) {
					sEvt = AdvSkinSounds.femaleDamage;
				} else {
					sEvt = AdvSkinSounds.femaleDamage2;
				}
				sEvt = AdvSkinSounds.femaleDamage;
			}
	    	System.out.println("HE");
		    Minecraft.getInstance().world.playSound(pEntity.getPosition(), sEvt, pEntity.getSoundCategory(), 1.0F, 1.0F, true);
    	}
    }
    */
    
    private float rotationYawV2 = 0;
    public boolean addAnotherLayer = false;
    private boolean hideFirstPersonBody = false;
	private boolean forceNaturalRotation = false;

    @SubscribeEvent
    public void onPlayerEvent(TickEvent.PlayerTickEvent evt) {
    	
    	
    	if(Minecraft.getInstance().player == evt.player) {
			hideFirstPersonBody = false;
			forceNaturalRotation = false;
	    	if(evt.player.getHeldItemMainhand().getItem() instanceof FilledMapItem) hideFirstPersonBody = true;
	    	if((evt.player.getHeldItemMainhand().getItem() instanceof BowItem) || (evt.player.getHeldItemOffhand().getItem() instanceof BowItem) ||
	    			(evt.player.getHeldItemMainhand().getItem() instanceof CrossbowItem) || (evt.player.getHeldItemOffhand().getItem() instanceof CrossbowItem)) {
	    		hideFirstPersonBody = true;
	    	}
	    	if(evt.player.getRidingEntity() != null) {
	    		hideFirstPersonBody = true;
	    	}
    	}
    	//Iterable<Entity> ENTITIES = Minecraft.getInstance().world.getAllEntities();
    	//RayTraceResult ray = Minecraft.getInstance().objectMouseOver;
    	
    	//Entity ent = Minecraft.getInstance().pointedEntity;
    	//((AbstractClientPlayerEntity) ent).getLocationSkin()
    }
    
    
    
    private int fpsTimer = 0;
    @SubscribeEvent
    public void onRenderSpecificHand(RenderSpecificHandEvent evt) {
    	 
    	if(evt.getHand() == Hand.OFF_HAND) {
    		if(!CFG.generic.getBool("showOffHand")) {
	    		if(evt.getItemStack().isItemEqual(new ItemStack(Items.SHIELD, 1))) evt.setCanceled(true);
    		}
    	}
    	
    	
	    if(CFG.generic.getBool("fp_enabled") && !hideFirstPersonBody) {
	    	Minecraft m = Minecraft.getInstance();
		    if(Minecraft.getInstance().gameSettings.thirdPersonView == 0) {
		    	PlayerEntity ent = Minecraft.getInstance().player;
		  	   	float f = ent.renderYawOffset;
		  	   	float f1 = ent.rotationYaw;
				float f2 = ent.rotationPitch;
				float f3 = ent.prevRotationYawHead;
				float f4 = ent.rotationYawHead;
				float f5 = ent.prevRenderYawOffset;
				EntityRendererManager rMgr = Minecraft.getInstance().getRenderManager();

				GlStateManager.pushMatrix();
				
				float fov2 = Float.valueOf(CFG.generic.getParameter("fp_fov").toString());
				float fovOffset = (float)Math.floor(-20+fov2*40);
				
			    GlStateManager.matrixMode(5889);
			    GlStateManager.loadIdentity();
				GlStateManager.multMatrix(Matrix4f.perspective(Minecraft.getInstance().gameSettings.fov + fovOffset, (float)m.mainWindow.getFramebufferWidth() / (float)m.mainWindow.getFramebufferHeight(), 0.05F, 500 * MathHelper.SQRT_2));
			    GlStateManager.matrixMode(5888);
			    GlStateManager.loadIdentity();
			    
			    GlStateManager.rotatef(Minecraft.getInstance().player.getPitch(evt.getPartialTicks()), 1, 0, 0);
			    
			    if(CFG.generic.getBool("fp_natural") && !forceNaturalRotation) {
				    float tmpYaw = Minecraft.getInstance().player.getYaw(evt.getPartialTicks());
				    float fYaw = (tmpYaw+180) - (ent.prevRenderYawOffset + (ent.renderYawOffset - ent.prevRenderYawOffset) * evt.getPartialTicks());
				    GlStateManager.rotatef(fYaw, 0, 1, 0);
			    } else {
			    	GlStateManager.rotatef(180f, 0, 1, 0);
			    }
				
				//GlStateManager.translatef(0, 0, 2.25f);
				GlStateManager.translatef(0, 0, -0.35f);
				if(ent.shouldRenderSneaking()) {
					GlStateManager.translatef(0, -0.1f, 0);
				}
				float fov = (float) Minecraft.getInstance().gameSettings.fov * -(Minecraft.getInstance().player.getFovModifier()-2);
				//80 = 0.1
				//110 = 0.25
				//30 = -0.55
				float finalFov = (fov / 110) - (0.75f * (fov/110));
				
				GlStateManager.translatef(0, finalFov, 0);
				float headYaw = 0;
				ent.prevRenderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				rotationYawV2 += (headYaw-rotationYawV2) / 8f;
				ent.renderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				ent.rotationYawHead = 0;
				ent.prevRotationYawHead = ent.rotationYawHead;

				if(Minecraft.getInstance().gameSettings.viewBobbing) {
					applyBobbing(evt.getPartialTicks());
				}
				
				String skinType = ((AbstractClientPlayerEntity) ent).getSkinType();
				if(fpsTimer < 20) { //wait 20 frames because it doesn't detect it right away?
					boolean useSmallArms = skinType.contains("default")?false:true;
					AdvSkinClient.fpsRenderer = new SteinPlayerRenderer(rMgr, useSmallArms);
					fpsTimer++;
				}
				if(AdvSkinClient.fpsRenderer != null && 
						ent.getSwimAnimation(evt.getPartialTicks()) == 0 && 
						!ent.isElytraFlying()) {
					GlStateManager.color3f(1, 1, 1);
					
					AdvSkinClient.fpsRenderer.getEntityModel().bipedHead.isHidden = true;
					AdvSkinClient.fpsRenderer.getEntityModel().bipedHeadwear.isHidden = true;
					AdvSkinClient.fpsRenderer.doRender(Minecraft.getInstance().player, 0, ent.shouldRenderSneaking()?-1.3f:-1.8f, 0, 0, evt.getPartialTicks());
					//ObfuscationReflectionHelper.setPrivateValue(classToAccess, instance, value, fieldIndex);
				}
				  	
				//reset values
				ent.renderYawOffset = f;
				ent.rotationYaw = f1;
				ent.rotationPitch = f2;
				ent.prevRenderYawOffset = f5;
				ent.prevRotationYawHead = f3;
				ent.rotationYawHead = f4;
		  	    GlStateManager.popMatrix();
		    }
		    	
			evt.setCanceled(true);
	    }
		
    }

    private void applyBobbing(float partialTicks) {
    	Minecraft mc = Minecraft.getInstance();
    	if (mc.getRenderViewEntity() instanceof PlayerEntity) {
    		PlayerEntity playerentity = (PlayerEntity)mc.getRenderViewEntity();
    		float f = playerentity.distanceWalkedModified - playerentity.prevDistanceWalkedModified;
    		float f1 = -(playerentity.distanceWalkedModified + f * partialTicks);
    		float f2 = MathHelper.lerp(partialTicks, playerentity.prevCameraYaw, playerentity.cameraYaw);
    		GlStateManager.translatef(MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.2F, -Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2), 0.0F);
    		GlStateManager.rotatef(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
    		GlStateManager.rotatef(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
    	}
    }

    @SubscribeEvent
    public void onMouseEvent(MouseInputEvent evt) {
    	if(evt.getAction() == 1) {
    		AdvSkinMod.MOUSE_PRESSED = true;
    	} else if(evt.getAction() == 0) {
    		AdvSkinMod.MOUSE_PRESSED = false;
    	}
    }
    

    AdvSkinScreen advScreen = new AdvSkinScreen();

    @SubscribeEvent
    public void onClickEvent(MouseClickedEvent.Pre evt) {
    	advScreen.mouseClicked(evt.getMouseX(), evt.getMouseY(), evt.getButton());
    }
    
    @SubscribeEvent
    public void onClickEvent(MouseDragEvent.Pre evt) {
    	
    	advScreen.mouseDragged(evt.getMouseX(), evt.getMouseY(), evt.getDragX(), evt.getDragY(), evt.getMouseButton());
    }
    
    @SubscribeEvent
    public void onRenderGUIPost(RenderGameOverlayEvent.Post evt) {
    	if(evt.getType() == ElementType.CROSSHAIRS) {
    		if(!Minecraft.getInstance().gameSettings.showDebugInfo) {
    			advScreen.render(evt.getPartialTicks());
    		}
		}
    }
}
