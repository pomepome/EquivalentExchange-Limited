package ee.features;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEETorch extends BlockEE
{
    private int powerCycle;

    protected BlockEETorch(int var1)
    {
        super(var1, Material.circuits,INameRegistry.TorchEE);
        this.setTickRandomly(true);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 15;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 2;
    }

    private boolean canPlaceTorchOn(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockNormalCube(var2, var3, var4) || var1.getBlockId(var2, var3, var4) == Block.fence.blockID;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockNormalCube(var2 - 1, var3, var4) ? true : (var1.isBlockNormalCube(var2 + 1, var3, var4) ? true : (var1.isBlockNormalCube(var2, var3, var4 - 1) ? true : (var1.isBlockNormalCube(var2, var3, var4 + 1) ? true : this.canPlaceTorchOn(var1, var2, var3 - 1, var4))));
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var5 == 1 && this.canPlaceTorchOn(var1, var2, var3 - 1, var4))
        {
            var6 = 5;
        }

        if (var5 == 2 && var1.isBlockNormalCube(var2, var3, var4 + 1))
        {
            var6 = 4;
        }

        if (var5 == 3 && var1.isBlockNormalCube(var2, var3, var4 - 1))
        {
            var6 = 3;
        }

        if (var5 == 4 && var1.isBlockNormalCube(var2 + 1, var3, var4))
        {
            var6 = 2;
        }

        if (var5 == 5 && var1.isBlockNormalCube(var2 - 1, var3, var4))
        {
            var6 = 1;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.updateTick(var1, var2, var3, var4, var5);

        if (this.powerCycle > 0)
        {
            this.doInterdiction(var1, var2, var3, var4, true);
            --this.powerCycle;
        }

        this.doInterdiction(var1, var2, var3, var4, false);

        if (var1.getBlockMetadata(var2, var3, var4) == 0)
        {
            this.onBlockAdded(var1, var2, var3, var4);
        }

        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);

        if (var1.isBlockNormalCube(var2 - 1, var3, var4))
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
        }
        else if (var1.isBlockNormalCube(var2 + 1, var3, var4))
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        }
        else if (var1.isBlockNormalCube(var2, var3, var4 - 1))
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        }
        else if (var1.isBlockNormalCube(var2, var3, var4 + 1))
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
        }
        else if (this.canPlaceTorchOn(var1, var2, var3 - 1, var4))
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        }

        this.dropTorchIfCantStay(var1, var2, var3, var4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
        int var6;

        if (var1.isBlockIndirectlyGettingPowered(var2, var3, var4))
        {
            for (var6 = 0; var6 <= 2; ++var6)
            {
                this.doInterdiction(var1, var2, var3, var4, true);
            }

            this.powerCycle = 16;
        }

        if (this.dropTorchIfCantStay(var1, var2, var3, var4))
        {
            var6 = var1.getBlockMetadata(var2, var3, var4);
            boolean var7 = false;

            if (!var1.isBlockNormalCube(var2 - 1, var3, var4) && var6 == 1)
            {
                var7 = true;
            }

            if (!var1.isBlockNormalCube(var2 + 1, var3, var4) && var6 == 2)
            {
                var7 = true;
            }

            if (!var1.isBlockNormalCube(var2, var3, var4 - 1) && var6 == 3)
            {
                var7 = true;
            }

            if (!var1.isBlockNormalCube(var2, var3, var4 + 1) && var6 == 4)
            {
                var7 = true;
            }

            if (!this.canPlaceTorchOn(var1, var2, var3 - 1, var4) && var6 == 5)
            {
                var7 = true;
            }

            if (var7)
            {
                this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 1);
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        }
    }

    private boolean dropTorchIfCantStay(World var1, int var2, int var3, int var4)
    {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 1);
            var1.setBlockWithNotify(var2, var3, var4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3 var5, Vec3 var6)
    {
        int var7 = var1.getBlockMetadata(var2, var3, var4) & 7;
        float var8 = 0.15F;

        if (var7 == 1)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 2)
        {
            this.setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 3)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
        }
        else if (var7 == 4)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
        }
        else
        {
            float var9 = 0.1F;
            this.setBlockBounds(0.5F - var9, 0.0F, 0.5F - var9, 0.5F + var9, 0.6F, 0.5F + var9);
        }

        return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        double var7 = (double)((float)var2 + 0.5F);
        double var9 = (double)((float)var3 + 0.7F);
        double var11 = (double)((float)var4 + 0.5F);
        double var13 = 0.2199999988079071D;
        double var15 = 0.27000001072883606D;

        if (var6 == 1)
        {
            var1.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 2)
        {
            var1.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 3)
        {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 4)
        {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            var1.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        }
    }

    public void doInterdiction(World var1, int var2, int var3, int var4, boolean var5)
    {
        float var6 = 5.0F;
        List var7 = var1.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var9 = var7.iterator();

        while (var9.hasNext())
        {
            Entity var8 = (Entity)var9.next();
            this.PushEntities(var8, var2, var3, var4);
        }

        List var15 = var1.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var11 = var15.iterator();

        while (var11.hasNext())
        {
            Entity var10 = (Entity)var11.next();
            if(!(((EntityArrow)var10).shootingEntity instanceof EntityPlayer))
            {
            	this.PushEntities(var10, var2, var3, var4);
            }
        }

        List var14 = var1.getEntitiesWithinAABB(EntityLargeFireball.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var13 = var14.iterator();

        while (var13.hasNext())
        {
            Entity var12 = (Entity)var13.next();
            this.PushEntities(var12, var2, var3, var4);
        }

        List var16 = var1.getEntitiesWithinAABB(IMob.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var17 = var16.iterator();

        while (var17.hasNext())
        {
            Entity var18 = (Entity)var17.next();
            this.PushEntities(var18, var2, var3, var4);
        }
    }

    private void PushEntities(Entity var1, int var2, int var3, int var4)
    {
        if (!(var1 instanceof EntityPlayer))
        {
            double var6 = (double)((float)var2) - var1.posX;
            double var8 = (double)((float)var3) - var1.posY;
            double var10 = (double)((float)var4) - var1.posZ;
            double var12 = var6 * var6 + var8 * var8 + var10 * var10;
            var12 *= var12;

            if (var12 <= Math.pow(6.0D, 4.0D))
            {
                double var14 = -(var6 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);
                double var16 = -(var8 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);
                double var18 = -(var10 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);

                if (var14 > 0.0D)
                {
                    var14 = 0.22D;
                }
                else if (var14 < 0.0D)
                {
                    var14 = -0.22D;
                }

                if (var16 > 0.2D)
                {
                    var16 = 0.12000000000000001D;
                }
                else if (var16 < -0.1D)
                {
                    var16 = 0.12000000000000001D;
                }

                if (var18 > 0.0D)
                {
                    var18 = 0.22D;
                }
                else if (var18 < 0.0D)
                {
                    var18 = -0.22D;
                }

                var1.motionX += var14;
                var1.motionY += var16;
                var1.motionZ += var18;
            }
        }
    }
}

