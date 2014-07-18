package ee.features;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemFunction extends ItemEE {

	boolean skip = false;
	boolean isActivated = false;
	int count;
	double cost;
	String name;

	public ItemFunction(int id, String name) {
		super(id, name);
		this.setMaxStackSize(1).setContainerItem(this);
		this.name = name;
	}
	public void setCost(double f)
	{
		cost = f;
	}
	@Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }
	public int getIconFromDamage(int par1)
    {
		if(par1 == 1&&this.getHasSubtypes())
		{
			return this.iconIndex + 1;
		}
        return this.iconIndex;
    }
	@Override
	public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		doPassive(par2World,(EntityPlayer)par2World.playerEntities.get(0),par1ItemStack);
	}
	public void decreaseItem(int itemID,int i)
	{

	}
	public int getItemCount(int itemID)
	{
		ItemStack[] inv = EELimited.getMod().getPlayer().inventory.mainInventory;
		int ret = 0;
		for(int i = 0;i < inv.length;i++)
		{
			if(inv[i]!=null&&inv[i].itemID == itemID)
			{
				ret += inv[i].stackSize;
			}
		}
		return ret;
	}
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		onActivate(var1);
		return var1;
    }
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		onActivate(par1ItemStack);
		return true;
    }
	public void onActivate(ItemStack is)
	{
		skip = !skip;
		if(skip)
		{
			return;
		}
		if(is.getMaxDamage() > 0||is.getHasSubtypes())
		{
			is.setItemDamage(1 - is.getItemDamage());
		}
	}
	public abstract void onDestroyedLog(EntityPlayer p,ItemStack is,int x,int y,int z,int blockId);
	public abstract void doPassive(World world,EntityPlayer player,ItemStack is);
	public abstract void doPassive(World world,EntityPlayer player,ItemStack is,int d);

}
