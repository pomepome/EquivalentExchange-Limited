package ee.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.addons.extrautil.AddonExtraUtilities;
import ee.features.EELimited;
import ee.util.EEProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		p.capabilities.allowFlying = message.flag;

		if (!flag)
		{
			p.capabilities.isFlying = false;
		}
		return null;
	}

}
