package com.genkash.naturesharvest.init;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.genkash.naturesharvest.blocks.CropsBase;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	private static final List<Block> BLOCKS = new LinkedList<Block>();
	
	//Example entry you just need to add 1 Line for the Block Now!!!!
	//public static final Block NAME_OF_BLOCK = new BlockWhatever();
	
	public static final Block tomatoe_plant = new CropsBase(ModItems.tomatoe_seeds, ModItems.tomatoe);

	public static void init() 
	{
		try 
		{
			for (Field field : ModBlocks.class.getDeclaredFields()) 
			{
				Object obj = field.get(null);
				if (obj instanceof Block) 
				{
					Block block = (Block) obj;
					String name = field.getName().toLowerCase(Locale.ENGLISH);
					registerBlock(name, block);
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	public static void registerBlock(String name, Block block) 
	{
		BLOCKS.add(block);

		GameRegistry.register(block.setRegistryName(ModReference.MOD_ID, name).setUnlocalizedName(ModReference.MOD_ID + "." + name));

		ItemBlock item;
		if (block instanceof IHasCustomItem)
			item = ((IHasCustomItem) block).getItemBlock();
		else
			item = new ItemBlock(block);

		GameRegistry.register((ItemBlock) item.setRegistryName(ModReference.MOD_ID, name).setUnlocalizedName(ModReference.MOD_ID + "." + name));
	}

	public static void registerRenderers() 
	{
		for (Block block : BLOCKS)
			if (block instanceof ISubBlocksBlock) 
			{
				List<String> models = ((ISubBlocksBlock) block).getModels();
				for (int i = 0; i < models.size(); i++)
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(ModReference.MOD_ID + ":blocks/" + models.get(i), "inventory"));
			} else 
			{
				ResourceLocation name = block.getRegistryName();
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(ModReference.MOD_ID + ":blocks/" + name.getResourcePath(), "inventory"));
			}
	}

	public static interface IHasCustomItem {
		ItemBlock getItemBlock();
	}

	public static interface ISubBlocksBlock {
		List<String> getModels();
	}
}
