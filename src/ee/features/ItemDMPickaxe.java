package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemDMPickaxe extends ItemEE
{
    public boolean skip = false;

    public ItemDMPickaxe(int par1)
    {
        super(par1, NameRegistry.DMPickaxe, 9);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.blockMaterial == Material.rock ? (int)(10 * (stack.getItemDamage() + 0.2)) : 2.5F;
    }
    public boolean canHarvestBlock(Block par1Block)
    {
        return true;
    }
    public String getMaterialName(Material m)
    {
        if (m == m.air)
        {
            return "air";
        }

        if (m == m.anvil)
        {
            return "anvil";
        }

        if (m == m.cactus)
        {
            return "cactus";
        }

        if (m == m.cake)
        {
            return "cake";
        }

        if (m == m.circuits)
        {
            return "circuits";
        }

        if (m == m.clay)
        {
            return "clay";
        }

        if (m == m.cloth)
        {
            return "cloth";
        }

        if (m == m.coral)
        {
            return "coral";
        }

        if (m == m.craftedSnow)
        {
            return "craftedSnow";
        }

        if (m == m.dragonEgg)
        {
            return "dragonEgg";
        }

        if (m == m.fire)
        {
            return "fire";
        }

        if (m == m.glass)
        {
            return "glass";
        }

        if (m == m.grass)
        {
            return "grass";
        }

        if (m == m.ground)
        {
            return "ground";
        }

        if (m == m.ice)
        {
            return "ice";
        }

        if (m == m.iron)
        {
            return "iron";
        }

        if (m == m.lava)
        {
            return "lava";
        }

        if (m == m.leaves)
        {
            return "leaves";
        }

        if (m == m.materialCarpet)
        {
            return "materialCarpet";
        }

        if (m == m.piston)
        {
            return "piston";
        }

        if (m == m.plants)
        {
            return "plants";
        }

        if (m == m.portal)
        {
            return "portal";
        }

        if (m == m.pumpkin)
        {
            return "pumpkin";
        }

        if (m == m.redstoneLight)
        {
            return "redstoneLight";
        }

        if (m == m.rock)
        {
            return "rock";
        }

        if (m == m.sand)
        {
            return "sand";
        }

        if (m == m.snow)
        {
            return "snow";
        }

        if (m == m.sponge)
        {
            return "sponge";
        }

        if (m == m.tnt)
        {
            return "tnt";
        }

        if (m == m.vine)
        {
            return "vine";
        }

        if (m == m.water)
        {
            return "water";
        }

        if (m == m.web)
        {
            return "web";
        }

        if (m == m.wood)
        {
            return "wood";
        }

        return "unknown";
    }
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        Block block = Block.blocksList[par3World.getBlockId(par4, par5, par6)];
        int metadata = par3World.getBlockMetadata(par4, par5, par6);
        skip = !skip;

        if (skip)
        {
            return true;
        }

        if (EELimited.Debug)
        {
            EEProxy.getPlayer().sendChatMessage("" + MinecraftForge.getBlockHarvestLevel(block, metadata, "pickaxe"));
            EEProxy.getPlayer().sendChatMessage(getMaterialName((Block.blocksList[par3World.getBlockId(par4, par5, par6)].blockMaterial)));
        }

        if (par5 > 0 && par1ItemStack.getItemDamage() > 0 && Block.blocksList[par3World.getBlockId(par4, par5, par6)].getBlockHardness(par3World, par4, par5, par6) < 0)
        {
            Block.blocksList[par3World.getBlockId(par4, par5, par6)].dropBlockAsItem(par3World, par4, par5, par6, par3World.getBlockMetadata(par4, par5, par6), 0);
            par3World.setBlock(par4, par5, par6, 0);
            par1ItemStack.setItemDamage(1);
            return true;
        }

        /*
        if(par5 > 0 && par1ItemStack.getItemDamage() > 0)
        {
        	int blockID = par3World.getBlockId(par4, par5, par6);
        	if(Block.blocksList[blockID] == null || Block.blocksList[blockID].blockMaterial != Material.rock)
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
        					if(par3World.getBlockId(nx, par5, nz) == blockID&&ny == par5)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, par5, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						par3World.setBlock(nx, par5, nz, 0);
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
        					if(par3World.getBlockId(nx, par5, nz) == blockID && ny == par5)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, par5, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						par3World.setBlock(nx, par5, nz, 0);
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
        					if(par3World.getBlockId(nx, ny, par6) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, par6, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, par6, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, par6, 0);
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
        					if(par3World.getBlockId(nx, ny, par6) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, nx, ny, par6, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, nx, ny, par6, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(nx, ny, par6, 0);
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
        					if(par3World.getBlockId(par4, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, par4, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, par4, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(par4, ny, nz, 0);
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
        					if(par3World.getBlockId(par4, ny, nz) == blockID)
        					{
        						Block.blocksList[blockID].dropBlockAsItem(par3World, par4, ny, nz, par3World.getBlockMetadata(nx, ny, nz), 0);
        						Block.blocksList[blockID].breakBlock(par3World, par4, ny, nz, par3World.getBlockMetadata(nx, ny, nz),0);
        						par3World.setBlock(par4, ny, nz, 0);
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