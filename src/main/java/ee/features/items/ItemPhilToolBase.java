package ee.features.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.items.interfaces.IChargeable;
import ee.features.items.interfaces.IModeChange;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

public class ItemPhilToolBase extends ItemEEFunctional implements IChargeable,IModeChange
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
	@Override
	public void onActivated(EntityPlayer player, ItemStack is)
	{
	}
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(var3.isSneaking())
		{
			return new ItemStack(EELimited.Phil);
		}
		return var1;
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if (!world.isRemote)
		{
    		if(stack.getItemDamage() == 0)
    		{
    			Block b = world.getBlock(x, y, z);
    			int meta = world.getBlockMetadata(x, y, z);
    			ItemStack iss = new ItemStack(b, 1, meta);
    			ItemStack isd = FurnaceRecipes.smelting().getSmeltingResult(iss);
    			if(isd == null)
    			{
    				return true;
    			}
    			isd = isd.copy();
    			if(isd.getItem() instanceof ItemBlock)
    			{
    				ItemBlock ib = (ItemBlock)isd.getItem();
    				world.setBlock(x, y, z, ib.field_150939_a);
    			}
    			else
    			{
    				world.setBlock(x, y, z, Blocks.air);
    				EntityItem ei = new EntityItem(world, x, y, z, isd);
    				world.spawnEntityInWorld(ei);
    			}
    		}
		}
    	return true;
    }
	@Override
	public void changeCharge(EntityPlayer player, ItemStack stack)
	{
		player.setCurrentItemOrArmor(0, EELimited.gs(EELimited.Phil));
	}
	@Override
	public int getChargeLevel(ItemStack is)
	{
		return 0;
	}
}
