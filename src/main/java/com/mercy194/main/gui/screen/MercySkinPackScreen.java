package com.mercy194.main.gui.screen;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.elements.SteinButton;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.realmsclient.gui.ChatFormatting;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.fml.client.config.GuiUtils;

public class MercySkinPackScreen extends Screen {

    private ResourceLocation BACKGROUND;
    

    private ArrayList<SteinButton> BUTTONS = new ArrayList<SteinButton>();
    
    private static float modelRotationX = 0.55f;
    
    private boolean syncServerOnline = false;
    private boolean rotatingModel = false;
    
    public MercySkinPackScreen() {
        super(new TranslationTextComponent("narrator.screen.title"));
		
		modelRotationX = 0.55f;
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }
    private ScrollPanel scrollPanel;
    protected void init() {
    	BUTTONS.clear();
        int j = this.height / 2;

    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);

        BUTTONS.add(new SteinButton(width / 2 - 82, j-67, 110, 18, "Starscream", button -> { 
            plr.skinPack = 1;
            AdvSkinMod.updateStatus(plr);
        }));
        
        //SKYWARP
        BUTTONS.add(new SteinButton(width / 2 - 82, j-67, 110, 18, "Skywarp", button -> {
            plr.skinPack = 2;
            AdvSkinMod.updateStatus(plr);
        	//Equip Starscream Models
        }));
        BUTTONS.add(new SteinButton(width / 2 - 82, j-67, 110, 18, "Thundercracker", button -> { 
            plr.skinPack = 3;
            AdvSkinMod.updateStatus(plr);
        	//Equip Starscream Models
        }));
        scrollPanel = new ScrollPanel(minecraft, 120, 150, this.height / 2 - 72, this.width / 2 - 84) {
			
			@Override
			protected int getContentHeight() {
				// TODO Auto-generated method stub
				return 420;
			}
			
			@Override
			protected void drawPanel(int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
				for(int i = 0; i < BUTTONS.size(); i++) {
					BUTTONS.get(i).y = this.height / 2 - 77 + (i*20) + relativeY;
					BUTTONS.get(i).render(mouseX, mouseY, 0);
				}
				
			}
		};

        //Settings Buttons
        this.addButton(new Button(this.width / 2 - 86, this.height / 2 - 93, 79, 14, "GUI Settings...", button -> { 
        	Minecraft.getInstance().displayGuiScreen(new SteinGUISettingsScreen(this));
        }));
        
