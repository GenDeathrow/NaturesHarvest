package com.genkash.naturesharvest.proxies;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;

import com.genkash.naturesharvest.init.ModBlocks;
import com.genkash.naturesharvest.init.ModItems;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemAndBlockRenderers() {
		ModItems.registerRenderers();
		ModBlocks.registerRenderers();
	}

	@Override
	public void setCustomStateMap(Block block, StateMap stateMap) {
		ModelLoader.setCustomStateMapper(block, stateMap);
	}
	
	@Override
	public void PreInit() {

	}
	
	@Override	
	public void Init() {

	}
	
	@Override
	public void postInit() {
		// Bind TE Render models here
	}
}
