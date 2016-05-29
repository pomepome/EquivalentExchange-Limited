package ee.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.opengl.GL11;

import ee.features.tiles.TileEntityAggregator;
import ee.gui.container.ContainerAggregator;
import ee.util.AggregatorRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiAggregator extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("ee","textures/gui/aggregator.png");

	private TileEntityAggregator tile;

	public GuiAggregator(TileEntityAggregator tile,EntityPlayer p)
	{
		super(new ContainerAggregator(tile,p));
		this.tile = tile;
	}
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
		String s = StatCollector.translateToLocal("container.aggregator");
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        {
        	// draw progress bar
        	int i1 = this.tile.getProcessScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
		{
			// draw light level
			int sunLevel = tile.getSunLevelScaled(14);
			int s = 14 - sunLevel;
    		this.drawTexturedModalRect(k + 56, l + 36 + 12 - 13 + s, 176, 12 - 14 + s, 14, sunLevel);
		}
    	double multiplier = tile.getMultiplierLight();
		double d = (multiplier * AggregatorRegistry.get(tile.getStackInSlot(4)));
		String s = "multiplier:"+ getFormattedValue(d,3) + "x";
        fontRendererObj.drawString(s, k + 80, l + 65, 0x404040);
	}
	private double getFormattedValue(double val,int index)
	{
		int i1 = 1;
		for(int i = 0;i < index;i++)
		{
			i1 *= 10;
		}
		return (double)((int)(val * i1)) / i1;
	}
}
