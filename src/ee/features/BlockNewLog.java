package ee.features;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNewLog extends BlockLog {

	public BlockNewLog(int par1) {
		super(par1);
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		super.breakBlock(par1World,par2,par3,par4,par5,par6);
		if(EELimited.instance.getPlayer().getCurrentEquippedItem().itemID == EELimited.IDAxe)
		{
			ItemStack is = EELimited.instance.getPlayer().getCurrentEquippedItem();
			int x = par2;
			int y = par3;
			int z = par4;
			EntityPlayer p = EELimited.instance.getPlayer();
			if(is.getItemDamage() > 0)
			{
				for(int i = y + 1;i < 256;i++)
				{
					if(p.worldObj.getBlockId(x,i,z) == 0||p.worldObj.getBlockMaterial(x, i, z) != Material.wood)
					{
						break;
					}
					this.dropBlockAsItem(p.worldObj,x,i,z,p.worldObj.getBlockMetadata(x, i, z),0);
					p.worldObj.setBlockWithNotify(x, i, z, 0);
				}
			}
		}
	}


}
