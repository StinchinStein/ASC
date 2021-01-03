package com.mercy194.main.gui.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.category.accessory.CategoryAccHat;
import com.mercy194.main.gui.category.accessory.CategoryAccLegs;
import com.mercy194.main.gui.category.accessory.CategoryAccMask;
import com.mercy194.main.gui.category.accessory.CategoryAccShoulder;
import com.mercy194.main.gui.category.accessory.CategoryAccTorso;
import com.mercy194.main.gui.category.apparel.CategoryApparelChest;
import com.mercy194.main.gui.category.player.CategoryGeneral;
import com.mercy194.main.gui.elements.MercyButton;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.elements.SteinImageButton;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class MercyCustomizationScreen extends Screen {

    private ResourceLocation BACKGROUND, BACKGROUND2;
    
    //buttons for each category
    private List<ArrayList<AdvCategory>> CATEGORIES = new ArrayList<ArrayList<AdvCategory>>();
    enum AdvCat {
    	PLAYER, APPAREL, ACCESSORY
    }
    private AdvCategory skinGeneral;
    private SteinButton plrBtn, apparelBtn, miscBtn;
    
    private static float modelRotationX = 0.55f;
    
    //private boolean syncServerOnline = false;
    private boolean rotatingModel = false;
    
    public static String[] tooltipText = new String[] { "" };
    
    public MercyCustomizationScreen() {
        super(new TranslationTextComponent("narrator.screen.title"));
		
		modelRotationX = 0.55f;
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    public void addCategory(AdvCat en, AdvCategory cat) {
    	CATEGORIES.get(en.ordinal()).add(cat);
    }
    public void initCategories() {
    	CATEGORIES.clear();
    	CATEGORIES.add(new ArrayList<AdvCategory>());
    	CATEGORIES.add(new ArrayList<AdvCategory>());
    	CATEGORIES.add(new ArrayList<AdvCategory>()); //add 3
    	//for(int i = 0; i < CATEGORIES.size(); i++) CATEGORIES.get(i).clear();
    	
        skinGeneral = new CategoryGeneral(this);
        
        //categories.add(new CategoryHead(this)); //head
        //addCategory(AdvCat.ACCESSORY, new CategoryAccHat(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccMask(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccShoulder(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccTorso(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccLegs(this)); //shoulder
        addCategory(AdvCat.APPAREL, new CategoryApparelChest(this)); //chest
        //addCategory(AdvCat.APPAREL, new CategoryApparelLegs(this)); //chest

        //add the buttons for said categories
        for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {

            	int x = z;
            	int yH = 15;
            	CATEGORIES.get(in).get(z).catBtn = this.addButton(new SteinButton(width / 2 - 180, (height / 2)-63+(yH*x), 52, yH, CATEGORIES.get(in).get(z).dispName, button -> { 
    	            AdvSkinMod.accIndex = x;
    	        }));
        	}
        }
        //categories.add(new CategoryLegs(this)); //head
        //categories.add(new CategoryFeet(this)); //head
    }
    protected void init() {
        int j = this.height / 2;
      
        initCategories();

        

        plrBtn = this.addButton(new SteinButton(width / 2 - 125, j-63, 62, 13, I18n.format(AdvSkinMod.MODID + ".cat.player"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 0;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        }));
        apparelBtn = this.addButton(new SteinButton(width / 2 - 125 + (67), j-63, 68, 13, I18n.format(AdvSkinMod.MODID + ".cat.app"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 1;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        	
        }));
        miscBtn = this.addButton(new SteinButton(width / 2 + 15, j-63, 62, 13, I18n.format(AdvSkinMod.MODID + ".cat.acc"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 2;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        }));

    	plrBtn.active = AdvSkinMod.currentCat!=0;
    	apparelBtn.active = AdvSkinMod.currentCat!=1;
    	miscBtn.active = AdvSkinMod.currentCat!=2;


        
        this.addButton(new SteinImageButton(this.width / 2 + 82, this.height / 2 - 65, 42, 20-4, "Reload", button -> {
        	for(int b = 0; b < AdvSkinMod.getPlayers().size(); b++) {
        		try {
        			AdvSkinMod.getPlayers().get(b).reload();
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
		}));
    	
        //Settings Buttons
        this.addButton(new Button(this.width / 2 - 122, this.height / 2 - 93, 79, 14, "GUI Settings...", button -> { 
        	Minecraft.getInstance().displayGuiScreen(new SteinGUISettingsScreen(this));
        }));
        
        this.addButton(new ImageButton(this.width / 2 + 128, j - 74, 20, 20, 0, 0, 20, new ResourceLocation(AdvSkinMod.MODID, "textures/settings.png"), 32, 64, button -> {
        	Minecraft.getInstance().displayGuiScreen(new SteinRenderSettingsScreen(this));
        }));
        /*
        this.addButton(new ImageButton(this.width / 2 + 128, j - 52, 20, 20, 0, 0, 20, new ResourceLocation(AdvSkinMod.MODID, "textures/reload2.png"), 32, 64, button -> {
        	for(int b = 0; b < AdvSkinMod.getPlayers().size(); b++) {
        		try {
        			AdvSkinMod.getPlayers().get(b).reload();
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }));
        */
		new Thread(new Runnable() {
			public void run() {
		    	AdvSkinMod.syncServerOnline = AdvSkinMod.getServerStatus();
			}
		}).start();

        BACKGROUND = new ResourceLocation(AdvSkinMod.MODID, "textures/gui/wardrobe_bg3.png");
        BACKGROUND2 = new ResourceLocation(AdvSkinMod.MODID, "textures/gui/wardrobe_bg5.png");
    }
    
    public void renderBackground() {
    	
    	super.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int i = (this.width - 370) / 2;
        int j = (this.height - 158) / 2;
        AbstractGui.blit(i, j, 0, 0, 338, 163, 512, 512);
        if(AdvSkinMod.currentCat!=0) AbstractGui.blit(i, j, 340, 0, 60, 112, 512, 512);
        if(AdvSkinMod.currentCat==0) {
            //AbstractGui.blit(i, j, 292, 0, 60, 112, 512, 512);
        }
        
    }
    
    public void render(int f1, int f2, float f3) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer fR = mc.fontRenderer;
		
        this.renderBackground();
        tooltipText = new String[] { "" };
        for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
	        		AdvCategory category = CATEGORIES.get(in).get(z);
	        		if(category.catBtn != null) {
		        		category.catBtn.visible = (AdvSkinMod.currentCat==category.getIndex());
		        		category.catBtn.active = !(AdvSkinMod.accIndex==z);
		        		category.display = (AdvSkinMod.currentCat==category.getIndex()) && (AdvSkinMod.accIndex==z);
	        		}
        	}
        }
        int x = this.width / 2;
        int y = this.height / 2;

        String syncServerStatus = I18n.format(AdvSkinMod.MODID + ".server") + ": " + (AdvSkinMod.syncServerOnline?ChatFormatting.GREEN + I18n.format(AdvSkinMod.MODID + ".server.online"):ChatFormatting.RED + I18n.format(AdvSkinMod.MODID + ".server.offline"));
        this.font.drawString(syncServerStatus, x + 40, y - 90, 0xCCCCCC);

        this.font.drawString(I18n.format(AdvSkinMod.MODID + ".mod.name"), x - 125, y - 74, 0x444444);
        Collection<NetworkPlayerInfo>playersC=Minecraft.getInstance().getConnection().getPlayerInfoMap();
    	playersC.forEach((loadedPlayer) -> {
			String loadedPlayerName = loadedPlayer.getGameProfile().getId().toString();
			if(loadedPlayerName.contentEquals("bdd55ea2-7814-48b6-b684-22c9666350b3")) {
        		if(!PlayerEntity.getUUID(minecraft.player.getGameProfile()).toString().contentEquals("bdd55ea2-7814-48b6-b684-22c9666350b3")) {
	        		this.font.drawStringWithShadow("You're playing on a server with the Advanced Skin Customization creator!", this.width / 2 - minecraft.fontRenderer.getStringWidth("You're playing on a server with the Advanced Skin Customization creator!") / 2, 12, 0xFF00FF);
	        	}
        	}
		});

		//String t = "Using Mod: " + AdvSkinMod.getCountUsingMod();
		//fR.drawStringWithShadow(t, mc.mainWindow.getScaledWidth() / 2 + 54, mc.mainWindow.getScaledHeight() / 2 + 72, 0xFFFFFF);
        
		skinGeneral.display = AdvSkinMod.currentCat==0;
		skinGeneral.render(f1, f2, f3);
		
		/*
		String t = "Players Using Mod: " + AdvSkinMod.getCountUsingMod();
		fR.drawStringWithShadow(t, mc.mainWindow.getScaledWidth() / 2 - 135 - fR.getStringWidth(t), mc.mainWindow.getScaledHeight() / 2 - 72, 0xFFFFFF);
		if(Minecraft.getInstance().world != null) {
    		List<ClothingPlayer> l = AdvSkinMod.getPlayersUsingMod();
    		for(int i = 0; i < l.size(); i++) {
    			try {
	    			String txt = Minecraft.getInstance().world.getPlayerByUuid(UUID.fromString(l.get(i).username)).getDisplayName().getString();
	    			fR.drawStringWithShadow(txt, mc.mainWindow.getScaledWidth() / 2 - 135 - fR.getStringWidth(txt), mc.mainWindow.getScaledHeight() / 2 - 60 + (i*12), 0xFFFFFF);	
	    		} catch(Exception e) {}
    		}
		}*/
		
		
        //Draw Entity
        GlStateManager.color3f(1f, 1f, 1f);
        int xP = this.width / 2 + 78+10;
        int yP = this.height / 2 + 56;
        
		drawEntityOnScreen(xP, yP, 50, xP - f1, yP-76 - f2, Minecraft.getInstance().player);
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.render(f1, f2, f3);
        	}
        }
		
		super.render(f1, f2, f3);

		if(!tooltipText[0].equals("")) {
			GuiUtils.drawHoveringText(Lists.newArrayList(tooltipText), f1, f2, minecraft.mainWindow.getWidth(), minecraft.mainWindow.getHeight(), minecraft.mainWindow.getWidth(), minecraft.fontRenderer);
		}
		/*
		if(currentCat==0) {
	        if(f1 > this.width / 2 - 114 && f2 > this.height / 2 - 77 && f1 < this.width / 2 - 90 && f2 < this.height / 2 - 49) {
	        	GuiUtils.drawHoveringText(Lists.newArrayList("Transformers Skin Pack"), f1, f2, minecraft.mainWindow.getWidth(), minecraft.mainWindow.getHeight(), 160, minecraft.fontRenderer);
	        }
		}*/

    }

    public boolean mouseReleased(double f1, double f2, int f3) {
		skinGeneral.mouseReleased(f1, f2, f3);
		
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseReleased(f1, f2, f3);
        	}
        }
		
    	rotatingModel = false;
    	return super.mouseReleased(f1, f2, f3);
    }
    public boolean mouseClicked(double f1, double f2, int f3) {
		skinGeneral.mouseClicked(f1, f2, f3);
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseClicked(f1, f2, f3);
        	}
        }
/*
    	if(currentCat==0) {
	    	if(f1 > this.width / 2 - 114 && f2 > this.height / 2 - 77 && f1 < this.width / 2 - 90 && f2 < this.height / 2 - 49) {
	    		Minecraft.getInstance().displayGuiScreen(new MercySkinPackScreen());
	    	    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	    	}
    	}
    	*/
		return super.mouseClicked(f1, f2, f3);
    }
    
    @Override
    public boolean mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	skinGeneral.mouseDragged(f1, f2, f3, f4, f5);

    	for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseDragged(f1, f2, f3, f4, f5);
        	}
        }
    	
    	if(f1 > this.width / 2 + 40+8 && f2 > this.height / 2 - 47 && f1 < this.width / 2 + 117+8 && f2 < this.height / 2 + 64) {
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
