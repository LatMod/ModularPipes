package com.latmod.modularpipes.block;

import com.feed_the_beast.ftbl.lib.config.ConfigDouble;
import com.feed_the_beast.ftbl.lib.util.misc.NameMap;
import net.minecraft.util.IStringSerializable;

/**
 * @author LatvianModder
 */
public enum EnumTier implements IStringSerializable
{
	BASIC("basic", 1D),
	IRON("iron", 1D),
	GOLD("gold", 1.5D),
	QUARTZ("quartz", 2D),
	LAPIS("lapis", 2.5D),
	ENDER("ender", 4D),
	EMERALD("emerald", 5D),
	STAR("star", 10D);

	public static final NameMap<EnumTier> NAME_MAP = NameMap.create(BASIC, values());

	private final String name;
	public final int modules;
	public final ConfigDouble speed;

	EnumTier(String n, double d)
	{
		name = n;
		modules = Math.min(ordinal(), 6);
		speed = new ConfigDouble(d, 1D, 100D);
	}

	@Override
	public String getName()
	{
		return name;
	}
}