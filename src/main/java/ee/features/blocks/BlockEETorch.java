package ee.features.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Predicate;

import ee.features.NameRegistry;

public class BlockEETorch extends BlockEE {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate()
    {
        private static final String __OBFID = "CL_00002054";
        public boolean apply(EnumFacing facing)
        {
            return facing != EnumFacing.DOWN;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.apply((EnumFacing)p_apply_1_);
        }
    });
	private int powerCycle = 16;
	public BlockEETorch() {
		super(Material.circuits,NameRegistry.EETorch);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setTickRandomly(false).setLightLevel(1f);
	}
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos)
    {
        if (World.doesBlockHaveSolidTopSurface(worldIn, pos))
        {
            return true;
        }
        else
        {
            Block block = worldIn.getBlockState(pos).getBlock();
            return block.canPlaceTorchOnTop(worldIn, pos);
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        Iterator iterator = FACING.getAllowedValues().iterator();
        EnumFacing enumfacing;

        do
        {
            if (!iterator.hasNext())
            {
                return false;
            }

            enumfacing = (EnumFacing)iterator.next();
        }
        while (!this.canPlaceAt(worldIn, pos, enumfacing));

        return true;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos1 = pos.offset(facing.getOpposite());
        boolean flag = facing.getAxis().isHorizontal();
        return flag && worldIn.isSideSolid(blockpos1, facing, true) || facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos1);
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (this.canPlaceAt(worldIn, pos, facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else
        {
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            EnumFacing enumfacing1;

            do
            {
                if (!iterator.hasNext())
                {
                    return this.getDefaultState();
                }

                enumfacing1 = (EnumFacing)iterator.next();
            }
            while (!worldIn.isSideSolid(pos.offset(enumfacing1.getOpposite()), enumfacing1, true));
            worldIn.scheduleUpdate(pos,worldIn.getBlockState(pos).getBlock(), 1);
            this.powerCycle = 16;
            return this.getDefaultState().withProperty(FACING, enumfacing1);
        }
    }
    @Override
    public void updateTick(World var1,BlockPos pos,IBlockState state,Random rand)
    {
    }
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.checkForDrop(worldIn, pos, state);
        onNeighborChangeInternal(worldIn,new BlockPos(pos.getX(),pos.getY()+1,pos.getZ()),state);
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.checkForDrop(worldIn, pos, state))
        {
            return true;
        }
        else
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            EnumFacing.Axis axis = enumfacing.getAxis();
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            boolean flag = false;
            int var6;
            if (worldIn.isBlockIndirectlyGettingPowered(pos)!=0)
            {
                for (var6 = 0; var6 <= 2; ++var6)
                {
                    this.doInterdiction(worldIn, pos.getX(), pos.getY(), pos.getZ());
                }

                this.powerCycle = 16;
            }
            if (axis.isHorizontal() && !worldIn.isSideSolid(pos.offset(enumfacing1), enumfacing1, true))
            {
                flag = true;
            }
            else if (axis.isVertical() && !this.canPlaceOn(worldIn, pos.offset(enumfacing1)))
            {
                flag = true;
            }

            if (flag)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING)))
        {
            return true;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock() == this)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }

    public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end)
    {
        EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue(FACING);
        float f = 0.15F;

        if (enumfacing == EnumFacing.EAST)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (enumfacing == EnumFacing.WEST)
        {
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (enumfacing == EnumFacing.SOUTH)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (enumfacing == EnumFacing.NORTH)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
        else
        {
            f = 0.1F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }

        return super.collisionRayTrace(worldIn, pos, start, end);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
                break;
            case 5:
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
        }

        return iblockstate;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;

        if (enumfacing.getAxis().isHorizontal())
        {
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double)enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
        }
        else
        {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

    	FMLClientHandler.instance().getClient().thePlayer.attackEntityFrom(DamageSource.cactus, 200);
        if (this.powerCycle > 0)
        {
            this.doInterdiction(worldIn, pos.getX(), pos.getY(), pos.getZ());
            --this.powerCycle;
            if(this.powerCycle < 0)
            {
            	this.powerCycle = 16;
            }
        }
        worldIn.scheduleUpdate(pos, state.getBlock(),1);
    }

    public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i;

        switch (SwitchEnumFacing.FACING_LOOKUP[((EnumFacing)state.getValue(FACING)).ordinal()])
        {
            case 1:
                i = b0 | 1;
                break;
            case 2:
                i = b0 | 2;
                break;
            case 3:
                i = b0 | 3;
                break;
            case 4:
                i = b0 | 4;
                break;
            case 5:
            case 6:
            default:
                i = b0 | 5;
        }

        return i;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }

    static final class SwitchEnumFacing
        {
            static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
            private static final String __OBFID = "CL_00002053";

            static
            {
                try
                {
                    FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 1;
                }
                catch (NoSuchFieldError var6)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 2;
                }
                catch (NoSuchFieldError var5)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
                }
                catch (NoSuchFieldError var4)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 4;
                }
                catch (NoSuchFieldError var3)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 5;
                }
                catch (NoSuchFieldError var2)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.UP.ordinal()] = 6;
                }
                catch (NoSuchFieldError var1)
                {
                    ;
                }
            }
        }
    public void doInterdiction(World var1, int var2, int var3, int var4)
    {
        float var6 = 100.0F;
        List var7 = var1.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.fromBounds((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var9 = var7.iterator();

        while (var9.hasNext())
        {
            EntityLiving var8 = (EntityLiving)var9.next();
            this.PushEntities(var8, var2, var3, var4);
        }

        List var15 = var1.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.fromBounds((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var11 = var15.iterator();

        while (var11.hasNext())
        {
            Entity var10 = (Entity)var11.next();

            if (((EntityArrow)var10).shootingEntity != null && !(((EntityArrow)var10).shootingEntity instanceof EntityPlayer))
            {
                this.PushEntities(var10, var2, var3, var4);
            }
        }

        List var14 = var1.getEntitiesWithinAABB(EntityLargeFireball.class, AxisAlignedBB.fromBounds((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var13 = var14.iterator();

        while (var13.hasNext())
        {
            Entity var12 = (Entity)var13.next();
            this.PushEntities(var12, var2, var3, var4);
        }

        List var16 = var1.getEntitiesWithinAABB(EntitySlime.class, AxisAlignedBB.fromBounds((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
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
                var1.addVelocity(var14 * 4, var16 * 4, var18 * 4);
                var1.velocityChanged = true;
            }
        }
    }
}
