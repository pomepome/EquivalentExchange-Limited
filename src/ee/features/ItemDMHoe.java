package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemDMHoe extends ItemEE
{
    public ItemDMHoe(int ID)
    {
        super(ID, NameRegistry.DMHoe, 6);
        this.setMaxDamage(200);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	int i1 = par3World.getBlockId(par4, par5, par6);
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)||!(i1 == Block.grass.blockID || i1 == Block.dirt.blockID))
        {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
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
        				useHoe(par1ItemStack,par2EntityPlayer,par3World,ox + i,par5,oz + j,par7);
        			}
        		}
        		return true;
        	}
        	else
        	{
        		return useHoe(par1ItemStack, par2EntityPlayer, par3World, par4,par5, par6, par7);
        	}
        }
    }

	private boolean useHoe(ItemStack par1ItemStack,EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,int par6, int par7) {
		UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);

		if (MinecraftForge.EVENT_BUS.post(event))
		{
		    return false;
		}

		if (event.getResult() == Result.ALLOW)
		{
		    return true;
		}

		int i1 = par3World.getBlockId(par4, par5, par6);
		int i2 = par3World.getBlockId(par4, par5 + 1, par6);
		Block b1 = Block.blocksList[i2];
		if(b1 != null && (b1.blockMaterial == Material.plants || b1.blockMaterial == Material.vine))
		{
			b1.dropBlockAsItem(par3World, par4, par5 + 1, par6, par3World.getBlockMetadata(par4, par5 + 1, par6), 0);
			par3World.setBlock(par4, par5 + 1, par6, 0);
			par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 1.5F), (double)((float)par6 + 0.5F), b1.stepSound.getBreakSound(), (b1.stepSound.getVolume() + 1.0F) / 2.0F, b1.stepSound.getPitch() * 0.8F);
		}
		boolean air = par3World.isAirBlock(par4, par5 + 1, par6);

		if (par7 != 0 && air && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID))
		{
		    Block block = Block.tilledField;
		    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

		    if (par3World.isRemote)
		    {
		        return true;
		    }
		    else
		    {
		        par3World.setBlock(par4, par5, par6, block.blockID);
		        return true;
		    }
		}
		else
		{
		    return false;
		}
	}
}
