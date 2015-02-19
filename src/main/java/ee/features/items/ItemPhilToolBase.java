package ee.features.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.NameRegistry;

public class ItemPhilToolBase extends ItemEEFunctional {

	public ItemPhilToolBase() {
		super(NameRegistry.PhilTool);
	}
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack is)
    {
		return true;
    }
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(!var3.isSneaking())
		{
			int damage = var1.getItemDamage();
			ItemStack is = var1.copy();
			var3.destroyCurrentEquippedItem();
			is.setItemDamage((is.getItemDamage() + 1) % 1);
			return is;
		}
		return new ItemStack(EELimited.Phil);
    }
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs p_150895_2_, List list)
	{
	  list.add(new ItemStack(item, 1, 0));//Smelting
	}
	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		return "item.PhilTool_smelt";
	}
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
		return itemStack;
    }

}
