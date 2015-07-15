package ee.features.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.NameRegistry;

public class ItemKleinStar extends ItemEE
{
	private IIcon[] icons = new IIcon[6];
	private int[] maxEMCs = new int[]{50000,200000,800000,3200000,12800000,51200000};
	public ItemKleinStar()
	{
		super(NameRegistry.Klein);
		this.setHasSubtypes(true).setMaxStackSize(1).setMaxDamage(0).setNoRepair();
	}
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return "item.Klein_"+par1ItemStack.getItemDamage();
    }
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for(int i = 0;i < 6;i++)
		{
			par3List.add(new ItemStack(this,1,i));
		}
    }
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for(int i = 0;i < 6;i++)
		{
			icons[i] = par1IconRegister.registerIcon("ee:Klein"+i);
		}
    }
	/**
     * Determines if the durability bar should be rendered for this item.
     * Defaults to vanilla stack.isDamaged behavior.
     * But modders can use this for any data they wish.
     *
     * @param stack The current Item Stack
     * @return True if it should render the 'durability' bar.
     */
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    /**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	NBTTagCompound nbt = stack.getTagCompound();
    	int damage  = stack.getItemDamage();
    	if(nbt == null)
    	{
    		nbt = new NBTTagCompound();
    		nbt.setInteger("EMC",0);
    		nbt.setInteger("MaxEMC",getMaxEMC(damage));
    		stack.setTagCompound(nbt);
    	}
    	if(nbt.getInteger("EMC") > nbt.getInteger("MaxEMC"))
    	{
    		nbt.setInteger("EMC",nbt.getInteger("MaxEMC"));
    	}
    	return 1 - ((double)nbt.getInteger("EMC") / (double)nbt.getInteger("MaxEMC"));
    }
	public int getMaxEMC(int damage)
	{
		return maxEMCs[damage];
	}
	public IIcon getIconFromDamage(int par1)
    {
		int meta = MathHelper.clamp_int(par1,0,5);
		return icons[meta];
    }
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		NBTTagCompound nbt = itemStack.getTagCompound();
		if(nbt != null)
		{
			list.add("EMC:"+nbt.getInteger("EMC"));
			list.add("MaxEMC:"+nbt.getInteger("MaxEMC"));
		}
	}
}
