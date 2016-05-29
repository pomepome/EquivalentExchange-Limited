package ee.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.tiles.TileEntityAggregator;
import ee.util.EEProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class PacketAlchChestUpdate implements IMessage, IMessageHandler<PacketAlchChestUpdate, IMessage>
{
	public int x,y,z;
	public PacketAlchChestUpdate(){}
	public PacketAlchChestUpdate(int x,int y,int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(PacketAlchChestUpdate message, MessageContext ctx)
	{
		ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
