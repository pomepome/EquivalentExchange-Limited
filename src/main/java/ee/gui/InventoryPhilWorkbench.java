package ee.gui;

import ee.features.EELimited;
import ee.features.items.ItemPhilosophersStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryPhilWorkbench extends InventoryCrafting
{
	Container container;
	World world;
	
	public InventoryPhilWorkbench(Container c,World w)
	{
		super(c, 3, 3);
		container = c;
		world = w;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	private PhilData getData()
	{
		return ItemPhilosophersStone.getPhilData(new ItemStack(EELimited.Phil), world);
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		PhilData data = getData();
		if(data != null)
		{
			return data.inventory[slot];
		}
		return null;
	}

	public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_)
    {
        if (p_70463_1_ >= 0 && p_70463_1_ < 3)
        {
            int k = p_70463_1_ + p_70463_2_ * 3;
            return this.getStackInSlot(k);
        }
        else
        {
            return null;
        }
    }
	
	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		PhilData data = getData();
		if(data == null)
		{
			return null;
		}
		if(data.inventory[var1] != null)
        {
            ItemStack var3;
            if(data.inventory[var1].stackSize <= var2)
            {
            	var3 = data.inventory[var1];
            	data.inventory[var1] = null;
            	this.markDirty();
            	container.onCraftMatrixChanged(this);
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
                container.onCraftMatrixChanged(this);
                return var3;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		PhilData data = getData();
		if(data != null)
		{
			ItemStack stack = data.inventory[slot];
			data.inventory[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		PhilData data = getData();
		if(data != null)
		{
			data.inventory[slot] = stack;
		}
		container.onCraftMatrixChanged(this);
	}

	@Override
	public String getInventoryName() {
		return "PhilWork";
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
		getData().markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}

}
