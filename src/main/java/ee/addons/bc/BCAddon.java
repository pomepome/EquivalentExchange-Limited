package ee.addons.bc;

import buildcraft.BuildCraftCore;
import buildcraft.api.transport.IPipeTile;
import ee.features.EELimited;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class BCAddon
{
	public static void load(EELimited mod)
	{
		ItemStack woodEng = mod.gs(BuildCraftCore.engineBlock);
		ItemStack woodGear = mod.gs(BuildCraftCore.woodenGearItem);
	}
	public static boolean isPipe(TileEntity tile)
	{
		return tile instanceof IPipeTile;
	}
	public static void putItemInPipe(TileEntity tile,ItemStack stack,int side)
	{
		if(!isPipe(tile))
		{
			return;
		}
		((IPipeTile)tile).injectItem(stack, true, ForgeDirection.getOrientation(side),null);
	}
}
