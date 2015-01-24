package ee.features.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.NameRegistry;

public class ItemPhilToolBase extends ItemEEFunctional
{
	protected String[] names = {"smelt","crowbar","hhammer","screw","shammer","wrench"};
	public ItemPhilToolBase()
	{
		super(NameRegistry.PhilTool);
		this.setTextureName("ee:"+NameRegistry.Philo).setHasSubtypes(true);
	}
	public ItemPhilToolBase(String philtool)
	{
		super(NameRegistry.PhilTool+philtool);
		this.setTextureName("ee:"+NameRegistry.Philo).setHasSubtypes(true);
	}
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack is,int pass)
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
		int damage = is.getItemDamage();
		return "item.PhilTool_"+names[damage];
	}
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
		return itemStack;
    }
}
