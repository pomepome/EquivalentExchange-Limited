package ee.wailacompat;

import java.util.List;

import ee.features.tiles.TileEntityColoredAlchChest;
import ee.gui.BagData;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HudColoredChest implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile,NBTTagCompound nbt, World world, int a, int b, int c){return null;}

	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> defaulttip,IWailaDataAccessor dataAccesor, IWailaConfigHandler confHandler)
	{
		TileEntity tile = dataAccesor.getTileEntity();
		if(tile instanceof TileEntityColoredAlchChest)
		{
			TileEntityColoredAlchChest chest = (TileEntityColoredAlchChest)tile;
			BagData data = chest.getBagData();
			if(data == null)
			{
				defaulttip.add(SpecialChars.RESET + "Color filter:" + SpecialChars.OBF+"UNKNOWN");
			}
			else
			{
				String bagString = data.getDataName();
				String damage = bagString.split("_")[1];
				int col = Integer.parseInt(damage);
				defaulttip.add(SpecialChars.RESET + "Color filter:" + SpecialChars.GOLD + ColorUtil.getColorFromId(col).toUpperCase());
			}
		}
		return defaulttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> defaulttip,IWailaDataAccessor dataAccessor, IWailaConfigHandler confHandler){return defaulttip;}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor dataAccessor,IWailaConfigHandler confHandler) {return null;}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> defaulttip,IWailaDataAccessor dataAccesor, IWailaConfigHandler confHandler){return defaulttip;}

}
