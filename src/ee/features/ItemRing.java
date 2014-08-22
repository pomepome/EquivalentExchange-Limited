package ee.features;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemRing extends ItemEE
{
    Icon On, Off;
    String texture = "";

    public ItemRing(int par1, String name)
    {
        super(par1, name);
        texture = name;
        this.setMaxDamage(0);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
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

    public Icon getIconFromDamage(int par1)
    {
        return par1 == 1 ? On : Off;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        On = par1IconRegister.registerIcon("ee:" + texture + "On");
        Off = par1IconRegister.registerIcon("ee:" + texture + "Off");
    }
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is);
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is, int d);
}
