package assets.ee.models.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.items.ItemEEFunctional;

public class ItemDMAxe extends ItemEEFunctional
{
	public ItemDMAxe()
	{
		super(NameRegistry.DMAxe, 6);
	}
	@Override
	public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block.getMaterial() != Material.rock;
	}
	public boolean isWood(Block b)
	{
		if (b == null)
		{
			return false;
		}

		String name = b.getUnlocalizedName();
		return name.toLowerCase().contains("wood") || name.toLowerCase().contains("log");
	}
	public boolean isLeave(Block b)
	{
		if (b == null)
		{
			return false;
		}

		String name = b.getUnlocalizedName();
		return name.toLowerCase().contains("leave");
	}
	public Block getBlock(World w, int x, int y, int z)
	{
		return w.getBlockState(new BlockPos(x, y, z)).getBlock();
	}
	public void breakBlock(World w, int x, int y, int z)
	{
		Block b = getBlock(w,x, y, z);

		if (b != null)
    	{
        	b.dropBlockAsItem(w,new BlockPos(x, y, z), w.getBlockState(new BlockPos(x, y, z)), 0);
        	b.breakBlock(w,new BlockPos(x, y, z), w.getBlockState(new BlockPos(x, y, z)));
        	w.setBlockToAir(new BlockPos(x, y, z));
    	}
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, int par7, float par8, float par9, float par10)
	{
		int par4 = pos.getX();
		int par5 = pos.getY();
		int par6 = pos.getZ();
    	//EEProxy.mc.thePlayer.sendChatMessage(EEProxy.getSide(par2EntityPlayer, par4, par5, par6).name());;
    	if (par1ItemStack.getItemDamage() > 0 && isWood(getBlock(par3World,par4, par5, par6)))
    	{
        	if (EELimited.cutDown)
        	{
        		for (; isWood(getBlock(par3World,par4, par5, par6)); par5--) {}
            	par5++;
        	}

        	int ox = par4 - 3;
        	int oz = par5 - 3;

        	for (int i = par5; i < 256; i++)
        	{
            	if (!isWood(getBlock(par3World,par4, i, par6)))
            	{
                	break;
            	}

            	breakBlock(par3World, par4, i, par6);
            	/*
            	for(int x = 0;x < 7;x++)
            	{
            	for(int z = 0;z < 7;z++)
            	{
            		int blockID = getBlockId(par3World,ox + x,i,oz + z);
            		if(isWood(blockID)||isLeave(blockID))
            		{
            			breakBlock(par3World,ox + x,i,oz + z);
            		}
            	}
            	}
            	*/
        	}
    	}
    	else
    	{
        	par1ItemStack.setItemDamage(1 - par1ItemStack.getItemDamage());
    	}

    	return true;
	}
	@Override
	public float getDigSpeed(ItemStack stack,IBlockState state)
	{
		Block block = state.getBlock();
    	return block.getMaterial() == Material.wood ? 20 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
	}
}