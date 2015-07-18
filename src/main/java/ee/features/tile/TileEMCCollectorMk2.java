package ee.features.tile;

import ee.features.Constants;
import net.minecraft.nbt.NBTTagCompound;

public class TileEMCCollectorMk2 extends TileEmcProducer
{
	public TileEMCCollectorMk2()
	{
		super(Constants.COLLECTOR_MK2_MAX);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.setEmcValue(nbt.getDouble("EMC"));
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setDouble("EMC", this.getStoredEmc());
	}
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		 this.addEmc(Math.round(getSunLevel() * 7)/10);
		 updateEmc();
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
			double toSend = Math.min(5,getStoredEmc());
			this.sendEmcToRequesting(toSend / numRequest);
			this.removeEmc(toSend);
		}
	}
	protected int getSunLevel()
	{
		return worldObj.getBlockLightValue(xCoord, yCoord + 1, zCoord);
	}
	@Override
	public boolean isRequestingEmc() {
		return false;
	}
}
