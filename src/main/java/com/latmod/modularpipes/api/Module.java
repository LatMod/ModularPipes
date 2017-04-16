package com.latmod.modularpipes.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

/**
 * @author LatvianModder
 */
public class Module
{
    public Module()
    {
    }

    public ModuleData createData(ModuleContainer container)
    {
        return NoData.INSTANCE;
    }

    public boolean insertInPipe(ModuleContainer container, EntityPlayer player)
    {
        return true;
    }

    public void removeFromPipe(ModuleContainer container, EntityPlayer player)
    {
    }

    public void pipeBroken(ModuleContainer container)
    {
    }

    public void update(ModuleContainer container)
    {
    }

    public boolean onRightClick(ModuleContainer container, EntityPlayer player, EnumHand hand)
    {
        return false;
    }
}