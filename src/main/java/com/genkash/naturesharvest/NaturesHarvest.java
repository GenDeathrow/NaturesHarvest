package com.genkash.naturesharvest;

import java.io.IOException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import com.genkash.naturesharvest.init.ModBlocks;
import com.genkash.naturesharvest.init.ModItems;
import com.genkash.naturesharvest.init.ModRecipes;
import com.genkash.naturesharvest.init.ModReference;
import com.genkash.naturesharvest.proxies.CommonProxy;



@Mod(modid = ModReference.MOD_ID, name = ModReference.NAME, version = ModReference.VERSION)
public class NaturesHarvest {

	@Instance(ModReference.MOD_ID)
	public static NaturesHarvest INSTANCE;

	@SidedProxy(clientSide = ModReference.PROXY_PATH + ".ClientProxy", serverSide = ModReference.PROXY_PATH + ".CommonProxy")
	public static CommonProxy PROXY;

	public static SimpleNetworkWrapper NETWORK_WRAPPER;

	public static CreativeTabs TAB = new CreativeTabs(ModReference.MOD_ID) {
		@Override
		public Item getTabIconItem() {
			return Items.CARROT;
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.init();
		ModBlocks.init();
		PROXY.registerTileEntities();
		PROXY.registerItemAndBlockRenderers();
		ModRecipes.addRecipes();
		NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(ModReference.CHANNEL_NAME);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) throws IOException {
		PROXY.Init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit();
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {

	}
}