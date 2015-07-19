package ee.features.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import ee.features.Constants;
import ee.features.EEProxy;
import ee.features.items.ItemKleinStar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEMCCharger extends TileEmc implements IInventory,ISidedInventory
{
	public TileEMCCharger()
	{
		super(Constants.COLLECTOR_MK1_MAX);
	}
	ItemStack content;
	@Override
	public boolean isRequestingEmc()
	{
		return content != null && EEProxy.getEMC(content) != EEProxy.getMaxEMC(content);
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return content;
	}

	@Override
	public ItemStack decrStackSize(int slot, int qnt)
	{
		ItemStack stack = content;

		if (stack != null)
		{
			if (stack.stackSize <= qnt)
			{
				content = null;
			}
			else
			{
				stack = stack.splitStack(qnt);

				if (stack.stackSize == 0)
				{
					content = null;
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (content != null)
		{
			ItemStack stack = content;
			content = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		content = p_70299_2_;
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "EMCCharger";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		Item item = p_94041_2_.getItem();
		if(item != null && item instanceof ItemKleinStar)
		{
			return true;
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return isItemValidForSlot(slot,item);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return content != null && EEProxy.getEMC(content) >= EEProxy.getMaxEMC(content);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.setEmcValue(nbt.getDouble("EMC"));
		ItemStack is = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		if(is != null)
		{
			content = is;
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setDouble("EMC", this.getStoredEmc());
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(content != null)
		{
			content.writeToNBT(nbtItem);
		}
		nbt.setTag("Item",nbtItem);
	}
	public void updateEntity()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			return;
		}
		if(content == null)
		{
			return;
		}
		if(!(content.getItem() instanceof ItemKleinStar))
		{
			ItemStack is = content.copy();
			EEProxy.spawnEntityItem(worldObj, is, xCoord, yCoord + 1, zCoord);
			setInventorySlotContents(0,null);
			return;
		}
		if(getStoredEmc() > 0 && content.getItem() instanceof ItemKleinStar)
		{
			int stored = EEProxy.getEMC(content);
			int toSend = (int)getStoredEmc();
			if(toSend + stored > EEProxy.getMaxEMC(content))
			{
				toSend = EEProxy.getMaxEMC(content) - stored;
			}
			EEProxy.setEMC(content, stored + toSend);
			this.removeEmc(toSend);
		}
	}
}
