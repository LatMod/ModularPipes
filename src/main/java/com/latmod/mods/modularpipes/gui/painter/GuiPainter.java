package com.latmod.mods.modularpipes.gui.painter;

import com.latmod.mods.modularpipes.ModularPipes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author LatvianModder
 */
public class GuiPainter extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(ModularPipes.MOD_ID, "textures/gui/painter.png");

	public final ContainerPainter container;
	public ButtonPaint buttonPaint;

	public GuiPainter(ContainerPainter c)
	{
		super(c);
		container = c;
		ySize = 118;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addButton(buttonPaint = new ButtonPaint(this, 0, guiLeft + 80, guiTop + 11));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button instanceof ButtonPaint)
		{
			if (container.enchantItem(container.player, 0))
			{
				mc.playerController.sendEnchantPacket(container.windowId, 0);
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}


}
