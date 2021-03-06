package ee.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSound implements IMessage, IMessageHandler<PacketSound, IMessage>
{
	int sound;
	float volume,pitch;
	
	public PacketSound(){}
	public PacketSound(EnumSounds eSound,float soundVolume,float soundPitch)
	{
		volume = soundVolume;
		pitch = soundPitch;
		sound = eSound.getID();
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		sound = buf.readInt();
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(sound);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
	}

	@Override
	public IMessage onMessage(PacketSound message, MessageContext ctx)
	{
		EEProxy.playSoundAtPlayer(EnumSounds.getFromID(message.sound).getPath(), ctx.getServerHandler().playerEntity, message.volume, message.pitch);
		return null;
	}

}
