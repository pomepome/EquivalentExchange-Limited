package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMShovel extends ItemEE
{
    public ItemDMShovel(int par1)
    {
        super(par1, NameRegistry.DMShovel, 9);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return (block.blockMaterial == Material.ground || block.blockMaterial == Material.clay || block.blockMaterial == Material.grass || block.blockMaterial == block.gravel.blockMaterial) ? 10 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
    }
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par5 > 0 && par1ItemStack.getItemDamage() > 0 && par3World.getBlockId(par4, par5, par6) == Block.bedrock.blockID)
        {
            Block.blocksList[par3World.getBlockId(par4, par5, par6)].dropBlockAsItem(par3World, par4, par5, par6, par3World.getBlockMetadata(par4, par5, par6), 0);
            par3World.setBlock(par4, par5, par6, 0);
            par1ItemStack.setItemDamage(1);
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }

        /*
        if(par5 > 0 && par1ItemStack.getItemDamage() > 0)
        {
        	int blockID = par3World.getBlockId(par4, par5, par6);
        	Block block = Block.blocksList[blockID];
        	if(block == null || (block.blockMaterial != Material.ground &&block.blockMaterial != block.gravel.blockMaterial&& block.blockMaterial != Material.clay && block.blockMaterial != Material.grass))
        	{
        		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        	}
        	if(EELimited.Debug)
        	{
        		EEProxy.getPlayer().addChatMessage(EEProxy.getSide(par2EntityPlayer, par4, par5, par6).name());
        	}
        	switch(EEProxy.getSide(par2EntityPlayer, par4, par5, par6))
        	{
        		case UP:
        		{
        			int ox = par4 - 1;
        			int oy = par5;
        			int oz = par6 - 1;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox + i;
        					int ny = oy;
        					int nz = oz + j;
        					if(par3World.getBlockId(nx, ny, nz) == blockID&&ny == par5)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		case DOWN:
        		{
        			int ox = par4 - 1;
        			int oy = par5;
        			int oz = par6 - 1;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox + j;
        					int ny = oy;
        					int nz = oz + i;
        					if(par3World.getBlockId(nx, ny, nz) == blockID && ny == par5)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		case NORTH:
        		{
        			int ox = par4 - 1;
        			int oy = par5 - 1;
        			int oz = par6;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox + i;
        					int ny = oy + j;
        					int nz = oz;
        					if(par3World.getBlockId(nx, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		case SOUTH:
        		{
        			int ox = par4 - 1;
        			int oy = par5 - 1;
        			int oz = par6;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox + i;
        					int ny = oy + j;
        					int nz = oz;
        					if(par3World.getBlockId(nx, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		case EAST:
        		{
        			int ox = par4;
        			int oy = par5 - 1;
        			int oz = par6 - 1;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox;
        					int ny = oy + i;
        					int nz = oz + j;
        					if(par3World.getBlockId(nx, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		case WEST:
        		{
        			int ox = par4;
        			int oy = par5 - 1;
        			int oz = par6 - 1;
        			for(int i = 0;i < 3;i++)
        			{
        				for(int j = 0;j < 3;j++)
        				{
        					int nx = ox;
        					int ny = oy + i;
        					int nz = oz + j;
        					if(par3World.getBlockId(nx, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, nz, 0);
        					}
        				}
        			}
        		}
        		default:break;
        	}
        	return true;
        }
        */
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}
