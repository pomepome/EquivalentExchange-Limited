package ee.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.EELimited;
import ee.features.EEProxy;
import ee.features.items.IChargeable;
import ee.features.items.IExtraFunction;
import ee.features.items.IProjectileShooter;
import ee.features.items.ItemRing;

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
			if(EELimited.Debug && is.getItemDamage() == 0)
			{
				EEProxy.chatToPlayer(player, "Activated!");
			}
			if(is != null && is.getItem() instanceof ItemRing)
			{
				((ItemRing)is.getItem()).onActivated(player,is);
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
