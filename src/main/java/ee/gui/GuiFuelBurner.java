package ee.gui;

import org.lwjgl.opengl.GL11;

import ee.features.tiles.TileFuelBurner;
import ee.gui.container.ContainerFuelBurner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFuelBurner extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("ee","textures/gui/Charger.png");
	public GuiFuelBurner(InventoryPlayer p,TileFuelBurner tile)
	{
		super(new ContainerFuelBurner(p,tile));
		this.xSize = 256;
		this.ySize = 256;
	}
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString("Fuel Burner", this.xSize / 2 - this.fontRendererObj.getStringWidth("Fuel Burner") / 2, 25, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 47, this.ySize - 114, 4210752);
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(40+(width - xSize) / 2, 14+(height - ySize) / 2, 0, 0, xSize, ySize);
	}
}
