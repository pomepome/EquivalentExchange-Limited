package ee.gui;

import org.lwjgl.opengl.GL11;

import ee.features.tiles.DMPedestalTile;
import ee.gui.container.ContainerPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPedestal extends GuiContainer
{
	private ResourceLocation texture = new ResourceLocation("ee", "textures/gui/pedestal.png");

	public GuiPedestal(InventoryPlayer inventory, DMPedestalTile tile)
	{
		super(new ContainerPedestal(inventory, tile));
		xSize = 175;
		ySize = 135;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
