package ee.features.items;

import ee.features.EEProxy;
import ee.features.NameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemVolcanite extends ItemEE
{
    public ItemVolcanite()
    {
        super(NameRegistry.Volc);
        this.setMaxStackSize(1).setContainerItem(this);
    }

    public void doVaporize(ItemStack var1, World var2, EntityPlayer var3, int range)
    {
        boolean var4 = false;

        if (range % 2 == 0)
        {
            range++;
        }

        int count = (range + 1) / 2;
        int ox = (int) var3.posX - count;
        int oy = (int) var3.posY - count;
        int oz = (int) var3.posZ - count;

        for (int i = 0; i < range; i++)
        {
            for (int j = 0; j < range; j++)
            {
                for (int k = 0; k < range; k++)
                {
                    int nx = ox + i;
                    int ny = oy + j;
                    int nz = oz + k;

                    if (var2.getBlock(nx, ny, nz).getMaterial() == Material.water)
                    {
                        var4 = true;
                        var2.setBlock(nx, ny, nz, Blocks.air);
                    }
                }
            }
        }
        if (var4)
        {
            EEProxy.playSoundAtPlayer("random.fizz", var3, 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.isSneaking())
        {
            doVaporize(var1, var2, var3, 21);
        }

        return var1;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlock(par4, par5, par6) != Blocks.air)
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }

            if (!(par3World.getBlock(par4, par5, par6).getMaterial() == Material.lava || par3World.getBlock(par4, par5, par6).getMaterial() == Material.water) && !par3World.isAirBlock(par4, par5, par6))
            {
                return false;
            }
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (Blocks.lava.canPlaceBlockAt(par3World, par4, par5, par6))
            {
                par3World.setBlock(par4, par5, par6, Blocks.lava);
                par3World.getBlock(par4, par5, par6).onNeighborBlockChange(par3World, par4, par5, par6, Blocks.air);
            }

            return true;
        }
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }
    @Override
    public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
    	if(par3Entity instanceof EntityPlayer)
    	{
    		EntityPlayer p = (EntityPlayer)par3Entity;
    		p.addPotionEffect(new PotionEffect(Potion.fireResistance.id,0));
    	}
    }
}
