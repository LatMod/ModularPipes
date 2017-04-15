package com.latmod.modularpipes.block;

import com.latmod.modularpipes.ModularPipes;
import com.latmod.modularpipes.ModularPipesCommon;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * @author LatvianModder
 */
public class BlockBase extends Block
{
    public BlockBase(String id, Material blockMaterialIn, MapColor blockMapColorIn)
    {
        super(blockMaterialIn, blockMapColorIn);
        setUnlocalizedName(ModularPipes.MOD_ID + '.' + id);
        setRegistryName(ModularPipes.MOD_ID + ':' + id);
        setCreativeTab(ModularPipesCommon.TAB);
    }
}