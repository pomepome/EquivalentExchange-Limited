package ee.wailacompat;

import java.util.List;
import java.util.Set;

import ee.features.tiles.TileEMCCharger;
import ee.features.tiles.TileEmc;
import ee.features.tiles.TileEmcProducer;
import ee.util.EEProxy;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HudEMCMachine implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		try
	    {
			int emc = -1;
			int maxEmc = -1;
			emc = WailaAddon.emc.getInt((TileEmc)te);
			maxEmc = WailaAddon.maxEmc.getInt((TileEmc)te);
			if(te instanceof TileEMCCharger)
			{
				ItemStack stack = (ItemStack)WailaAddon.content.get((TileEMCCharger)te);
				if(stack != null)
				{
					NBTTagCompound subNBT = new NBTTagCompound();
					stack.writeToNBT(subNBT);
					tag.setTag("content", subNBT);
				}
			}
			tag.setInteger("emc", emc);
			tag.setInteger("maxEmc", maxEmc);
	    }
	    catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> defaulttip,IWailaDataAccessor dataAccesor, IWailaConfigHandler confHandler)
	{
		TileEntity tile = dataAccesor.getTileEntity();
		int emc = -1;
		int maxEmc = -1;
		NBTTagCompound nbt = dataAccesor.getNBTData();
		ItemStack content = null;
		if(nbt.hasKey("content"))
		{
			content = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("content"));
		}
		emc = nbt.getInteger("emc");
		maxEmc = nbt.getInteger("maxEmc");
		if(tile instanceof TileEmc)
		{
			TileEmc machine = (TileEmc)tile;
			defaulttip.add(String.format("Stored EMC: %d/%d", emc, maxEmc));
			if(machine instanceof TileEMCCharger)
			{
				if(content == null)
				{
					defaulttip.add("Content:NULL");
				}
				else
				{
					defaulttip.add("Content:"+content.getDisplayName());
					defaulttip.add(String.format("Klein EMC: %d/%d",EEProxy.getEMC(content),EEProxy.getMaxEMC(content)));
				}
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
