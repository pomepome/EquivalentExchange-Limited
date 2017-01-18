package ee.features.items;

import java.util.List;

import ee.features.EELimited;
import ee.features.items.interfaces.IPedestalItem;
import ee.features.tiles.DMPedestalTile;
import ee.util.EEProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemIgnitionRing extends ItemRing implements IPedestalItem
{

	public ItemIgnitionRing()
	{
		super("ignition");
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is)
	{
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d)
	{
		if(world.isRemote)
		{
			return;
		}
		EEProxy.igniteNearby(world, player);
	}

	@Override
	public void updateInPedestal(World world, int x, int y, int z)
	{
		if (!world.isRemote && EELimited.pedIgnitionCooldown != -1)
		{
			DMPedestalTile tile = ((DMPedestalTile) world.getTileEntity(x, y, z));
			if (tile.getActivityCooldown() == 0)
			{
				List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, tile.getEffectBounds());
				for (EntityLivingBase living : list)
				{
					if(living instanceof EntityPlayer)
					{
						continue;
					}
					if(living instanceof EntityTameable && ((EntityTameable)living).getOwner() != null)
					{
						continue;
					}
					living.setFire(8);
					living.attackEntityFrom(DamageSource.outOfWorld, 6.0F);
				}

				tile.setActivityCooldown(EELimited.pedIgnitionCooldown);
			}
			else
			{
				tile.decrementActivityCooldown();
			}
		}
	}

}
