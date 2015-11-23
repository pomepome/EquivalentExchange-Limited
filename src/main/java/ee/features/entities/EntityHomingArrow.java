package ee.features.entities;
import java.util.List;

import ee.features.EELimited;
import ee.util.ReflectionUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityHomingArrow extends EntityArrow
{
	EntityLivingBase target;
	World world;
	public boolean flag;
	private void init(World world)
	{
		this.world = world;
	}

	public EntityHomingArrow(World world)
	{
		super(world);
		init(world);
	}

	public EntityHomingArrow(World world, EntityLivingBase par2, float par3)
	{
		super(world, par2, par3);
		init(world);
	}

	public void setPickable(boolean flag)
	{
		this.flag = flag;
	}
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(EELimited.Debug && this.isInGround())
		{
			System.out.println("GROUNDDDDDDD!!!!!");
		}
		AxisAlignedBB box = this.boundingBox;
		if (target == null && !isInGround())
		{
			AxisAlignedBB bBox = box.expand(12, 12, 12);
			List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bBox);

			double distance = 100000;

			for (EntityLivingBase entity : list)
			{
				if(entity instanceof EntityPlayer)
				{
					continue;
				}
				double toIt = distanceTo(entity);

				if (distance > toIt)
				{
					distance = toIt;
					target = entity;
				}
			}

			if (target == null)
			{
				return;
			}

			double d5 = target.posX - this.posX;
			double d6 = target.boundingBox.minY + target.height - this.posY;
			double d7 = target.posZ - this.posZ;

			this.setThrowableHeading(d5, d6, d7, 2.0F, 0.0F);
		}
		else if (!isInGround())
		{
			if (target.getHealth() == 0)
			{
				AxisAlignedBB bBox = box.expand(12, 12, 12);
				List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bBox);

				double distance = 100000;

				for (EntityLivingBase entity : list)
				{
					if(entity instanceof EntityPlayer)
					{
						continue;
					}
					double toIt = distanceTo(entity);

					if (distance > toIt)
					{
						distance = toIt;
						target = entity;
					}
				}

				if (target == null)
				{
					worldObj.removeEntity(this);
					return;
				}

				double d5 = target.posX - this.posX;
				double d6 = target.boundingBox.minY + target.height - this.posY;
				double d7 = target.posZ - this.posZ;

				this.setThrowableHeading(d5, d6, d7, 2.0F, 0.0F);
				return;
			}

			world.spawnParticle("flame", box.maxX, box.maxY, box.maxZ, 0.0D, 0.0D, 0.0D);

			double d5 = target.posX - this.posX;
			double d6 = target.boundingBox.minY + target.height - this.posY;
			double d7 = target.posZ - this.posZ;

			this.setThrowableHeading(d5, d6, d7, 2.0F, 0.0F);
		}
		if(this.motionX == 0 && this.motionY == 0 && this.motionZ == 0)
		{
			worldObj.removeEntity(this);
			this.setDead();
		}
	}

	private double distanceTo(EntityLivingBase entity)
	{
		double [] ds = new double []
		{
			this.posX - entity.posX,
			this.posY - entity.posY,
			this.posZ - entity.posZ
		};

		double d = 0;

		for (int i = 0; i < 3; i++)
			d += ds[i] * ds[i];

		return Math.sqrt(d);
	}

	private boolean isInGround()
	{
			return ReflectionUtil.getArrowInGround(this);
	}
}