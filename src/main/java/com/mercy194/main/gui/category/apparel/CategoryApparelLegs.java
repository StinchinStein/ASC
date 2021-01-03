package com.mercy194.main.gui.category.apparel;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryApparelLegs extends AdvCategory {
	
	public CategoryApparelLegs(MercyCustomizationScreen parent) {
		super(parent, "Legs");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;
        /*
        if(plr.gender==false) {
	        this.addCombo(new SteinComboButton(3, parent.width / 2 - 84, j-47, 120, "Breasts: " + (plr.partSize+1) + "/3", plr.partSize, val -> {
	        	plr.partSize = val.getValue();
	            val.setMessage("Breasts: " + (plr.partSize+1) + "/3");
	        	AdvSkinMod.updateStatus(plr);
	        }));
        }
        */
	}
	

	public int getIndex() {
		return 1;
	}
}
