package ee.features.tiles;

import ee.features.Constants;

public class TileEMCCollector extends TileEmcProducer
{
	public TileEMCCollector()
	{
		super(Constants.COLLECTOR_MK1_MAX);
	}
	@Override
	public boolean isRequestingEmc() {
		return false;
	}
	protected int getSunLevel()
	{
		return worldObj.getBlockLightValue(xCoord, yCoord + 1, zCoord);
	}
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		 this.addEmc(Math.round(getSunLevel() * 5)/10);
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
			int toSend = (int)Math.min(5,getStoredEmc());
			this.sendEmcToRequesting(toSend / numRequest);
			this.removeEmc(toSend);
		}
	}
	@Override
	public int getSendingEmc()
	{
		return (int) getStoredEmc();
	}
}