        this.addButton(new ImageButton(this.width / 2 + 120, j - 74, 20, 20, 0, 0, 20, new ResourceLocation(AdvSkinMod.MODID, "textures/settings.png"), 32, 64, button -> {
        	Minecraft.getInstance().displayGuiScreen(new SteinRenderSettingsScreen(this));
        }));
        this.addButton(new ImageButton(this.width / 2 + 120, j - 52, 20, 20, 0, 0, 20, new ResourceLocation(AdvSkinMod.MODID, "textures/reload2.png"), 32, 64, button -> {
        	for(int b = 0; b < AdvSkinMod.getPlayers().size(); b++) {
        		try {
        			AdvSkinMod.getPlayers().get(b).reload();
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }));
        
        new Thread(new Runnable() {
			public void run() {
				syncServerOnline = AdvSkinMod.getServerStatus();
			}
		}).start();
		
        BACKGROUND = new ResourceLocation(AdvSkinMod.MODID, "textures/gui/wardrobe_bg4.png");
    }
    
    public void renderBackground() {
    	super.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int i = (this.width - 290) / 2;
        int j = (this.height - 158) / 2;
        AbstractGui.blit(i, j, 0, 0, 290, 163, 512, 512);
        
    }
    
    public float scrollPos = 0;
    public void render(int f1, int f2, float f3) {
        this.renderBackground();
        
        scrollPanel.render(f1, f2, f3);
        int x = this.width / 2;
        int y = this.height / 2;
        String syncServerStatus = "Sync Servers: " + (syncServerOnline?ChatFormatting.GREEN + "Online":ChatFormatting.RED + "Offline");
        this.font.drawString(syncServerStatus, x + 40, y - 90, 0xCCCCCC);
        
        this.font.drawString("Model Packs", x + 48, y - 66, 0x444444);

        if(f1 > this.width / 2 - 114 && f2 > this.height / 2 - 77 && f1 < this.width / 2 - 90 && f2 < this.height / 2 - 49) {
        	GuiUtils.drawHoveringText(Lists.newArrayList("Transformers Skin Pack"), f1, f2, minecraft.mainWindow.getWidth(), minecraft.mainWindow.getHeight(), 160, minecraft.fontRenderer);
        }
        if(f1 > this.width / 2 - 114 && f2 > this.height / 2 + 32 && f1 < this.width / 2 - 90 && f2 < this.height / 2 + 59) {
        	GuiUtils.drawHoveringText(Lists.newArrayList("Disable Skin Pack"), f1, f2, minecraft.mainWindow.getWidth(), minecraft.mainWindow.getHeight(), 160, minecraft.fontRenderer);
        }
        //Draw Entity
        GlStateManager.color3f(1f, 1f, 1f);
        int xP = this.width / 2 + 78;
        int yP = this.height / 2 + 56;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
		drawEntityOnScreen(xP, yP, 50, xP - f1, yP-76 - f2, Minecraft.getInstance().player);
	
		
		super.render(f1, f2, f3);

    }

    public boolean mouseReleased(double f1, double f2, int f3) {

		scrollPanel.mouseReleased(f1, f2, f3);
		
    	rotatingModel = false;
    	return super.mouseReleased(f1, f2, f3);
    }
    public boolean mouseScrolled(double f1, double f2, double f3) {

		scrollPanel.mouseScrolled(f1, 2f, f3);
		
    	return super.mouseScrolled(f1, f2, f3);
    }
    
    public boolean mouseClicked(double f1, double f2, int f3) {
    	scrollPanel.mouseClicked(f1, f2, f3);

    	if(f2 > this.height / 2 - 73) {
    		for(int i = 0; i < BUTTONS.size(); i++) {
    			BUTTONS.get(i).mouseClicked(f1, f2, f3);
    		}
    	}
    	if(f1 > this.width / 2 - 114 && f2 > this.height / 2 + 32 && f1 < this.width / 2 - 90 && f2 < this.height / 2 + 59) {
        	Minecraft m = Minecraft.getInstance();
        	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
            ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
            plr.skinPack = 0;
            AdvSkinMod.updateStatus(plr);
            Minecraft.getInstance().displayGuiScreen(new MercyCustomizationScreen());
    	    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    	}
		return super.mouseClicked(f1, f2, f3);
    }
    
    @Override
    public boolean mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	scrollPanel.mouseDragged(f1, f2, f3, f4, f5);
    	
    	if(f1 > this.width / 2 + 40 && f2 > this.height / 2 - 47 && f1 < this.width / 2 + 117 && f2 < this.height / 2 + 64) {
    		rotatingModel = true;
    	}
    	if(rotatingModel) {
        	modelRotationX += f4 / 150f;
    	}
    	return super.mouseDragged(f1, f2, f3, f4, f5);
    }
    
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity ent) {
	      GlStateManager.enableColorMaterial();
	      GlStateManager.pushMatrix();
	      GlStateManager.translatef((float)posX, (float)posY, 50.0F);
	      GlStateManager.scalef((float)(-scale), (float)scale, (float)scale);
	      GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
	      float f = ent.renderYawOffset;
	      float f0 = ent.prevRenderYawOffset;
	      float f1 = ent.rotationYaw;
	      float f2 = ent.rotationPitch;
	      float f3 = ent.prevRotationYawHead;
	      float f4 = ent.rotationYawHead;
	      float f5 = ent.prevRotationPitch;
	      
	      GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
	      RenderHelper.enableStandardItemLighting();
	      GlStateManager.rotatef((modelRotationX) * 365f, 0.0F, 1.0F, 0.0F);
	      //GlStateManager.rotatef((modelRotationY+0.12f) * 365f, 1.0F, 0.0F, 0.0F);
	      ent.renderYawOffset = 0;
	      ent.prevRenderYawOffset=0;
	      ent.rotationYaw = 0;
	      ent.rotationPitch = -mouseY / 6;
	      ent.prevRotationPitch = ent.rotationPitch;
	      ent.rotationYawHead = mouseX / 6;
	      ent.prevRotationYawHead = ent.rotationYawHead;
	      GlStateManager.translatef(0.0F, 0.0F, 0.0F);
	      EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
	      entityrenderermanager.setPlayerViewY(180.0F);
	      entityrenderermanager.setRenderShadow(false);
	      entityrenderermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, Minecraft.getInstance().getRenderPartialTicks(), Minecraft.getInstance().getRenderPartialTicks(), false);
	      entityrenderermanager.setRenderShadow(true);
	      ent.renderYawOffset = f;
	      ent.prevRenderYawOffset = f0;
	      ent.rotationYaw = f1;
	      ent.rotationPitch = f2;
	      ent.prevRotationYawHead = f3;
	      ent.rotationYawHead = f4;
	      ent.prevRotationPitch = f5;
	      GlStateManager.popMatrix();
	      RenderHelper.disableStandardItemLighting();
	      GlStateManager.disableRescaleNormal();
	      GlStateManager.activeTexture(GLX.GL_TEXTURE1);
	      GlStateManager.disableTexture();
	      GlStateManager.activeTexture(GLX.GL_TEXTURE0);
	   }
    
}
