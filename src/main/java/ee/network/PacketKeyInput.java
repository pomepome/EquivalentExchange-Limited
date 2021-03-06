package ee.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.items.interfaces.IChargeable;
import ee.features.items.interfaces.IExtraFunction;
import ee.features.items.interfaces.IModeChange;
import ee.features.items.interfaces.IProjectileShooter;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketKeyInput implements IMessage, IMessageHandler<PacketKeyInput,IMessage> {

	private int code;
	public PacketKeyInput(){}
	public PacketKeyInput(int key)
	{
		code = key;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		code = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(code);
	}

	@Override
	public IMessage onMessage(PacketKeyInput message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		ItemStack is = player.getHeldItem();
		if(is == null)
		{
			return null;
		}
		World w = player.worldObj;
		if(message.code == 0)
		{
			if(is != null && is.getItem() instanceof IModeChange)
			{
				if(is.getItemDamage() == 0)
				{
					EEProxy.playSoundAtPlayer(EnumSounds.HEAL.getPath(), player, 1.0f, 1.0f);
				}
				((IModeChange)is.getItem()).onActivated(player,is);
			}
		}
		if(message.code == 1)
		{
			boolean isSneaking = player.isSneaking();
			IChargeable item;
			if(is != null && is.getItem() instanceof IChargeable)
			{
				item = (IChargeable)is.getItem();
			}
			else
			{
				return null;
			}
			item.changeCharge(player, is);
		}
		if(message.code == 2)
		{
			if(is != null && is.getItem() instanceof IProjectileShooter)
			{
				((IProjectileShooter)is.getItem()).shootProcectile(player, is);
			}
		}
		if(message.code == 3)
		{
			if(is != null && is.getItem() instanceof IExtraFunction)
			{
				((IExtraFunction)is.getItem()).onExtraFunction(player, is);
			}
		}
		return null;
	}

}
