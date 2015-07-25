package ee.features.tiles;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFConverter extends TileEmc implements IEnergyHandler
{
	public TileRFConverter()
	{
		super(20000);
	}
	public int extractEnergy(int max, boolean doExtract)
	{
		int toExtract = Math.min(max, Math.min(getEnergyStored(), 200));
		if(doExtract)
		{
			removeEmc(toExtract);
		}
		return Math.min(max, toExtract);
	}
	public int getEnergyStored()
	{
		return (int)getStoredEmc();
	}
	public int getMaxEnergyStored() {
		return getMaxEmc();
	}
	public int receiveEnergy(int max, boolean doRecieve)
	{
		int last = (int) (getMaxEmc() - getStoredEmc());
		int toRecieve = Math.min(max, last);
		if(doRecieve)
		{
			addEmc(toRecieve);
		}
		return toRecieve;
	}
	@Override
	public boolean canConnectEnergy(ForgeDirection arg0) {
		return true;
	}
	@Override
	public int extractEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
		return extractEnergy(arg1,arg2);
	}
	@Override
	public int getEnergyStored(ForgeDirection arg0) {
		return getEnergyStored();
	}
	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		return getMaxEnergyStored();
	}
	@Override
	public int receiveEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
		return receiveEnergy(arg1,arg2);
	}
	public ForgeDirection getAgainstDir(int dir)
	{
		if(dir < 2)
		{
			return ForgeDirection.getOrientation(1 - dir);
		}
		if(dir == 2||dir == 4)
		{
			return ForgeDirection.getOrientation(6 - dir);
		}
		if(dir == 3||dir == 5)
		{
			return ForgeDirection.getOrientation(8 - dir);
		}
		return ForgeDirection.UNKNOWN;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		for(int i = 0;i < 6;i++)
		{
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			int x = xCoord + dir.offsetX;
			int y = yCoord + dir.offsetY;
			int z = zCoord + dir.offsetZ;
			TileEntity tile = worldObj.getTileEntity(x, y, z);
			if(tile != null && tile instanceof IEnergyHandler &&!(tile instanceof TileRFConverter))
			{
				int toExtract = this.extractEnergy(200,true);
				((IEnergyHandler)tile).receiveEnergy(getAgainstDir(i),toExtract,false);
			}
		}
	}
}
