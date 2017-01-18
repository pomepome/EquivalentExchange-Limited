package ee.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.features.tiles.DMPedestalTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PacketPedestalSync implements IMessage, IMessageHandler<PacketPedestalSync, IMessage>
{
	public int x, y, z;
	public boolean isActive;
	public ItemStack itemStack;

	public PacketPedestalSync(){}
	public PacketPedestalSync(DMPedestalTile tile)
	{
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		isActive = tile.getActive();
		itemStack = tile.getItemStack();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		isActive = buf.readBoolean();
		itemStack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(isActive);
		ByteBufUtils.writeItemStack(buf, itemStack);
	}

	@Override
	public IMessage onMessage(PacketPedestalSync message, MessageContext ctx)
	{
		TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);

		if (te instanceof DMPedestalTile)
		{
			DMPedestalTile pedestal = ((DMPedestalTile) te);
			pedestal.setActive(message.isActive);
			pedestal.setInventorySlotContents(0, message.itemStack);
		}

		return null;
	}

}
