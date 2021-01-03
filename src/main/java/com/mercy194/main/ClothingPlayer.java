package com.mercy194.main;

import org.json.simple.JSONObject;

import com.mercy194.render.LayerSteinCape;

import net.minecraft.util.ResourceLocation;

public class ClothingPlayer {

	public String username;
	public boolean gender;
	public int skinPack;
	public int partSize;
	public JSONObject accessories = new JSONObject();
	public String capeURL;
	public String preCapeURL;
	public boolean usingMod = false;
	
	public ResourceLocation RES_CAPE, RES_SKIN;
	
	public ClothingPlayer(String username) {
		this(username, true);
	}
	public ClothingPlayer(String username, boolean gender) {
		this.username = username;
		this.gender = gender;
		this.partSize = 1;
		this.skinPack = 0;
		this.capeURL = "";
	}

	public int getAccessory(String k) {
		if(this.accessories.get(k) != null) {
			return Integer.valueOf(this.accessories.get(k).toString());
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	public void updateAccessory(String k, int v) {
		this.accessories.put(k, v);
		AdvSkinMod.updateAccessory(k, v, this); //update on database for other players
	}

	public void setAccessory(String k, int v) {
		this.accessories.put(k, v);
	}
	@SuppressWarnings("unchecked")
	public void resetAccessories() {
		this.accessories.clear();
		AdvSkinMod.clearAccessories(this); //update on database for other players
	}
	public void reload() {
		AdvSkinMod.loadSQLForPlayer(this.username);
	}
}
