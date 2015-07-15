package ee.features.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.NameRegistry;
import ee.features.entity.EntityWaterProjectile;

public class ItemEvertide extends ItemChargeable implements IProjectileShooter {
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par3Entity instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)par3Entity;

            if (p.getAir() < 300)
            {
                p.setAir(300);
            }
        }
    }

    public ItemEvertide()
    {
        super(NameRegistry.Ever,8);
        this.setMaxStackSize(1).setContainerItem(this);
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
            if (Blocks.water.canPlaceBlockAt(par3World, par4, par5, par6))
            {
                par3World.setBlock(par4, par5, par6, Blocks.water);
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
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityWaterProjectile(player.worldObj, player));
		player.worldObj.playSoundAtEntity(player, "ee:items.transmute",1.0F,1.0F);
		return true;
	}
}
