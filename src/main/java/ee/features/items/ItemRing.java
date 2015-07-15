package ee.features.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EEProxy;

public abstract class ItemRing extends ItemEEFunctional
{
    IIcon On, Off;
    String texture = "";

    public ItemRing(String name)
    {
        super(name);
        texture = name;
        this.setMaxDamage(0).setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    @Override
    public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        doPassive(par2World, (EntityPlayer)par2World.playerEntities.get(0), par1ItemStack);

        if (par1ItemStack.getItemDamage() == 1)
        {
            doPassive(par2World, (EntityPlayer)par2World.playerEntities.get(0), par1ItemStack, 0);
        }
    }

    public IIcon getIconFromDamage(int par1)
    {
        return par1 == 1 ? On : Off;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        On = par1IconRegister.registerIcon("ee:" + texture + "On");
        Off = par1IconRegister.registerIcon("ee:" + texture + "Off");
    }
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is);
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is, int d);
    public void onActivated(EntityPlayer player,ItemStack is)
    {
    	int prevDamage = is.getItemDamage();
    	boolean flag = false;
    	if(useResource())
    	{
    		if(prevDamage == 0)
    		{
    			if(EEProxy.useResource(player,1,false))
    			{
    				flag = true;
    			}
    		}
    		else
    		{
    			flag = true;
    			if(is.getItem() instanceof ItemSwiftwolfsRing)
    			{
    				player.capabilities.allowFlying = false;
    				player.capabilities.isFlying = false;
    			}
    		}
    	}
    	else
    	{
    		flag = true;
    	}
    	if(flag)
    	{
    		is.setItemDamage(1 - prevDamage);
    	}
    }
    protected boolean useResource()
    {
    	return false;
    }
}