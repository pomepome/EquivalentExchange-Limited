package ee.features.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import ee.features.Constants;
import ee.features.EEProxy;
import ee.features.ITileEmc;
import ee.network.PacketHandler;
import ee.network.PacketTableSync;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEmc extends TileEntity implements ITileEmc
{
	private double emc;
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
	public void setEmc(double value)
	{
		this.emc = value <= maxAmount ? value : maxAmount;
	}

	@Override
	public void addEmc(double amount)
	{
		emc += amount;

		if (emc > maxAmount)
		{
			emc = maxAmount;
		}
		else if (emc < 0)
		{
			emc = 0;
		}
		this.markDirty();
	}

	public void addEmcWithPKT(double amount)
	{
		addEmc(amount);

		sendUpdatePKT();
	}

	public void addEmc(ItemStack stack)
	{
		addEmc(EEProxy.getEMC(stack) * stack.stackSize);
	}

	@Override
	public void removeEmc(double amount)
	{
		emc -= amount;

		if (emc < 0)
		{
			emc = 0;
		}
		this.markDirty();
	}

	public void removeEmcWithPKT(double amount)
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
	public double getStoredEmc()
	{
		return emc;
	}

	public int getMaxEmc()
	{
		return maxAmount;
	}

	@Override
	public boolean hasMaxedEmc()
	{
		return emc >= maxAmount;
	}

	public void setEmcValue(double value)
	{
		emc = value;
		this.markDirty();
	}

	public void setEmcValueWithPKT(double value)
	{
		setEmcValue(value);

		sendUpdatePKT();
	}

	public void sendUpdatePKT()
	{
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			PacketHandler.sendToAllAround(new PacketTableSync(emc, this.xCoord, this.yCoord, this.zCoord),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
		}
	}
}