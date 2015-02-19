package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import ee.features.NameRegistry;

public class ItemDMHoe extends ItemEE{
	public ItemDMHoe()
    {
        super(NameRegistry.DMHoe, 6);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World,BlockPos pos,EnumFacing face, float par8, float par9, float par10)
    {
    	int par4 = pos.getX();
    	int par5 = pos.getY();
    	int par6 = pos.getZ();
    	int par7 = face.getIndex();
    	Block i1 = par3World.getBlockState(pos).getBlock();
        if (!par2EntityPlayer.canPlayerEdit(pos, face, par1ItemStack)||!(i1 == Blocks.grass || i1 == Blocks.dirt))
        {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World,pos,face, par8, par9, par10);
        }
        else
        {
        	if(par1ItemStack.getItemDamage() == 1)
        	{
        		int ox = par4 - 2;
        		int oz = par6 - 2;
        		for(int i = 0;i < 5;i++)
        		{
        			for(int j = 0;j < 5;j++)
        			{
        				useHoe(par1ItemStack,par2EntityPlayer,par3World,new BlockPos(ox + i,par5,oz + j),par7);
        			}
        		}
        		return true;
        	}
        	else
        	{
        		return useHoe(par1ItemStack, par2EntityPlayer, par3World,new BlockPos(par4,par5, par6), par7);
        	}
        }
    }

	private boolean useHoe(ItemStack par1ItemStack,EntityPlayer par2EntityPlayer, World par3World,BlockPos pos, int par7) {
		UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World,pos);

		if (MinecraftForge.EVENT_BUS.post(event))
		{
		    return false;
		}

		if (event.getResult() == event.getResult().ALLOW)
		{
		    return true;
		}
		int par4 = pos.getX();
		int par5 = pos.getY();
		int par6 = pos.getZ();
		Block i1 = par3World.getBlockState(pos).getBlock();
		Block b1 = par3World.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock();
		if(b1 != null && (b1.getMaterial() == Material.plants || b1.getMaterial() == Material.vine))
		{
			b1.dropBlockAsItem(par3World,new BlockPos(par4, par5 + 1, par6), par3World.getBlockState(new BlockPos(par4, par5 + 1, par6)), 0);
			par3World.setBlockToAir(new BlockPos(par4, par5 + 1, par6));
			par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 1.5F), (double)((float)par6 + 0.5F), b1.stepSound.getBreakSound(), (b1.stepSound.getVolume() + 1.0F) / 2.0F, b1.stepSound.getFrequency() * 0.8F);
		}
		boolean air = par3World.isAirBlock(new BlockPos(par4, par5 + 1, par6));

		if (par7 != 0 && air && (i1 == Blocks.grass || i1 == Blocks.dirt))
		{
		    Block block = Blocks.farmland;
		    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F);

		    if (par3World.isRemote)
		    {
		        return true;
		    }
		    else
		    {
		        par3World.setBlockState(pos, block.getDefaultState());
		        return true;
		    }
		}
		else
		{
		    return false;
		}
	}
}
