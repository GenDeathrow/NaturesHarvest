package com.genkash.naturesharvest.init;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import com.genkash.naturesharvest.NaturesHarvest;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	//Example entry you just need to add 1 Line for the Item Now!!!!
	//public static final Item NAME_OF_ITEM = new ItemWhatever();
	
	public static final Item tomatoe_seeds = new ItemSeeds(ModBlocks.tomatoe_plant, Blocks.FARMLAND);
	public static final Item tomatoe = new ItemFood(5, 10, false);

	public static void init() 
	{
		try 
		{
			for (Field field : ModItems.class.getDeclaredFields()) 
			{
				Object obj = field.get(null);
				if (obj instanceof Item) 
				{
					Item item = (Item) obj;
					String name = field.getName().toLowerCase(Locale.ENGLISH);
					GameRegistry.register(item.setRegistryName(ModReference.MOD_ID, name).setUnlocalizedName(ModReference.MOD_ID + "." + name).setCreativeTab(NaturesHarvest.TAB));
				}
			}
		} catch (IllegalAccessException e) 
		{
			throw new RuntimeException(e);
		}
	}

	public static void registerRenderers() 
	{
		try {
			for (Field field : ModItems.class.getDeclaredFields()) 
			{
				Object obj = field.get(null);
				if (obj instanceof Item) 
				{
					Item item = (Item) obj;

					if (item instanceof ISubItemsItem) 
					{
						List<String> models = ((ISubItemsItem) item).getModels();
						for (int i = 0; i < models.size(); i++)
							ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(ModReference.MOD_ID + ":" + models.get(i), "inventory"));
					} else 
					{
						String name = field.getName().toLowerCase(Locale.ENGLISH);
						ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ModReference.MOD_ID + ":" + name, "inventory"));
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static interface ISubItemsItem {
		List<String> getModels();
	}

}
