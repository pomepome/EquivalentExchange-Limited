package ee.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.EELimited;

public class PacketSpawnParticle implements IMessage, IMessageHandler<PacketSpawnParticle, IMessage>
{
	private String particleName;
	private double x;
	private double y;
	private double z;
	private double velX;
	private double velY;
	private double velZ;

	//Needs to have an empty constructor
	public PacketSpawnParticle() {}

	public PacketSpawnParticle(String name, double x, double y, double z)
	{
		particleName = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public PacketSpawnParticle(String name, double x, double y, double z, double velX, double velY, double velZ)
	{
		this(name, x, y, z);
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
	}

	@Override
	public IMessage onMessage(PacketSpawnParticle message, MessageContext ctx)
	{
		EELimited.proxy.getEntityPlayerInstance().worldObj.spawnParticle(message.particleName, message.x, message.y, message.z, message.velX, message.velY, message.velZ);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		particleName = ByteBufUtils.readUTF8String(buffer);
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		velX = buffer.readDouble();
		velY = buffer.readDouble();
		velZ = buffer.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, particleName);
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(velX);
		buffer.writeDouble(velY);
		buffer.writeDouble(velZ);
	}
}
