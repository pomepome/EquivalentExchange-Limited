package ee.features.tiles;

import cpw.mods.fml.common.network.NetworkRegistry;
import ee.features.Constants;
import ee.features.ITileEmc;
import ee.network.PacketHandler;
import ee.network.PacketTableSync;
import ee.util.EEProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEmc extends TileEntity implements ITileEmc
{
	private int EMC;
	private final int maxAmount;

	public TileEmc()
	{
		maxAmount = Constants.TILE_MAX_EMC;
	}

	public TileEmc(int maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	@Override
	public void setEmc(int value)
	{
		this.EMC = value <= maxAmount ? value : maxAmount;
	}

	@Override
	public void addEmc(int amount)
	{
		EMC += amount;

		if (EMC > maxAmount)
		{
			EMC = maxAmount;
		}
		else if (EMC < 0)
		{
			EMC = 0;
		}
		this.markDirty();
	}

	public void addEmcWithPKT(int amount)
	{
		addEmc(amount);

		sendUpdatePKT();
	}

	public void addEmc(ItemStack stack)
	{
		addEmc(EEProxy.getEMC(stack) * stack.stackSize);
	}

	@Override
	public void removeEmc(int amount)
	{
		EMC -= amount;

		if (EMC < 0)
		{
			EMC = 0;
		}
		this.markDirty();
	}

	public void removeEmcWithPKT(int amount)
	{
		removeEmc(amount);

		sendUpdatePKT();
	}

	public void removeItemRelativeEmc(ItemStack stack)
	{
		removeEmc(EEProxy.getEMC(stack));
	}

	public void removeItemRelativeEmcWithPKT(ItemStack stack)
	{
		removeItemRelativeEmc(stack);

		sendUpdatePKT();
	}

	@Override
	public int getStoredEmc()
	{
		return EMC;
	}

	public int getMaxEmc()
	{
		return maxAmount;
	}

	@Override
	public boolean hasMaxedEmc()
	{
		return EMC >= maxAmount;
	}

	public void setEmcValue(int value)
	{
		EMC = value;
		this.markDirty();
	}

	public void setEmcValueWithPKT(int value)
	{
		setEmcValue(value);

		sendUpdatePKT();
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.setEmcValue(nbt.getInteger("EMC"));
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("EMC", this.getStoredEmc());
	}
	public void sendUpdatePKT()
	{
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			PacketHandler.sendToAllAround(new PacketTableSync(EMC, this.xCoord, this.yCoord, this.zCoord),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
		}
	}
	public boolean isRequestingEmc()
	{
		return getStoredEmc() < getMaxEmc();
	}
}