package com.mercy194.modules;

import com.mercy194.main.SteinEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class AdvSkinModule {
	
	public AdvSkinModule() {
        MinecraftForge.EVENT_BUS.register(this);
        
	}
}
