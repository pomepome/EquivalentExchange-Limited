package ee.features.tiles;

import ee.network.PacketHandler;
import ee.network.PacketOrientationSync;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileDirection extends TileEntity
{
	protected ForgeDirection orientation;
	public TileDirection()
	{
		this.orientation = ForgeDirection.SOUTH;
	}

	public ForgeDirection getOrientation()
	{
		return orientation;
	}

	public void setOrientation(ForgeDirection orientation)
	{
		this.orientation = orientation;
	}

	public void setOrientation(int orientation)
	{
		this.orientation = ForgeDirection.getOrientation(orientation);
	}

	public static int getRelativeOrientation(EntityLivingBase entity)
	{
		int direction = 0;
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH.ordinal();
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST.ordinal();
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH.ordinal();
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST.ordinal();
		}

		return direction;
	}

	public void setRelativeOrientation(EntityLivingBase ent, boolean sendPacket)
	{
		int direction = getRelativeOrientation(ent);

		setOrientation(direction);

		if (sendPacket)
		{
			PacketHandler.sendToAll(new PacketOrientationSync(this, direction));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		if (nbtTagCompound.hasKey("Direction"))
		{
			this.orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte("Direction"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setByte("Direction", (byte) orientation.ordinal());
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.func_148857_g());
	}
}
