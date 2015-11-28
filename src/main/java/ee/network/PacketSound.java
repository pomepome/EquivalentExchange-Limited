package ee.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.util.EEProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSound implements IMessage, IMessageHandler<PacketSound, IMessage>
{
	float volume,pitch;
	
	public PacketSound(){}
	public PacketSound(float soundVolume,float soundPitch)
	{
		volume = soundVolume;
		pitch = soundPitch;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
	}

	@Override
	public IMessage onMessage(PacketSound message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		EEProxy.playSoundAtPlayer("random.break", p, message.volume, message.pitch);
		return null;
	}

}
