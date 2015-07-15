package ee.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ee.features.tile.TileEntityAlchChest;

public class GuiAlchChest extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("ee","textures/gui/alchchest.png");

	public GuiAlchChest(InventoryPlayer invPlayer,InventoryAlchBag invBag,int meta) {
		super(new ContainerAlchBag(invPlayer, invBag,meta));
		this.xSize = 255;
		this.ySize = 230;
	}
	public GuiAlchChest(InventoryPlayer invPlayer,TileEntityAlchChest invChest) {
		super(new ContainerAlchChest(invPlayer, invChest));
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
