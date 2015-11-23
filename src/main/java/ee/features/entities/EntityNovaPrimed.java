package ee.features.entities;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.network.PacketHandler;
import ee.network.PacketSpawnParticle;
import ee.util.MiningExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNovaPrimed extends Entity
{
	public int fuse;
	private EntityLivingBase entity;
	public EntityNovaPrimed(World p_i1729_1_)
	{
		super(p_i1729_1_);
		this.fuse = 60;
	}
	public EntityNovaPrimed(World world, double x, double y, double z, EntityLivingBase placer)
	{
		super(world);
		this.setPosition(x, y, z);
		this.entity = placer;
		this.fuse = 60;
	}
		@Override
		public void onUpdate()
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= 0.03999999910593033D;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;

			if (this.onGround)
			{
				this.motionX *= 0.699999988079071D;
				this.motionZ *= 0.699999988079071D;
				this.motionY *= -0.5D;
			}

			if (this.fuse-- <= 0)
			{
				this.setDead();

				if (!this.worldObj.isRemote)
					this.explode();
			}
		}

		private void explode()
		{
			MiningExplosion explosion = new MiningExplosion(worldObj, this, this.posX, this.posY, this.posZ, 10.0F);
			explosion.isFlaming = true;
			explosion.isSmoking = true;
			explosion.doExplosionA();
			explosion.doExplosionB(true);

			int x = (int)posX;
			int y = (int)posY;
			int z = (int)posZ;
			PacketHandler.sendToAllAround(new PacketSpawnParticle("hugeexplosion",x, y, z), new TargetPoint(worldObj.provider.dimensionId,x,y + 1,z,32));
		}
		  /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	    {
	        p_70014_1_.setByte("Fuse", (byte)this.fuse);
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	    {
	        this.fuse = p_70037_1_.getByte("Fuse");
	    }

	    @SideOnly(Side.CLIENT)
	    public float getShadowSize()
	    {
	        return 0.0F;
	    }
		@Override
		protected void entityInit() {
		}
}
