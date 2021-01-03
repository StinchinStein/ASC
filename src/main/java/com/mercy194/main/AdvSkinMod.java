package com.mercy194.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mercy194.main.proxy.AdvSkinClient;
import com.mercy194.main.proxy.AdvSkinServer;
import com.mercy194.render.LayerSteinCape;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("advskinmod")
public class AdvSkinMod {
	private static final String SYNC_URL = "http://advmcskins.tk";//port=:25463
	
	public static final String VERSION = "2.6.2";
	public static final String MODID = "advskinmod";

    private static ArrayList<ClothingPlayer> CLOTHING_PLAYER = new ArrayList<ClothingPlayer>();    
    public static ArrayList<AdvClothing> CLOTHING = new ArrayList<AdvClothing>();
    
    public static boolean syncServerOnline = false;
    public static boolean MOUSE_PRESSED = false;

	public static JSONObject UPDATE_OBJ = new JSONObject();

    public static int accIndex = 0;
    public static int appIndex = 0;
    public static int currentCat = 0;
    
    public static boolean enableButtons = true;
    public static int enableButtonsTimer = 0;
    
    public static final AdvSkinServer PROXY = DistExecutor.runForDist(() -> AdvSkinClient::new, () -> AdvSkinServer::new);

    
	public static void addClothingToMod(AdvClothing c) {
		CLOTHING.add(c);
	}
	
    public static ClothingPlayer getPlayerByName(String username) {
    	for(int i = 0; i < CLOTHING_PLAYER.size(); i++) {
    		if(CLOTHING_PLAYER.get(i) != null) {
	    		if(username.toLowerCase().equals(CLOTHING_PLAYER.get(i).username.toLowerCase())) {
	    			return CLOTHING_PLAYER.get(i);
	    		}
    		}
    	}
    	ClothingPlayer plr = new ClothingPlayer(username);
    	CLOTHING_PLAYER.add(plr);
    	return plr;
    }

    public static JSONObject SKIN_OVERRIDES;
    
    public AdvSkinMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public static void loadSQLForPlayer(String username) {
    	new Thread(new Runnable() {
			public void run() {
				loadSQLForPlayerSync(username);
			}
    	}).start();
    }
    

    public static ClothingPlayer loadSQLForPlayerSync(String username) {
    	if(syncServerOnline) {
	        try {
				String url = SYNC_URL + "/ClothingMod/get_data.php?user=" + username;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
				String urlParameters = "";
				
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
		
				//responseCode = con.getResponseCode();
				
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				try {
					JSONObject jObj = (JSONObject) new JSONParser().parse(response.toString());
					
					if(getPlayerByName(username) != null) {
						ClothingPlayer plr = getPlayerByName(username);
						plr.gender = Boolean.valueOf(jObj.get("gender").toString());
						plr.partSize = Integer.valueOf(jObj.get("part_size").toString());
						plr.skinPack = Integer.valueOf(jObj.get("skin_pack").toString());
						plr.capeURL = jObj.get("capeURL").toString();
						plr.usingMod = true;
						try {
							if(jObj.get("extras") != null) plr.accessories = (JSONObject) new JSONParser().parse(jObj.get("extras").toString());
						} catch(Exception e) {}
				    	
				    	
					} else {
						ClothingPlayer plr = new ClothingPlayer(username);
						plr.gender = Boolean.valueOf(jObj.get("gender").toString());
						plr.skinPack = Integer.valueOf(jObj.get("skin_pack").toString());
						plr.capeURL = jObj.get("capeURL").toString();
						plr.usingMod = true;
						try {
							if(jObj.get("extras") != null) plr.accessories = (JSONObject) new JSONParser().parse(jObj.get("extras").toString());
						} catch(Exception e) {}
						CLOTHING_PLAYER.add(plr);
					}
				} catch(Exception e) {}
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
    	}
    	return getPlayerByName(username);
    }
    

    public static boolean getServerStatus() {

        try {
			String url = SYNC_URL + "/ClothingMod/server_status.php";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes("");
			wr.flush();
			wr.close();
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			if(response.toString().contains("SUCCESS")) {
				return true;
			}
			return false;
			
        } catch(Exception e) {
        	e.printStackTrace();
        }
		return false;
    }

    private void setupClient(final FMLClientSetupEvent event) {
    	PROXY.register();
    	//don't do shit here!
    }


    public static void updateStatus(ClothingPlayer plr) {
    	if(syncServerOnline) {
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
							
			        try {
						String url = SYNC_URL + "/ClothingMod/set_data.php?user=" + plr.username + "&gender=" + plr.gender + "&partSize=" + plr.partSize + "&skinPack=" + plr.skinPack + "&capeURL=" + URLEncoder.encode(plr.capeURL, "UTF-8") + "&version=" + AdvSkinMod.VERSION;
						
						//System.out.println(url);
						
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						
						con.setRequestMethod("POST");
						con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
						String urlParameters = "";
						
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(urlParameters);
						wr.flush();
						wr.close();
					
						BufferedReader in = new BufferedReader(
						        new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
				
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();

			    		
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
				}
			}).start();
    	}
    }


