package ee.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.items.ItemAlchemyBag;

public class InventoryAlchBag implements IInventory
{
	BagData data;
	public InventoryAlchBag(ItemStack stack,World w)
	{
		data = ItemAlchemyBag.getBagData(stack, w);
	}
	@Override
	public int getSizeInventory() {
		return data.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return data.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		if(data.inventory[var1] != null)
        {
            ItemStack var3;
            if(data.inventory[var1].stackSize <= var2)
            {
            	var3 = data.inventory[var1];
            	data.inventory[var1] = null;
            	this.markDirty();
            	return var3;
            }
            else
            {
            	var3 = data.inventory[var1].splitStack(var2);

            	if (data.inventory[var1].stackSize == 0)
                {
                    data.inventory[var1] = null;
                }

                this.markDirty();
                return var3;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack content)
	{
		data.inventory[index] = content;
	}

	@Override
	public String getInventoryName() {
		return "AlchBag";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty()
	{
		data.needUpdate = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
		markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack content)
	{
		return false;//return EELimited.cantPutAlchemyBag ? !(content.getItem() instanceof ItemAlchemyBag) : true;
	}

}
