package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.ClothingTextures;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccMask extends AdvCategory {

	public CategoryAccMask(MercyCustomizationScreen parent) {
		super(parent, "Head");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*0), 168, 2, "Hat: " + (plr.getAccessory("hat")+1) + "/2", plr.getAccessory("hat"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessory("hat", val.getValue());
	        	val.setMessage("Hat: " + (plr.getAccessory("hat")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessory("hat_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessory("hat_var")));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*1), 168, "Gas Mask: " + (plr.getAccessory("mask")+1) + "/2", plr.getAccessory("mask"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessory("mask", val.getValue());
	        	val.setMessage("Gas Mask: " + (plr.getAccessory("mask")+1) + "/2");
        	}
        }));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*2), 168, 4, "Mask: " + (plr.getAccessory("facemask")+1) + "/2", plr.getAccessory("facemask"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessory("facemask", val.getValue());
	        	val.setMessage("Mask: " + (plr.getAccessory("facemask")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessory("facemask_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessory("facemask_var")));
        /*
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*1), 120, "Wings: 0/0", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*2), 120, "Jacket: 0/0", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*3), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*4), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*5), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*6), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*7), 120, "Another Item", 0, val -> {
        }));
        */
	}

	public int getIndex() {
		return 2;
	}

}
