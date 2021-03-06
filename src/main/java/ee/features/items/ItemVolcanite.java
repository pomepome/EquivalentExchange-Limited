package ee.features.items;

import ee.features.Constants;
import ee.features.EEItems;
import ee.features.NameRegistry;
import ee.features.entities.EntityLavaProjectile;
import ee.features.items.interfaces.IChargeable;
import ee.features.items.interfaces.IExtraFunction;
import ee.features.items.interfaces.IPedestalItem;
import ee.features.items.interfaces.IProjectileShooter;
import ee.handler.PlayerChecks;
import ee.util.EEProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemVolcanite extends ItemChargeable implements IChargeable,IExtraFunction,IProjectileShooter,IPedestalItem
{
	private int[] ranges = new int[]{};

    public ItemVolcanite()
    {
        super(NameRegistry.Volc,8);
        this.setMaxStackSize(1).setContainerItem(this).setMaxDamage(3);
    }

    public void doVaporize(ItemStack var1, World var2, EntityPlayer var3, int range)
    {
        boolean var4 = false;

        if (range % 2 == 0)
        {
            range++;
        }

        int count = (range + 1) / 2;
        int ox = (int) var3.posX - count;
        int oy = (int) var3.posY - count;
        int oz = (int) var3.posZ - count;

        for (int i = 0; i < range; i++)
        {
            for (int j = 0; j < range; j++)
            {
                for (int k = 0; k < range; k++)
                {
                    int nx = ox + i;
                    int ny = oy + j;
                    int nz = oz + k;

                    if (var2.getBlock(nx, ny, nz).getMaterial() == Material.water)
                    {
                        var4 = true;
                        var2.setBlock(nx, ny, nz, Blocks.air);
                    }
                }
            }
        }
        if (var4)
        {
            EEProxy.playSoundAtPlayer("random.fizz", var3, 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.isSneaking())
        {
            doVaporize(var1, var2, var3, 21);
        }

        return var1;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if (!par3World.isRemote)
		{
			TileEntity tile = par3World.getTileEntity(par4, par5, par6);

			if (tile instanceof IFluidHandler)
			{
				IFluidHandler tank = (IFluidHandler) tile;

				if (EEProxy.canFillTank(tank, FluidRegistry.LAVA, par7))
				{
					if (EEProxy.useResource(par2EntityPlayer, 32, true))
					{
						EEProxy.fillTank(tank, FluidRegistry.LAVA, par7, 1000);
						return true;
					}
				}
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
            if (Blocks.lava.canPlaceBlockAt(par3World, par4, par5, par6))
            {
                par3World.setBlock(par4, par5, par6, Blocks.lava);
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
    public final void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5)
    {
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;

    		int x = (int) Math.floor(player.posX);
    		int y = (int) (player.posY - player.getYOffset());
    		int z = (int) Math.floor(player.posZ);
			Block b = world.getBlock(x, y - 1, z);
			Block bu = world.getBlock(x, y, z);

    		if ((b.getMaterial() == Material.lava) && bu == Blocks.air)
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
    			if((b.getMaterial() == Material.water) && bu == Blocks.air && EEProxy.getStackFromInv(player.inventory, new ItemStack(EEItems.Ever)) != null)
				{
					return;
				}
    			if (player.capabilities.getWalkSpeed() != Constants.PLAYER_WALK_SPEED)
    			{
    				EEProxy.setPlayerSpeed(player, Constants.PLAYER_WALK_SPEED);
    			}
    		}

    		if (!world.isRemote)
    		{
    			if (!player.isImmuneToFire())
    			{
    				EEProxy.setEntityImmuneToFire(player, true);
    			}

    			PlayerChecks.addPlayerFireChecks((EntityPlayerMP) player);
    		}
    	}
    }

	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is) {
	}

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityLavaProjectile(player.worldObj, player));
		player.worldObj.playSoundAtEntity(player, "ee:items.transmute",1.0F,1.0F);
		return true;
	}

	@Override
	public void updateInPedestal(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			WorldInfo info = world.getWorldInfo();
			info.setRainTime(0);
			info.setRaining(false);
			info.setThundering(false);
		}
	}
}
