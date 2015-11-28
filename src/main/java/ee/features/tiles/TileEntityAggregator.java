package ee.features.tiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.addins.bc.BCAddon;
import ee.features.EELimited;
import ee.util.AggregatorRegistry;
import ee.util.EEProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAggregator extends TileDirection implements ISidedInventory
{
	ItemStack[] inventory;
	public double progress;

	private static final Logger log = LogManager.getLogger("EELimited|Aggregator");

	public TileEntityAggregator()
	{
		inventory = new ItemStack[10];
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
		progress = nbt.getDouble("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setDouble("progress", progress);

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
				for(int j = 5;i < 10;i++)
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
				for(int j = 5;i < 10;i++)
				{
					IInventory inv = (IInventory)tile;
					if(inventory[j] != null)
					{
						ItemStack stack = EEProxy.pushStackInInv(inv, inventory[j].copy());
						inventory[j] = stack;
					}
				}
			}
		}
	}
	public ItemStack getFirstFuel()
	{
		for(int i = 0;i < 4;i++)
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
		if(inventory[4] != null)
		{
			return;
		}
		ItemStack toReplace = getFirstFuel();
		if(toReplace == null)
		{
			return;
		}
		inventory[4] = toReplace;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(!canProcess())
		{
			progress = 0;
		}
		else
		{
			if(worldObj.isRemote)
			{
				return;
			}
			double multiplier = getMultiplierLight();
			progress += AggregatorRegistry.get(inventory[4]) * multiplier;
			if(progress >= getWorkTime())
			{
				progress = 0;
				process();
			}
		}
		gatherFuel();
		if(EELimited.autoEject)
		{
			putOutput();
		}
		markDirty();
	}
	public int getProcessScaled(int size)
    {
        return (int)(this.progress * size) / getWorkTime();
    }
	public int getSunLevelScaled(int scale)
	{
		return (int)(this.getMultiplierLight() * scale);
	}
	private int getWorkTime()
	{
		return 2400;
	}
	public boolean canProcess()
	{
		double multiplier = getMultiplierLight() * AggregatorRegistry.get(inventory[4]);
		if(inventory[4] != null && multiplier > 0)
		{
			return EEProxy.pushStacksInInv(getOutputCopy(), false, new ItemStack(Blocks.glowstone));
		}
		return false;
	}
	private boolean process()
	{
		if(!canProcess())
		{
			return false;
		}
		inventory[4].stackSize--;
		if(inventory[4].stackSize <= 0)
		{
			inventory[4] = null;
		}
		insertOutput();
		return true;
	}
	public double getMultiplierLight()
	{
		float i1 = worldObj.getSavedLightValue(EnumSkyBlock.Block, xCoord, yCoord + 1, zCoord);
		return (double)i1 / 15;
	}
	private ItemStack[] getInputCopy()
	{
		return EEProxy.copyStacks(inventory[0],inventory[1],inventory[2],inventory[3],inventory[4]);
	}
	private ItemStack[] getOutputCopy()
	{
		return EEProxy.copyStacks(inventory[5],inventory[6],inventory[7],inventory[8],inventory[9]);
	}
	private void insertOutput()
	{
		ItemStack[] inserted = EEProxy.copyStacks(getOutputCopy());
		EEProxy.pushStackInInv(inserted, new ItemStack(Blocks.glowstone));
		for(int i = 0;i < 5;i++)
		{
			inventory[5 + i] = inserted[i];
		}
	}
	private void sortInventory()
	{
		ItemStack[] sortedOutput = EEProxy.sort(getOutputCopy());
		for(int i = 0;i < 5;i++)
		{
			inventory[9 - i] = null;
			if(sortedOutput.length > i)
			{
				inventory[9 - i] = sortedOutput[i];
			}
		}
	}
	@Override
	public int getSizeInventory()
	{
		return 10;
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
		return "aggregatot";
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
		if(slot > 4 && slot <= 9)
		{
			return false;
		}
		return AggregatorRegistry.get(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == 0 || side == 1)
		{
			return new int[]{0,1,2,3,4};
		}
		return new int[]{5,6,7,8,9};
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
