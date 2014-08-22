package ee.features;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EEProxy
{
    public static final int MAXWORLDHEIGHT = 256;
    private static boolean initialized;
    public static Minecraft mc;
    private static Object ee;

    public static void Init(Minecraft var0, Object var1)
    {
        if (!initialized)
        {
            initialized = true;
        }

        mc = var0;
        ee = var1;
    }
    @SideOnly(Side.CLIENT)
    public static EntityClientPlayerMP getPlayer()
    {
        return mc.thePlayer;
    }
    public static ForgeDirection getSide(EntityPlayer p, int x, int y, int z)
    {
        return ForgeDirection.getOrientation(determineOrientation(p.worldObj, x, y, z, p));
    }
    public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase)
    {
        if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F)
        {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

            if (d0 - (double)par2 > 2.0D)
            {
                return 1;
            }

            if ((double)par2 - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
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
            TileEntity var5 = var0.getBlockTileEntity(var1, var2, var3);
            return !var4.isInstance(var5) ? null : var5;
        }
    }

    public static boolean isEntityFireImmune(Entity var0)
    {
        return var0.isImmuneToFire();
    }

    public static int getEntityHealth(EntityLiving var0)
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
        return var0.getItemStackLimit();
    }

    public static int blockDamageDropped(Block var0, int var1)
    {
        return var0.damageDropped(var1);
    }

    public static void dropBlockAsItemStack(Block var0, EntityLiving var1, int var2, int var3, int var4, ItemStack var5)
    {
        var0.dropBlockAsItem(var1.worldObj, var2, var3, var4, var5.getItemDamage(), 0);
    }

    public static boolean isJumping(EntityPlayer var0)
    {
        return mc.gameSettings.keyBindJump.pressed;
    }

    public static boolean isFuel(ItemStack var0)
    {
        return isFuel(var0.itemID, var0.getItemDamage());
    }

    public static boolean isFuel(int var0)
    {
        return isFuel(var0, 0);
    }

    public static boolean isFuel(int var0, int var1)
    {
        return GameRegistry.getFuelValue(new ItemStack(var0, 1, var1)) != 0;
    }

    public static void playSound(String var0, float var1, float var2, float var3, float var4, float var5)
    {
        ModLoader.getMinecraftInstance().sndManager.playSound(var0, var1, var2, var3, var4, var5);
    }

    public static void playSound(String var0, double var1, double var3, double var5, float var7, float var8)
    {
        ModLoader.getMinecraftInstance().sndManager.playSound(var0, (float)var1, (float)var3, (float)var5, var7, var8);
    }

    public static void playSoundAtPlayer(String var0, EntityPlayer var1, float var2, float var3)
    {
        playSound(var0, (float)var1.posX, (float)var1.posY, (float)var1.posZ, var2, var3);
    }
}
