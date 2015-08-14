package ee.features.items;

import java.util.List;

import codechicken.microblock.Saw;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.items.interfaces.IModeChange;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Optional.Interface(modid = "ForgeMultipart",iface="codechicken.microblock.Saw")
public class ItemPhilToolFMP extends ItemPhilToolBase implements Saw,IModeChange
{
	String[] names = {"smelt","saw"};
	public ItemPhilToolFMP()
	{
		super("FMP");
	}
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs p_150895_2_, List list)
	{
	  list.add(new ItemStack(item, 1, 0));//Smelting
	  list.add(new ItemStack(item, 1, 1));//Saw
	}
	@Override
	public int getCuttingStrength(ItemStack arg0) {
		return arg0.getItemDamage() == 1 ? 5 : -1;
	}
	@Override
	public int getMaxCuttingStrength() {
		return 5;
	}
	@Override
	public void onActivated(EntityPlayer player, ItemStack is)
	{
		ItemStack id = is.copy();
		int damage = is.getItemDamage();
		id.setItemDamage((damage + 1) % 2);
		player.setCurrentItemOrArmor(0, id);
	}
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(var3.isSneaking())
		{
			return new ItemStack(EELimited.Phil);
		}
		return var1;
    }
	public String getUnlocalizedName(ItemStack is)
	{
		int damage = is.getItemDamage();
		return "item.PhilTool_"+names[damage];
	}
}
