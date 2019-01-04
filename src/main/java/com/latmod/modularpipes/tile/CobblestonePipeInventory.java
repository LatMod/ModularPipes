package com.latmod.modularpipes.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LatvianModder
 */
public class CobblestonePipeInventory implements IItemHandler, INBTSerializable<NBTTagList>
{
	public final TileCobblestonePipe pipe;
	public final List<PipeItem> items = new ArrayList<>();
	public CobblestonePipeInventory opposite;

	public CobblestonePipeInventory(TileCobblestonePipe t)
	{
		pipe = t;
	}

	@Override
	public NBTTagList serializeNBT()
	{
		NBTTagList list = new NBTTagList();

		for (PipeItem item : items)
		{
			list.appendTag(item.serializeNBT());
		}

		return list;
	}

	@Override
	public void deserializeNBT(NBTTagList list)
	{
		items.clear();

		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt = list.getCompoundTagAt(i);
			PipeItem item = new PipeItem();
			item.deserializeNBT(nbt);

			if (!item.stack.isEmpty())
			{
				items.add(item);
			}
		}
	}

	@Override
	public int getSlots()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if (!simulate)
		{
			PipeItem item = new PipeItem();
			item.stack = stack;
			item.created = pipe.getWorld().getTotalWorldTime();
			item.speed = 0.1F;
			items.add(item);
			pipe.markDirty();
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return 64;
	}

	public void update(EnumFacing to)
	{
		IItemHandler handler = null;
		World world = pipe.getWorld();
		long now = world.getTotalWorldTime();

		Iterator<PipeItem> itemIterator = items.iterator();
		while (itemIterator.hasNext())
		{
			PipeItem item = itemIterator.next();

			if (item.pos >= 1F)
			{
				if (handler == null)
				{
					handler = getInventory(world, to);
				}

				if (handler != opposite && handler instanceof CobblestonePipeInventory)
				{
					CobblestonePipeInventory pipe1 = (CobblestonePipeInventory) handler;
					item.pos %= 1F;
					pipe1.items.add(item);
					pipe1.pipe.markDirty();
					pipe1.pipe.sync = false;
					pipe.markDirty();
					pipe.sync = false;
				}
				else
				{
					ItemStack stack = handler == opposite ? item.stack : ItemHandlerHelper.insertItem(handler, item.stack, world.isRemote);

					if (!stack.isEmpty())
					{
						item.stack = stack;
						item.pos = 0F;
						opposite.items.add(item);
						pipe.markDirty();
					}
				}

				itemIterator.remove();
			}
			else if ((now - item.created) > item.getLifespan(world))
			{
				itemIterator.remove();
				pipe.markDirty();
				pipe.sync = false;
			}
		}
	}

	private IItemHandler getInventory(World world, EnumFacing to)
	{
		BlockPos pos = pipe.getPos().offset(to);

		if (world.isBlockLoaded(pos))
		{
			TileEntity tileEntity = world.getTileEntity(pos);

			if (tileEntity != null)
			{
				IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, to.getOpposite());

				if (handler != null)
				{
					return handler;
				}
			}
		}

		return opposite;
	}
}