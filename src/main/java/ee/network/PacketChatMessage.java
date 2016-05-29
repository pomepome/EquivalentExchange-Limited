package ee.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.util.EEProxy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.player.EntityPlayer;

public class PacketChatMessage implements IMessage, IMessageHandler<PacketChatMessage, IMessage>
{
	public String message;

	public PacketChatMessage(){}
	public PacketChatMessage(String mes)
	{
		message = mes;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		message = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, message);
	}

	@Override
	public IMessage onMessage(PacketChatMessage message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		EEProxy.chatToPlayer(p, message.message);
		return null;
	}

}
