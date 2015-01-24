package ee.features.items;

import java.util.List;

import ee.features.EELimited;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemPhilToolGT extends ItemPhilToolBase {
	public void getSubItems(Item item, CreativeTabs p_150895_2_, List list)
	{
	  list.add(new ItemStack(item, 1, 0));//Smelting
	  list.add(new ItemStack(item, 1, 1));//Crowbar
	  list.add(new ItemStack(item, 1, 2));//Crowbar
	  list.add(new ItemStack(item, 1, 3));//Crowbar
	  list.add(new ItemStack(item, 1, 4));//Crowbar
	  list.add(new ItemStack(item, 1, 5));//Crowbar
	}
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(!var3.isSneaking())
		{
			int damage = var1.getItemDamage();
			ItemStack is = var1.copy();
			var3.destroyCurrentEquippedItem();
			is.setItemDamage((is.getItemDamage() + 1) % 6);
			return is;
		}
		return new ItemStack(EELimited.Phil);
    }
	public String getUnlocalizedName(ItemStack is)
	{
		int damage = is.getItemDamage();
		return "item.PhilTool_"+names[damage];
	}
}
