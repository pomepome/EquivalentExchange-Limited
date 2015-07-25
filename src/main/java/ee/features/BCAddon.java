package ee.features;

import buildcraft.BuildCraftCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BCAddon
{
	public static void load(EELimited mod)
	{
		ItemStack woodEng = mod.gs(BuildCraftCore.engineBlock);
		ItemStack woodGear = mod.gs(BuildCraftCore.woodenGearItem);
		mod.addRecipe(mod.gs(mod.RFConverter),"GCG"," D ","GWG",'G',woodGear,'C',mod.EMCCollector,'D',Items.diamond);
	}
}
