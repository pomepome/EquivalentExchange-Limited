package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EELimited;
import ee.features.NameRegistry;


public class ItemDMAxe extends ItemEETool
{
    public ItemDMAxe()
    {
        super(NameRegistry.DMAxe, 6);
    }

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
    public boolean isWood(World w,int x,int y,int z)
    {
        return isWood(w.getBlock(x, y, z));
    }
    public boolean isLeaves(Block b)
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
        return w.getBlock(x, y, z);
    }
    public void breakBlock(World w, int x, int y, int z)
    {
        Block b = w.getBlock(x, y, z);

        if (b != null)
        {
            b.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
            b.breakBlock(w, x, y, z, b, 0);
            w.setBlock(x, y, z, Blocks.air);
        }
    }
    public void breakWood(World w,int x,int y,int z)
    {
    	for(int dx = -3;dx < 4;dx++)
    	{
    		for(int dz = -3;dz < 4;dz++)
    		{
    			if(isWood(w,x + dx,y,z + dz))
    			{
    				breakBlock(w,x + dx,y,z + dz);
    			}
    		}
    	}
    }
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        //EEProxy.mc.thePlayer.sendChatMessage(EEProxy.getSide(par2EntityPlayer, par4, par5, par6).name());;
        if (getChargeLevel(par1ItemStack) > 0 && isWood(par3World.getBlock(par4, par5, par6)))
        {
            if (EELimited.cutDown)
            {
                for (; isWood(par3World.getBlock(par4, par5, par6)); par5--) {}

                par5++;
            }

            int ox = par4 - 3;
            int oz = par5 - 3;

            for (int y = par5; y < 256; y++)
            {
                if (!isWood(par3World.getBlock(par4, y, par6)))
                {
                    break;
                }

                breakWood(par3World, par4, y, par6);
            }
        }
        else
        {
            par1ItemStack.setItemDamage(1 - par1ItemStack.getItemDamage());
        }

        return true;
    }
    @Override
    public float getDigSpeed(ItemStack stack, Block block,int metadata)
    {
        return block.getMaterial() == Material.wood ? 20 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
    }
}