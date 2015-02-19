package ee.features.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.NameRegistry;

public class ItemCovalenceDust extends ItemEE
{
    IIcon mid;
    IIcon high;

    public ItemCovalenceDust()
    {
        super(NameRegistry.Cov + "low");
        this.setHasSubtypes(true).setMaxDamage(0);
    }

    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItemDamage() == 2 ? "item.Cov_high" : par1ItemStack.getItemDamage() == 1 ? "item.Cov_mid" : "item.Cov_low";
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        if (par1 == 0)
        {
            return this.itemIcon;
        }

        if (par1 == 1)
        {
            return this.mid;
        }

        return this.high;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        mid = par1IconRegister.registerIcon("ee:Cov_mid");
        high = par1IconRegister.registerIcon("ee:Cov_High");
    }
}
