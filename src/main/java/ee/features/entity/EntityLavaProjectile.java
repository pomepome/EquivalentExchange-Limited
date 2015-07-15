package ee.features.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.util.ForgeDirection;
import ee.features.EEProxy;

public class EntityLavaProjectile extends EntityThrowable
{
	private EntityPlayer thrower;
	public EntityLavaProjectile(World world)
	{
		super(world);
	}

	public EntityLavaProjectile(World world, EntityPlayer entity)
	{
		super(world, entity);
		thrower = entity;
	}

	public EntityLavaProjectile(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.worldObj.isRemote)
		{
			boolean flag = true;

			for (int x = (int) (this.posX - 3); x <= this.posX + 3; x++)
				for (int y = (int) (this.posY - 3); y <= this.posY + 3; y++)
					for (int z = (int) (this.posZ - 3); z <= this.posZ + 3; z++)
					{
						Block block = this.worldObj.getBlock(x, y, z);

						if (block == Blocks.water || block == Blocks.flowing_water)
						{
							this.worldObj.setBlockToAir(x, y, z);

							if (flag)
							{
								this.worldObj.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
								flag = false;
							}
						}
					}

			if (this.isInWater())
			{
				this.setDead();
			}

			if (this.posY > 128)
			{
				if(EEProxy.useResource((EntityPlayer)thrower,16,true))
				{
					WorldInfo worldInfo = this.worldObj.getWorldInfo();
					worldInfo.setRaining(false);
				}
				this.setDead();
			}
		}
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if (this.worldObj.isRemote)
		{
			return;
		}

		if (mop.typeOfHit == MovingObjectType.BLOCK)
		{
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);

			if(!EEProxy.useResource((EntityPlayer)thrower, 128,true))
			{
				return;
			}
			this.worldObj.setBlock(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ, Blocks.flowing_lava);
			this.setDead();
		}
		else if (mop.typeOfHit == MovingObjectType.ENTITY)
		{
			Entity ent = mop.entityHit;
			if(!EEProxy.useResource((EntityPlayer)thrower, 8,true))
			{
				ent.setFire(1);
			}
			else
			{
				ent.setFire(10);
				ent.attackEntityFrom(DamageSource.inFire, 5);
			}
			this.setDead();
		}
	}

}
