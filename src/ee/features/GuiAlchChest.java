package ee.features;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

public class GuiAlchChest extends GuiContainer {

	private IInventory chestInventory;
    private int inventoryRows = 0;

    public GuiAlchChest(IInventory var1, IInventory var2, boolean var3)
    {
        super(new ContainerAlchChest(var2, var1, var3));
        this.chestInventory = var2;
        this.allowUserInput = false;
        this.inventoryRows = var2.getSizeInventory() / 13;
        this.ySize = 230;
        this.xSize = 255;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer() {}

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        int var4 = this.mc.renderEngine.getTexture("/ee/aprites/alchest.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.inventoryRows * 18 + 5);
        this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 5, 0, 149, this.xSize, 81);
    }

}
