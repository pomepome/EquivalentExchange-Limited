package ee.features.tiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.addons.bc.BCAddon;
import ee.features.EELimited;
import ee.util.EEProxy;
import ee.util.LocusRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLocus extends TileDirection implements ISidedInventory
{
	ItemStack[] inventory;
	public int procTime,currentBurnTime;

	private static final Logger log = LogManager.getLogger("EELimited|Locus");

	public TileEntityLocus()
	{
		inventory = new ItemStack[11];
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(!canProcess())
		{
			currentBurnTime = getBurnTime();
		}
		else
		{
			if(worldObj.isRemote)
			{
				return;
			}
			currentBurnTime++;
			if(currentBurnTime >= getBurnTime())
			{
				currentBurnTime = 0;
				procTime += LocusRegistry.getFuelValue(inventory[8]);
				inventory[8].stackSize--;
				if(inventory[8].stackSize == 0)
				{
					inventory[8] = null;
				}
			}
			if(procTime >= LocusRegistry.isDestValid(inventory[9]))
			{
				insertOutput();
				procTime = 0;
			}
		}
		/*
		if(inventory[0] == null)
		{
			sortInventory();
		}
		*/
		gatherFuel();
		if(EELimited.autoEject)
		{
			putOutput();
		}
		markDirty();
	}
	private int getBurnTime()
	{
		return 10;
	}
	public int getProcessScaled(int size)
    {
		if(inventory[9] == null)
		{
			return 0;
		}
        return (int)(this.procTime * size) / LocusRegistry.isDestValid(inventory[9]);
    }
	public int getBurnTimeScaled(int scale)
	{
		return currentBurnTime * scale / getBurnTime();
	}
	public void sortInventory()
	{
		ItemStack[] sorted = EEProxy.sort(getInputCopy(),false);
		for(int i = 0;i < 9;i++)
		{
			inventory[i] = null;
			if(i < sorted.length)
			{
				inventory[i] = sorted[i];
			}
		}
	}
	public ItemStack getFirstFuel()
	{
		for(int i = 0;i < 8;i++)
		{
			if(inventory[i] != null)
			{
				ItemStack stack = inventory[i].copy();
				inventory[i] = null;
				return stack;
			}
		}
		return null;
	}
	public void gatherFuel()
	{
		if(inventory[8] != null)
		{
			return;
		}
		ItemStack toReplace = getFirstFuel();
		if(toReplace == null)
		{
			return;
		}
		inventory[8] = toReplace;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			byte slot = subNBT.getByte("Slot");

			if (slot >= 0 && slot < getSizeInventory())
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(subNBT);
			}
		}
		procTime = nbt.getInteger("procTime");
		currentBurnTime = nbt.getInteger("current");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("procTime", procTime);
		nbt.setInteger("current", currentBurnTime);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (inventory[i] == null)
			{
				continue;
			}

			NBTTagCompound subNBT = new NBTTagCompound();
			subNBT.setByte("Slot", (byte) i);
			inventory[i].writeToNBT(subNBT);
			list.appendTag(subNBT);
		}

		nbt.setTag("Items", list);
	}
	public void putOutput()
	{
		if(inventory[10] == null)
		{
			return;
		}
		for(int i = 2;i < 6;i++)
		{
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			int x,y,z;
			x = xCoord + dir.offsetX;
			y = yCoord + dir.offsetY;
			z = zCoord + dir.offsetZ;
			TileEntity tile = worldObj.getTileEntity(x, y, z);
			if(tile == null)
			{
				continue;
			}
			if(EELimited.loadBC && BCAddon.isPipe(tile))
			{
				if(worldObj.getBlockMetadata(x, y, z) == EEProxy.getOpSide(dir).ordinal())
				{
					continue;
				}
				if(EELimited.Debug)
				{
					log.info("Pipe found : "+dir.name());
				}
				for(int j = 10;i < 11;i++)
				{
					if(inventory[j] != null)
					{
						BCAddon.putItemInPipe(tile, inventory[j].copy(), EEProxy.getOpSide(dir).ordinal());
						inventory[j] = null;
					}
				}
			}
			else if(tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
				for(int j = 10;i < 11;i++)
				{
					if(inventory[j] != null)
					{
						ItemStack stack = EEProxy.pushStackInInv(inv, inventory[j]);
						inventory[j] = stack;
					}
				}
			}
		}
	}
	public boolean canProcess()
	{
		if(inventory[8] != null && inventory[9] != null)
		{
			return EEProxy.pushStacksInInv(EEProxy.copyStacks(inventory[10]), false, EEProxy.normalizeStack(inventory[9]));
		}
		return false;
	}
	public void insertOutput()
	{
		ItemStack[] inserted = EEProxy.copyStacks(inventory[10]);
		EEProxy.pushStackInInv(inserted, EEProxy.normalizeStack(inventory[9]));
		inventory[10] = inserted[0];
	}
	private boolean process()
	{
		if(!canProcess())
		{
			return false;
		}
		inventory[8].stackSize--;
		if(inventory[8].stackSize <= 0)
		{
			inventory[8] = null;
		}
		insertOutput();
		return true;
	}
	private ItemStack[] getInputCopy()
	{
		return EEProxy.copyStacks(inventory[0],inventory[1],
									inventory[2],inventory[3],
									inventory[4],inventory[5],
									inventory[6],inventory[7],
									inventory[8]);
	}
	@Override
	public int getSizeInventory() {
		return 11;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = inventory[slot];
		if(stack != null)
		{
			if(stack.stackSize <= amount)
			{
				inventory[slot] = null;
			}
			else
			{
				stack = stack.splitStack(amount);
				if(stack.stackSize == 0)
				{
					inventory[slot] = null;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if(inventory[slot] != null)
		{
			ItemStack is = inventory[slot];
			inventory[slot] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack content)
	{
		inventory[slot] = content;
	}

	@Override
	public String getInventoryName()
	{
		return "locus";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
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
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return LocusRegistry.getFuelValue(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == 0 || side == 1)
		{
			return new int[]{0,1,2,3,4,5,6,7,8};
		}
		return new int[]{10};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return side == 0 || side == 1;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return side > 1;
	}

}
