package ee.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.blocks.TileDirection;

public class PacketOrientationSync implements IMessage,IMessageHandler<PacketOrientationSync,IMessage>
{
	private int orientation,x,y,z;
	public PacketOrientationSync(){}
	public PacketOrientationSync(TileEntity tile,int ori)
	{
		orientation = ori;
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		orientation = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(orientation);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(PacketOrientationSync message, MessageContext ctx) {
		TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tile instanceof TileDirection)
		{
			((TileDirection) tile).setOrientation(message.orientation);
		}
		return null;
	}

}
