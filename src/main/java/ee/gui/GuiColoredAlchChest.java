package ee.gui;

import org.lwjgl.opengl.GL11;

import ee.features.tiles.TileEntityColoredAlchChest;
import ee.gui.container.ContainerColoredAlchChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiColoredAlchChest extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("ee","textures/gui/alchchest-colored.png");

	public GuiColoredAlchChest(InventoryPlayer invPlayer,TileEntityColoredAlchChest invChest)
	{
		super(new ContainerColoredAlchChest(invPlayer, invChest));
		this.xSize = 255;
		this.ySize = 230;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1,int var2, int var3)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
	}

}
