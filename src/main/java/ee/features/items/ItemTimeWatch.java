package ee.features.items;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.items.interfaces.IExtraFunction;
import ee.features.items.interfaces.IModeChange;
import ee.features.items.interfaces.IPedestalItem;
import ee.features.tiles.DMPedestalTile;
import ee.features.tiles.TileDirection;
import ee.features.tiles.TileEmc;
import ee.util.EEProxy;
import ee.util.Timer1s;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.BlockFluidBase;

public class ItemTimeWatch extends ItemChargeable implements IPedestalItem,IExtraFunction
{
	int mode;

	IIcon on,off;

	Logger log = LogManager.getLogger("TimeWatch");

	public ItemTimeWatch()
	{
		super("timeWatch",3);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	@Override
    public final void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
		if(!(entity instanceof EntityPlayer))
		{
			return;
		}
		EntityPlayer player = (EntityPlayer)entity;
		mode = getMode(stack);
		int charge = getChargeLevel(stack);
		int act = charge + 1;
		int num = (int)Math.pow(2, act);

		WorldInfo wi = world.getWorldInfo();

		AxisAlignedBB aabb = player.boundingBox.expand(10, 10, 10);
		slowMobs(world, aabb, 0.5f);

		if(mode == 1)
		{
			//Forward mode
			if(wi.getWorldTime() == Long.MAX_VALUE)
			{
				wi.setWorldTime(0);
			}
			wi.setWorldTime(wi.getWorldTime() + num - 1);
		}
		else if(mode == 2)
		{
			if(wi.getWorldTime() == Long.MIN_VALUE)
			{
				wi.setWorldTime(0);
			}
			//Reverse mode
			wi.setWorldTime(wi.getWorldTime() - num - 1);
		}
		else if(mode == 3)
		{
			wi.setWorldTime(wi.getWorldTime() - 1);
		}
    }
	@Override
	public void updateInPedestal(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			AxisAlignedBB aabb = ((DMPedestalTile)world.getTileEntity(x, y, z)).getEffectBounds();
			speedUpTileEntities(world, 1, aabb);
			speedUpRandomTicks(world, 1, aabb);
			slowMobs(world, aabb, 0.5f);
		}
	}

	private void slowMobs(World world, AxisAlignedBB bBox, float mobSlowdown)
	{
		if (bBox == null) // Sanity check for chunk unload weirdness
		{
			return;
		}
		for (Object obj : world.getEntitiesWithinAABB(EntityLiving.class, bBox))
		{
			Entity ent = (Entity) obj;

			if (ent.motionX != 0)
			{
				ent.motionX *= mobSlowdown;
			}
			if (ent.motionZ != 0)
			{
				ent.motionZ *= mobSlowdown;
			}
		}
	}
	private void speedUpTileEntities(World world, int bonusTicks, AxisAlignedBB bBox)
	{
		if (bBox == null || bonusTicks == 0) // Sanity check the box for chunk unload weirdness
		{
			return;
		}
		List<TileEntity> list = EEProxy.getTileEntitiesWithinAABB(world, bBox);
		for (int i = 0; i < bonusTicks; i++)
		{
			for (TileEntity tile : list)
			{
				if ((tile instanceof TileEmc || tile instanceof TileDirection) && !(tile instanceof DMPedestalTile) && !tile.isInvalid())
				{
					tile.updateEntity();
				}
			}
		}
	}
	private void setMode(ItemStack stack, int mode)
	{
		if(stack == null)
		{
			return;
		}
		if(mode > 2)
		{
			mode = 0;
		}
		stack.setItemDamage(mode == 0 ? 0 : 1);
		stack.getTagCompound().setInteger("Mode", mode);
	}
	private int getMode(ItemStack stack)
	{
		if(stack == null || !(stack.getItem() instanceof ItemTimeWatch))
		{
			return 0;
		}
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null || !tag.hasKey("Mode"))
		{
			NBTTagCompound t = new NBTTagCompound();
			t.setInteger("Mode", 0);
			stack.setTagCompound(t);
			return 0;
		}
		return tag.getInteger("Mode");
	}
	private String getModeText(int mode)
	{
		switch(mode)
		{
			case 0 : return "timewatch.normal";
			case 1 : return "timewatch.forward";
			case 2 : return "timewatch.reverse";
			case 3 : return "timewatch.stop";
		}
		return "ERROR";
	}
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg)
	{
		if (dmg == 0)
		{
			return off;
		}

		return on;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		off = register.registerIcon("ee:time_watch_off");
		on = register.registerIcon("ee:time_watch_on");
	}
	private void speedUpRandomTicks(World world, int bonusTicks, AxisAlignedBB bBox)
	{
		if (bBox == null || bonusTicks == 0) // Sanity check the box for chunk unload weirdness
		{
			return;
		}
		for (int x = (int) bBox.minX; x <= bBox.maxX; x++)
		{
			for (int y = (int) bBox.minY; y <= bBox.maxY; y++)
			{
				for (int z = (int) bBox.minZ; z <= bBox.maxZ; z++)
				{
					Block block = world.getBlock(x, y, z);

					if (block.getTickRandomly()
							&& !(block instanceof BlockLiquid) // Don't speed vanilla non-source blocks - dupe issues
							&& !(block instanceof BlockFluidBase) // Don't speed Forge fluids - just in case of dupes as well
							&& !(block instanceof IGrowable)
							&& !(block instanceof IPlantable) // All plants should be sped using Harvest Goddess
						)
					{
						for (int i = 0; i < bonusTicks; i++)
						{
							block.updateTick(world, x, y, z, itemRand);
						}
					}
				}
			}

		}
	}
	@Override
	public void onExtraFunction(EntityPlayer player, ItemStack is)
	{
		int mode = getMode(is);
		mode = (mode + 1) % 4;
		if(mode == 0)
		{
			is.setItemDamage(0);
		}
		else
		{
			is.setItemDamage(1);
		}
		EEProxy.chatToPlayer(player, StatCollector.translateToLocal(getModeText(mode)));
		is.getTagCompound().setInteger("Mode", mode);
	}
}
