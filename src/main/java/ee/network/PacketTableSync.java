package ee.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.tiles.TileEmc;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class PacketTableSync implements IMessage, IMessageHandler<PacketTableSync, IMessage>
{
	private double emc;
	private int x;
	private int y;
	private int z;
	
	public PacketTableSync() {}
	
	public PacketTableSync(double emc, int x, int y, int z) 
	{
		this.emc = emc;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public IMessage onMessage(PacketTableSync pkt, MessageContext ctx) 
	{
		TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(pkt.x, pkt.y, pkt.z);
		if (tile instanceof TileEmc)
		{
			((TileEmc) tile).setEmcValue(pkt.emc);
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		emc = buf.readDouble();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(emc);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
}
