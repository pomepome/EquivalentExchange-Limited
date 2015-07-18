package ee.gui;

import org.lwjgl.opengl.GL11;

import ee.features.tile.TileEMCCharger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiEMCCharger extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("ee","textures/gui/Charger.png");
	public GuiEMCCharger(InventoryPlayer p,TileEMCCharger tile)
	{
		super(new ContainerCharger(p,tile));
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(40+(width - xSize) / 2, 14+(height - ySize) / 2, 0, 0, xSize, ySize);
	}

}
