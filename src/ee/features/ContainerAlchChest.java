package ee.features;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAlchChest extends Container {

    private boolean freeRoaming;
    private IInventory lowerChestInventory;
    private int numRows;

    public ContainerAlchChest(IInventory var1, IInventory var2, boolean var3)
    {
        this.freeRoaming = !var3;
        this.lowerChestInventory = var1;
        this.lowerChestInventory.openChest();
        this.numRows = var1.getSizeInventory() / 13;
        int var4;
        int var5;

        for (var4 = 0; var4 < this.numRows; ++var4)
        {
            for (var5 = 0; var5 < 13; ++var5)
            {
                this.addSlotToContainer(new Slot(var1, var5 + var4 * 13, 12 + var5 * 18, 5 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(var2, var5 + var4 * 9 + 9, 48 + var5 * 18, 152 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(var2, var4, 48 + var4 * 18, 210));
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.freeRoaming ? true : this.lowerChestInventory.isUseableByPlayer(var1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int var1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(var1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (var1 < this.numRows * 13)
            {
                if (!this.mergeItemStack(var4, this.numRows * 13, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, this.numRows * 13, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);
        this.lowerChestInventory.closeChest();
    }

}
