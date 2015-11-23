package ee.features.tiles;

import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;
import ee.gui.BagData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityColoredAlchChest extends TileDirection implements ISidedInventory
{
	private ItemStack[] mainInventory = new ItemStack[105];
	private BagData bag;

	private int ticksSinceSync;
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	public int requiredEmc;

	@Override
	public int getSizeInventory() {
		return 105;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			byte slot = subNBT.getByte("Slot");

			if (slot >= 0 && slot < 105)
			{
				mainInventory[slot] = ItemStack.loadItemStackFromNBT(subNBT);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < 105; i++)
		{
			if (mainInventory[i] == null)
			{
				continue;
			}

			NBTTagCompound subNBT = new NBTTagCompound();
			subNBT.setByte("Slot", (byte) i);
			mainInventory[i].writeToNBT(subNBT);
			list.appendTag(subNBT);
		}

		nbt.setTag("Items", list);
	}
	public BagData getBagData()
	{
		if(mainInventory[104] != null)
		{
			return ItemAlchemyBag.getBagData(mainInventory[104], worldObj);
		}
		return null;
	}
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if(slot < 104)
		{
			return bag == null ? mainInventory[slot] : bag.inventory[slot];
		}
		else
		{
			return mainInventory[104];
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int ammount)
	{
		ItemStack stack = this.getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize <= ammount)
			{
				this.setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(ammount);
				if (stack.stackSize == 0)
				{
					this.setInventorySlotContents(slot, null);;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (mainInventory[slot] != null)
		{
			ItemStack stack = mainInventory[slot];
			mainInventory[slot] = null;
			return stack;
		}
		return null;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(bag == null)
		{
			bag = getBagData();
		}
		else
		{
			bag = getBagData();
			if(bag != null)
			{
				bag.markDirty();
			}
		}

		updateChest();
		markDirty();
	}
	private void updateChest() {
		if (++ticksSinceSync % 20 * 4 == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, EELimited.cAlchChest, 1, numPlayersUsing);
		}

		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;
		double adjustedXCoord, adjustedZCoord;

		if (numPlayersUsing > 0 && lidAngle == 0.0F)
		{
			adjustedXCoord = xCoord + 0.5D;
			adjustedZCoord = zCoord + 0.5D;
			worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;

			if (numPlayersUsing > 0)
			{
				lidAngle += angleIncrement;
			}
			else
			{
				lidAngle -= angleIncrement;
			}

			if (lidAngle > 1.0F)
			{
				lidAngle = 1.0F;
			}

			if (lidAngle < 0.5F && var8 >= 0.5F)
			{
				adjustedXCoord = xCoord + 0.5D;
				adjustedZCoord = zCoord + 0.5D;
				worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F)
			{
				lidAngle = 0.0F;
			}
		}
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if(slot == 104)
		{
			mainInventory[104] = stack;
		}
		else if(bag != null)
		{
			bag.inventory[slot] = stack;
		}
		else
		{
			mainInventory[slot] = stack;
		}
	}

	@Override
	public String getInventoryName()
	{
		return "coloredAlchChest";
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
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}
	@Override
	public boolean receiveClientEvent(int number, int arg)
	{
		if (number == 1)
		{
			numPlayersUsing = arg;

			return true;
		}
		else return super.receiveClientEvent(number, arg);
	}

	@Override
	public void openInventory()
	{
		++numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, EELimited.cAlchChest, 1, numPlayersUsing);
	}

	@Override
	public void closeInventory()
	{
		--numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, EELimited.cAlchChest, 1, numPlayersUsing);
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(slot == 104)
		{
			return stack.getItem() instanceof ItemAlchemyBag;
		}
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
	{
		int[] array = new int[104];
		for(int i = 0;i < array.length;i++)
		{
			array[i] = i;
		}
		return array;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack p_102007_2_, int side) {
		return slot < 104;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack p_102008_2_, int side) {
		return slot < 104;
	}

}
