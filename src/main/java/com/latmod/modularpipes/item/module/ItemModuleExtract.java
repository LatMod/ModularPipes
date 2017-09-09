package com.latmod.modularpipes.item.module;

import com.latmod.modularpipes.data.ModuleContainer;
import com.latmod.modularpipes.data.TransportedItem;
import com.latmod.modularpipes.item.ItemModule;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * @author LatvianModder
 */
public class ItemModuleExtract extends ItemModule
{
	public ItemModuleExtract(String id)
	{
		super(id);
	}

	@Override
	public void updateModule(ModuleContainer container)
	{
		if (!container.isRemote() && container.getTick() % 20 == 19)
		{
			extractItem(container);
		}
	}

	public void extractItem(ModuleContainer container)
	{
		TileEntity tile = container.getFacingTile();

		if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, container.facing.getOpposite()))
		{
			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, container.facing.getOpposite());

			if (handler != null)
			{
				int slot = -1;
				ItemStack stack = ItemStack.EMPTY;

				for (int i = 0; i < handler.getSlots(); i++)
				{
					stack = handler.extractItem(i, 1, true);

					if (!stack.isEmpty())
					{
						slot = i;
						break;
					}
				}

				if (slot != -1)
				{
					TransportedItem item = new TransportedItem(container.getNetwork());
					item.stack = stack;
					item.filters = container.getFilterConfig().get();

					if (item.generatePath(container))
					{
						handler.extractItem(slot, 1, false);
						item.addToNetwork();
					}
				}
			}
		}
	}
}