	public static void clearAccessories(ClothingPlayer plr) {
		if(syncServerOnline) {
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
							
			        try {
						String url = SYNC_URL + "/ClothingMod/reset_accessories.php?user=" + plr.username;
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						
						con.setRequestMethod("POST");
						con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
						String urlParameters = "";
						
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(urlParameters);
						wr.flush();
						wr.close();
					
						BufferedReader in = new BufferedReader(
						        new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
				
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
			    		
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
				}
			}).start();
    	} else {
    		//local
    		//CFG.skin.setParameter("gender", plr.gender);
    		//CFG.skin.save();
    	}
	}

    public static void updateAccessory(String key, Object val, ClothingPlayer plr) {
    	if(syncServerOnline) {
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
							
			        try {
						String url = SYNC_URL + "/ClothingMod/set_accessory.php?user=" + plr.username + "&k=" + key + "&v=" + val;
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						
						con.setRequestMethod("POST");
						con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
						String urlParameters = "";
						
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(urlParameters);
						wr.flush();
						wr.close();
					
						BufferedReader in = new BufferedReader(
						        new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
				
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();

			    		
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
				}
			}).start();
    	} else {
    		//local
    		//CFG.skin.setParameter("gender", plr.gender);
    		//CFG.skin.save();
    	}
    }
    

	public static void checkForUpdates() {
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
							
			        try {
						String url = SYNC_URL + "/ClothingMod/update_check.json";
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
						con.setRequestMethod("POST");
						con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
						String urlParameters = "";
						
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(urlParameters);
						wr.flush();
						wr.close();
					
						BufferedReader in = new BufferedReader(
						        new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
				
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();

						JSONObject obj2 = (JSONObject) new JSONParser().parse(response.toString());
						AdvSkinMod.UPDATE_OBJ = obj2;
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
				}
			}).start();
	}

	
	public static ArrayList<ClothingPlayer> getPlayersUsingMod() {
		ArrayList<ClothingPlayer> p = new ArrayList<ClothingPlayer>();
		if(CLOTHING_PLAYER.size() > 0) {
			for(int i = 0; i < CLOTHING_PLAYER.size(); i++) {
				if(CLOTHING_PLAYER.get(i).usingMod) p.add(CLOTHING_PLAYER.get(i));
			}
		}
		return p;
	}

	public static int getCountUsingMod() {
		int c = 0;
		if(CLOTHING_PLAYER.size() > 0) {
			for(int i = 0; i < CLOTHING_PLAYER.size(); i++) {
				if(CLOTHING_PLAYER.get(i).usingMod) c++;
			}
		}
		return c;
	}
	public static ArrayList<ClothingPlayer> getPlayers() {
		return CLOTHING_PLAYER;
	}

	public static void log(String msg) {
		System.out.println("[AdvSkinMod] " + msg);
		
	}
}
