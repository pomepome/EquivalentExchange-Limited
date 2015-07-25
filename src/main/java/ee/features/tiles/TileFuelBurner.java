package ee.features.tiles;

import ee.features.EELimited;
import ee.features.blocks.emc.BlockFuelBurner;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileFuelBurner extends TileEmcProducer implements IInventory
{
	ItemStack inventory;
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		if(inventory == null)
		{
			updateEmc();
			return;
		}
		if(EELimited.instance.getFuelValue(inventory) == 0)
		{
			return;
		}
		 this.addEmc(EELimited.instance.getFuelValue(inventory));
		 inventory.stackSize--;
		 if(inventory.stackSize <= 0)
		 {
			 inventory = null;
		 }
		 updateEmc();
		 markDirty();
	}
	public void updateEmc()
	{
		this.checkSurroundingBlocks(false);
		int numRequest = this.getNumRequesting();
		if (this.getStoredEmc() == 0)
		{
			return;
		}
		if (numRequest > 0 && !this.isRequestingEmc())
		{
			double toSend = Math.min(100,getStoredEmc());
			this.sendEmcToRequesting(toSend / numRequest);
			this.removeEmc(toSend);
		}
		BlockFuelBurner b = (BlockFuelBurner)getBlock();
		b.updateFurnaceBlockState(isBurning(), worldObj, xCoord, yCoord, zCoord);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		ItemStack is = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		if(is != null)
		{
			inventory = is;
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(inventory != null)
		{
			inventory.writeToNBT(nbtItem);
		}
		nbt.setTag("Item",nbtItem);
	}
	public Block getBlock()
	{
		return worldObj.getBlock(xCoord, yCoord, zCoord);
	}
	@Override
	public boolean isRequestingEmc()
	{
		return false;
	}
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	@Override
	public ItemStack getStackInSlot(int p_70301_1_)
	{
		return inventory;
	}
	@Override
	public ItemStack decrStackSize(int slot, int qnt)
	{
		ItemStack stack = inventory;

		if (stack != null)
		{
			if (stack.stackSize <= qnt)
			{
				stack = null;
			}
			else
			{
				stack = stack.splitStack(qnt);

				if (stack.stackSize == 0)
				{
					stack = null;
				}
			}
		}

		return stack;
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		ItemStack content = inventory;
		if (content != null)
		{
			ItemStack stack = content;
			content = null;
			return stack;
		}
		return null;
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack item)
	{
		inventory = item;
	}
	@Override
	public String getInventoryName()
	{
		return "Fuel Burner";
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
		return EELimited.instance.getFuelValue(p_94041_2_) > 0;
	}
	public boolean isBurning()
	{
		return getStoredEmc() > 0;
	}
}
