package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ee.features.EEProxy;
import ee.features.NameRegistry;

public class ItemVolcanite extends ItemEEFunctional {

	public ItemVolcanite() {
		super(NameRegistry.Volc);
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

                    if (var2.getBlockState(new BlockPos(nx,ny,nz)).getBlock().getMaterial() == Material.water)
                    {
                        var4 = true;
                        var2.setBlockToAir(new BlockPos(nx,ny,nz));
                    }
                }
            }
        }
        if (var4)
        {
            EEProxy.playSoundAtPlayer("random.fizz", var3, 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }
    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.isSneaking())
        {
            doVaporize(var1, var2, var3, 21);
        }

        return var1;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumFacing par7, float par8, float par9, float par10)
    {
    	BlockPos pos1 = pos.offset(par7);
    	Block b = par3World.getBlockState(pos1).getBlock();
        if (!par3World.isAirBlock(pos))
        {

            if (!(b.getMaterial() == Material.lava || b.getMaterial() == Material.water) && !par3World.isAirBlock(pos1))
            {
                return false;
            }
        }

        if (!par2EntityPlayer.canPlayerEdit(pos1, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (Blocks.lava.canPlaceBlockAt(par3World, pos1))
            {
            	IBlockState state = Blocks.lava.getDefaultState();
                par3World.setBlockState(pos1,state);
                state.getBlock().onNeighborBlockChange(par3World, pos1,state,Blocks.air);
            }

            return true;
        }
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
