package ee.features.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FoodStats;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ee.features.EELimited;

public class EEProxy {
	public static final int MAXWORLDHEIGHT = 256;
    private static boolean initialized;
    private static EELimited ee;

    public static void Init(EELimited var1)
    {
        if (!initialized)
        {
            initialized = true;
        }
        ee = var1;
    }
    @SideOnly(Side.CLIENT)
    public static EntityPlayerSP getPlayer()
    {
        return getMC().thePlayer;
    }

    public static Minecraft getMC()
    {
    	return FMLClientHandler.instance().getClient();
    }

    public static boolean isClient(World var0)
    {
        return var0.isRemote;
    }

    public static boolean isServer()
    {
        return false;
    }

    public static Object getTileEntity(IBlockAccess var0, int var1, int var2, int var3, Class var4)
    {
        if (var2 < 0)
        {
            return null;
        }
        else
        {
            TileEntity var5 = var0.getTileEntity(new BlockPos(var1, var2, var3));
            return !var4.isInstance(var5) ? null : var5;
        }
    }

    public static boolean isEntityFireImmune(Entity var0)
    {
        return var0.isImmuneToFire();
    }

    public static int getEntityHealth(EntityLivingBase var0)
    {
        return (int)var0.getHealth();
    }

    public static FoodStats getFoodStats(EntityPlayer var0)
    {
        return var0.getFoodStats();
    }

    public static WorldInfo getWorldInfo(World var0)
    {
        return var0.getWorldInfo();
    }

    public static int getMaxStackSize(Item var0)
    {
        return var0.getItemStackLimit(new ItemStack(var0));
    }

    public static int blockDamageDropped(Block var0, IBlockState var1)
    {
        return var0.damageDropped(var1);
    }

    public static void dropBlockAsItemStack(Block var0, EntityLiving var1, int var2, int var3, int var4, IBlockState var5)
    {
        var0.dropBlockAsItem(var1.worldObj,new BlockPos(var2, var3, var4), var5, 0);
    }

    public static boolean isJumping(EntityPlayer var0)
    {
        return getMC().gameSettings.keyBindJump.isPressed();
    }

    public static void playSound(String var0, float var1, float var2, float var3, float var4, float var5)
    {
       getMC().theWorld.playSound(var1,var2,var3,var0, var4, var5,false);
    }

    public static void playSoundAtPlayer(String var0, EntityPlayer var1, float var2, float var3)
    {
        playSound(var0, (float)var1.posX, (float)var1.posY, (float)var1.posZ, var2, var3);
    }
}
