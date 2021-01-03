package com.mercy194.api;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mercy194.gfx.SteinSkinnedBox;
import com.mercy194.main.AdvSkinMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;

public class MercyModel {

	public ArrayList<Texture> textures = new ArrayList<Texture>();
	public ArrayList<SteinSkinnedBox> elements = new ArrayList<SteinSkinnedBox>();
	public JSONObject jsonData = new JSONObject();
	
	@SuppressWarnings("unchecked")
	public MercyModel(String filename) {
		try {
			ResourceLocation l = new ResourceLocation(AdvSkinMod.MODID, filename);
			InputStream stream = Minecraft.getInstance().getResourceManager().getResource(l).getInputStream();
			//File file = new File(l.getPath());
			InputStreamReader r = new InputStreamReader(stream);
			jsonData = (JSONObject) new JSONParser().parse(r);

			//JSONArray TEXTURES = (JSONArray) jsonData.get("textures");
			JSONArray ELEMENTS = (JSONArray) jsonData.get("elements");
			ELEMENTS.forEach(e -> {
				JSONObject element = (JSONObject) e;
				JSONArray FARRAY = (JSONArray) element.get("from");
				Vector3 from = new Vector3(FARRAY.get(0).toString(), FARRAY.get(1).toString(), FARRAY.get(2).toString());
				JSONArray TARRAY = (JSONArray) element.get("to");
				Vector3 to = new Vector3(TARRAY.get(0).toString(), TARRAY.get(1).toString(), TARRAY.get(2).toString());
				JSONObject f1 = (JSONObject) element.get("faces");
				JSONArray UVARRAY = (JSONArray) ((JSONObject)f1.get("north")).get("uv");
				
				Vector4 uv = new Vector4(UVARRAY.get(0).toString(), UVARRAY.get(1).toString(), UVARRAY.get(2).toString(), UVARRAY.get(3).toString());
				SteinSkinnedBox sBox = new SteinSkinnedBox(16, 16, uv.z, uv.w, from.x, from.y, from.z, to.x-from.x, to.y-from.y, to.z-from.z);
				elements.add(sBox);
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
