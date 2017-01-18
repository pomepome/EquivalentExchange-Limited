package ee.features.tiles;

import ee.features.Constants;

public class TileEMCCollectorMk3 extends TileEmcProducer
{
	public TileEMCCollectorMk3()
	{
		super(Constants.COLLECTOR_MK3_MAX);
	}
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		 this.addEmc(Math.round(getSunLevel() * 10)/10);
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
			int toSend = Math.min(30,getStoredEmc());
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
	@Override
	public int getSendingEmc()
	{
		return (int)getStoredEmc();
	}
}
