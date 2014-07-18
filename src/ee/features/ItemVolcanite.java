package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVolcanite extends ItemFunction {

	public ItemVolcanite(int id) {
		super(id,INameRegistry.Volcanite);
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}

	public void doVaporize(ItemStack var1, World var2, EntityPlayer var3,int range)
    {
        boolean var4 = false;

        if(range %2 == 0)
        {
        	range++;
        }

        int count = (range + 1) / 2;

        int ox = (int)var3.posX - count;
        int oy = (int)var3.posY - count;
        int oz = (int)var3.posZ - count;

        for(int i = 0;i < range;i++)
        {
        	for(int j = 0;j < range;j++)
        	{
        		for(int k = 0;k < range;k++)
        		{
        			int nx = ox + i;
        			int ny = oy + j;
        			int nz = oz + k;
        			if(var2.getBlockMaterial(nx, ny, nz) == Material.water)
        			{
        				var4 = true;
        				var2.setBlockWithNotify(nx, ny, nz, 0);
        			}
        		}
        	}
        }
        if(var4)
        {
        	EEProxy.playSoundAtPlayer("random.fizz", var3, 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(var3.isSneaking())
		{
			doVaporize(var1,var2,var3,21);
		}
		return var1;
    }
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlockId(par4, par5, par6) != 0)
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

            if (!(par3World.getBlockMaterial(par4, par5, par6) == Material.lava||par3World.getBlockMaterial(par4, par5, par6)==Material.water)&&!par3World.isAirBlock(par4, par5, par6))
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
            if (Block.lavaMoving.canPlaceBlockAt(par3World, par4, par5, par6))
            {
                par3World.setBlockWithNotify(par4, par5, par6, Block.lavaMoving.blockID);
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
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}

	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,
			int z, int blockId) {
	}
}
