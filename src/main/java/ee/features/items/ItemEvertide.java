package ee.features.items;

import ee.features.Constants;
import ee.features.EEItems;
import ee.features.NameRegistry;
import ee.features.entities.EntityWaterProjectile;
import ee.features.items.interfaces.IPedestalItem;
import ee.features.items.interfaces.IProjectileShooter;
import ee.util.EEProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemEvertide extends ItemChargeable implements IPedestalItem,IProjectileShooter,IFluidContainerItem
{
	public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5)
    {
        if (par3Entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)par3Entity;
			if (player.getAir() < 300)
        	{
            	player.setAir(300);
        	}
			int x = (int) Math.floor(player.posX);
			int y = (int) (player.posY - player.getYOffset());
			int z = (int) Math.floor(player.posZ);
			Block b = world.getBlock(x, y - 1, z);
			Block bu = world.getBlock(x, y, z);

			if ((b.getMaterial() == Material.water) && bu == Blocks.air)
			{
				if (!player.isSneaking())
				{
					player.motionY = 0.0D;
					player.fallDistance = 0.0F;
					player.onGround = true;
				}

				if (!world.isRemote && player.capabilities.getWalkSpeed() < 0.25F)
				{
					EEProxy.setPlayerSpeed(player, 0.25F);
				}
			}
			else if (!world.isRemote)
			{
				if((b.getMaterial() == Material.lava) && bu == Blocks.air && EEProxy.getStackFromInv(player.inventory, new ItemStack(EEItems.Volc)) != null)
				{
					return;
				}
				if (player.capabilities.getWalkSpeed() != Constants.PLAYER_WALK_SPEED)
				{
					EEProxy.setPlayerSpeed(player, Constants.PLAYER_WALK_SPEED);
				}
			}
        }
    }

    public ItemEvertide()
    {
        super(NameRegistry.Ever,8);
        this.setMaxStackSize(1).setContainerItem(this);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if (!par3World.isRemote)
		{
			TileEntity tile = par3World.getTileEntity(par4, par5, par6);

			if (tile instanceof IFluidHandler)
			{
				IFluidHandler tank = (IFluidHandler) tile;

				if (EEProxy.canFillTank(tank, FluidRegistry.WATER, par7))
				{
					EEProxy.fillTank(tank, FluidRegistry.WATER, par7, 1000);
					return true;
				}
			}

			Block block = par3World.getBlock(par4, par5, par6);
			int meta = par3World.getBlockMetadata(par4, par5, par6);
			if (block == Blocks.cauldron && meta < 3)
			{
				((BlockCauldron) block).func_150024_a(par3World, par4, par5, par6, meta + 1);
				return true;
			}
		}
        if (par3World.getBlock(par4, par5, par6) != Blocks.air)
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }

            if (!(par3World.getBlock(par4, par5, par6).getMaterial() == Material.lava || par3World.getBlock(par4, par5, par6).getMaterial() == Material.water) && !par3World.isAirBlock(par4, par5, par6))
            {
                return false;
            }
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (Blocks.water.canPlaceBlockAt(par3World, par4, par5, par6))
            {
                par3World.setBlock(par4, par5, par6, Blocks.water);
                par3World.getBlock(par4, par5, par6).onNeighborBlockChange(par3World, par4, par5, par6, Blocks.air);
            }

            return true;
        }
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityWaterProjectile(player.worldObj, player));
		player.worldObj.playSoundAtEntity(player, "ee:items.transmute",1.0F,1.0F);
		return true;
	}

	@Override
	public FluidStack getFluid(ItemStack container)
	{
		return new FluidStack(FluidRegistry.WATER,1000);
	}

	@Override
	public int getCapacity(ItemStack container) {
		return 1000;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill)
	{
		return 1000;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
	{
		return new FluidStack(FluidRegistry.WATER,1000);
	}

	@Override
	public void updateInPedestal(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			WorldInfo info = world.getWorldInfo();
			info.setRaining(true);
		}
	}
}
