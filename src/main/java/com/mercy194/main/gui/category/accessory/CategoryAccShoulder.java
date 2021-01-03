package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

public class CategoryAccShoulder extends AdvCategory {

	public CategoryAccShoulder(MercyCustomizationScreen parent) {
		super(parent, "Shoulder");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47, 168, 3, "Backpack: " + (plr.getAccessory("backpack")+1) + "/2", plr.getAccessory("backpack"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessory("backpack", val.getValue());
	        	val.setMessage("Backpack: " + (plr.getAccessory("backpack")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessory("backpack_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessory("backpack_var")));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*1), 168, 4, "Wings: " + (plr.getAccessory("wings")+1) + "/2",plr.getAccessory("wings"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessory("wings", val.getValue());
	        	val.setMessage("Wings: " + (plr.getAccessory("wings")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessory("wings_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessory("wings_var")));
        
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
