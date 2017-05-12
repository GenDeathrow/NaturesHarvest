package com.genkash.naturesharvest.blocks;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.genkash.naturesharvest.NaturesHarvest;

public class CropsBase extends BlockCrops
{
    //public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    
    public float GROWTHCHANCE = 25.0F;
    protected Item SEED = Items.WHEAT_SEEDS;
    protected Item CROP = Items.WHEAT;
    
    public CropsBase(Item seedIn, Item cropIn)
    {
    	super();
    	this.SEED = seedIn;
    	this.CROP = cropIn;
    	this.setCreativeTab(NaturesHarvest.TAB);
    }
    
    /**
     * Set Growth Chance on random tick. Default: 25 <br>
     * Lower Better
     * @return
     */
    public CropsBase setGrowthChance(float newChance)
    {
    	this.GROWTHCHANCE = newChance;
    	return this;
    }
    
    @Override
    protected Item getSeed()
    {
        return this.SEED;
    }
    
    @Override
    protected Item getCrop()
    {
        return  this.CROP;
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	this.checkAndDropBlock(worldIn, pos, state);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if (i < this.getMaxAge())
            {
                float f = CropsBase.getGrowthChance(this, worldIn, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(GROWTHCHANCE / f) + 1) == 0))
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }
    

}
