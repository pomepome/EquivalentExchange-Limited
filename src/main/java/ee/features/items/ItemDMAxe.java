package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.network.PacketHandler;
import ee.network.PacketSound;
import ee.util.EEProxy;
import ee.util.EnumSounds;


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
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float minX, float minY, float minZ)
    {
		System.out.println(world.getBlock(x, y, z).getUnlocalizedName());
        //EEProxy.mc.thePlayer.sendChatMessage(EEProxy.getSide(par2EntityPlayer, par4, par5, par6).name());;
    	if(getChargeLevel(stack) > 0 && (isWood(world,x,y,z) || isLeaves(world,x,y,z)))
    	{
    		deforestAOE(world,stack,player, 4);
    	}
    	
        return true;
    }
    public boolean onBlockDestroyed(ItemStack stack, World w, Block block, int x, int y, int z, EntityLivingBase destroyer)
    {
    	destroyWood(stack,w,x,y,z);
        return false;
    }
	private void destroyWood(ItemStack stack, World world, int x, int y, int z)
	{
		if (isWood(world.getBlock(x, y, z)))
        {
            if (EELimited.cutDown)
            {
                for (; isWood(world.getBlock(x, y, z)); y--) {}

                y++;
            }

            int dx = x - 3;
            int dz = y - 3;

            for (int i = y; i < 256; i++)
            {
                if (!isWood(world.getBlock(x, i, z)))
                {
                    break;
                }
                breakWood(world, x, i, z);
            }
        }
	}
    @Override
    public float getDigSpeed(ItemStack stack, Block block,int metadata)
    {
        return block.getMaterial() == Material.wood ? 10 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
    }
}