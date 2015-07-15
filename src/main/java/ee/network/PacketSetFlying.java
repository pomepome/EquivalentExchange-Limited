package ee.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSetFlying implements IMessageHandler<PacketSetFlying, IMessage>, IMessage
{
	boolean flag;
	public PacketSetFlying(){}
	public PacketSetFlying(boolean f){flag = f;}
	@Override
	public void fromBytes(ByteBuf buf)
	{
		flag = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(flag);
	}

	@Override
	public IMessage onMessage(PacketSetFlying message, MessageContext ctx)
	{
		Minecraft.getMinecraft().thePlayer.capabilities.allowFlying = message.flag;

		if (!flag)
		{
			Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
		}
		return null;
	}

}
