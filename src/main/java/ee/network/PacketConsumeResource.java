package ee.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.EEProxy;

public class PacketConsumeResource implements IMessage,IMessageHandler<PacketConsumeResource,IMessage>
{
	private int amount;

	public PacketConsumeResource(int amount)
	{
		this.amount = amount;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(amount);
	}

	@Override
	public IMessage onMessage(PacketConsumeResource message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		EEProxy.useResource(player, message.amount,true);
		return null;
	}
}