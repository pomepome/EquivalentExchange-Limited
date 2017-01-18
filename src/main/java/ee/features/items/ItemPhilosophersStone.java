package ee.features.items;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import ee.features.EEItems;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.entities.EntityMobRandomizer;
import ee.features.items.interfaces.IChargeable;
import ee.features.items.interfaces.IExtraFunction;
import ee.features.items.interfaces.IProjectileShooter;
import ee.features.tiles.TileDirection;
import ee.features.tiles.TileEmc;
import ee.gui.PhilData;
import ee.network.PacketHandler;
import ee.network.PacketSound;
import ee.network.PacketSpawnParticle;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemPhilosophersStone extends ItemEEFunctional implements IExtraFunction,IChargeable,IProjectileShooter
{

	PhilData data;

	public ItemPhilosophersStone()
    {
        super(NameRegistry.Philo);
        this.setCreativeTab(EELimited.TabEE);
    }
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer p)
    {
		if(!p.isSneaking())
		{
			return var1;
		}
		return new ItemStack(EEItems.PhilTool);
    }
	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		p.openGui(EELimited.instance,EELimited.CRAFT,p.worldObj,(int)p.posX,(int)p.posY, (int)p.posZ);
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)entity;
			if(!world.isRemote)
			{
				data = getPhilData(world);
				data.markDirty();
			}
		}
	}
	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityMobRandomizer(player.worldObj,player));
		player.worldObj.playSoundAtEntity(player, "ee:items.transmute",1.0F,1.0F);
		return true;
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		if(par3World.isRemote)
		{
			return true;
		}
		if(par3World.getTileEntity(par4, par5, par6) instanceof TileEmc)
		{
			TileEmc tile = (TileEmc)par3World.getTileEntity(par4, par5, par6);
			EEProxy.chatToPlayer(par2EntityPlayer, "Stored EMC:"+tile.getStoredEmc());
			EEProxy.chatToPlayer(par2EntityPlayer, "This machine "+(tile.isRequestingEmc() ? "is requesting EMC": "isn't requesting EMC"));
		}
		return true;
    }
	@Override
	public void changeCharge(EntityPlayer player, ItemStack stack)
	{
		World w = player.worldObj;
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(w, player, true);
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			TileEntity tile = w.getTileEntity(x, y, z);
			if( tile != null && tile instanceof TileDirection)
			{
				TileDirection td = (TileDirection)tile;
				td.setRelativeOrientation(player, true);
				EEProxy.playSoundAtPlayer(EnumSounds.ACTION.getPath(), player, 1.0f, 1.0f);
				PacketHandler.sendToAllAround(new PacketSpawnParticle("largesmoke", x, y + 1, z), new TargetPoint(w.provider.dimensionId, x, y + 1, z, 32));
				return;
			}
			else if( tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
				int orientation = TileDirection.getRelativeOrientation(player);
				if(w.getBlockMetadata(x, y, z) != orientation)
				{
					w.setBlockMetadataWithNotify(x, y, z, orientation, 2);
					EEProxy.playSoundAtPlayer(EnumSounds.ACTION.getPath(), player,1.0f,1.0f);
					PacketHandler.sendToAllAround(new PacketSpawnParticle("largesmoke", x, y + 1, z), new TargetPoint(w.provider.dimensionId, x, y + 1, z, 32));
					return;
				}
			}
		}
		player.setCurrentItemOrArmor(0, new ItemStack(EEItems.PhilTool));
	}
	@Override
	public int getChargeLevel(ItemStack is) {
		return 0;
	}
	public static PhilData getPhilData(World world)
	{
		PhilData pData = null;

		pData = (PhilData)world.loadItemData(PhilData.class, "Phil");
		if(pData == null)
		{
			pData = new PhilData("Phil");
			pData.markDirty();
			world.setItemData("Phil", pData);
		}

		return pData;
	}
}
