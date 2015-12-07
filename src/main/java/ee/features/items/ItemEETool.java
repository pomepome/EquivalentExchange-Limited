package ee.features.items;

import java.util.List;

import com.google.common.collect.Lists;

import ee.network.PacketHandler;
import ee.network.PacketSound;
import ee.network.PacketSpawnParticle;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEETool extends ItemChargeable
{
	public ItemEETool(String name, int damage)
	{
		super(name,1,damage);
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
    public boolean isLeaves(World w,int x,int y,int z)
    {
        return isLeaves(w.getBlock(x, y, z));
    }
    public boolean isLeaves(Block b)
    {
        if (b == null)
        {
            return false;
        }

        String name = b.getUnlocalizedName();
        return name.toLowerCase().contains("leaves");
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
    	breakBlock(w,x,y,z);
    }
	protected void deforestAOE(World world, ItemStack stack, EntityPlayer player, int emcCost)
	{
		byte charge = (byte)getChargeLevel(stack);
		boolean flag = false;
		if (charge == 0 || world.isRemote)
		{
			return;
		}

		List<ItemStack> drops = Lists.newArrayList();

		for (int x = (int) player.posX - (5 * charge); x <= player.posX + (5 * charge); x++)
		{
			for (int y = (int) player.posY - (10 * charge); y <= player.posY + (10 * charge); y++)
			{
				for (int z = (int) player.posZ - (5 * charge); z <= player.posZ + (5 * charge); z++)
				{
					Block block = world.getBlock(x, y, z);

					if (block == Blocks.air)
					{
						continue;
					}
					if (isWood(block) || isLeaves(block))
					{
						if(!EEProxy.useResource(player, emcCost, true))
						{
							break;
						}
						PacketHandler.sendToAll(new PacketSpawnParticle("largesmoke",x + 0.5,y + 0.5,z + 0.5));
						breakBlock(world,x,y,z);
						flag = true;
					}
				}
			}
		}
		if(flag)
		{
			PacketHandler.sendToServer(new PacketSound(EnumSounds.CHARGE,1.0f,1.0f));
		}
		player.swingItem();
	}
}
