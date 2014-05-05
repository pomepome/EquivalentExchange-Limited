package ee.features;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCov extends ItemNonFunction {

	public ItemCov(int id, String name) {
		super(id, name);
		this.setHasSubtypes(true).setMaxDamage(0);
	}

	public String getItemNameIS(ItemStack par1ItemStack)
    {
        switch(par1ItemStack.getItemDamage())
        {
        	case 0: return "cov.low";
        	case 1: return "cov.mid";
        	case 2: return "cov.high";
        }
        return "";
    }

	public int getIconFromDamage(int par1)
    {
		return IconManager.get(INameRegistry.covalanceLow) - par1;
    }

	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }

}
