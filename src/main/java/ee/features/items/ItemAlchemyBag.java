package ee.features.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.gui.BagData;
import ee.util.EEProxy;

public class ItemAlchemyBag extends ItemEEFunctional {

	BagData data;
	private IIcon[] icons;

	public ItemAlchemyBag() {
		super(NameRegistry.Bag);
		setHasSubtypes(true).setContainerItem(null).setMaxDamage(0);
	}
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		player.openGui(EELimited.instance,EELimited.ALCH_BAG_ID, world,player.chunkCoordX,player.chunkCoordY,player.chunkCoordZ);
		return item;
    }
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int par4, boolean par5)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)entity;
			if(!world.isRemote)
			{
				data = getData(item,world);
				data.onUpdate(world, p);
				data.markDirty();
				World worldObj = p.worldObj;
				if (EEProxy.invContainsItem(data.inventory, new ItemStack(EELimited.BHR, 1, 1)))
				{
					AxisAlignedBB bBox = p.boundingBox.expand(7, 7, 7);
					List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, bBox);

					for (EntityItem i : itemList)
					{
						i.delayBeforeCanPickup = 20;
						double d1 = (p.posX - i.posX);
						double d2 = (p.posY + (double)p.getEyeHeight() - i.posY);
						double d3 = (p.posZ - i.posZ);
						double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

						i.motionX += d1 / d4 * 0.1D;
						i.motionY += d2 / d4 * 0.1D;
						i.motionZ += d3 / d4 * 0.1D;

						i.moveEntity(i.motionX, i.motionY, i.motionZ);
					}

					AxisAlignedBB pickBBox = p.boundingBox.expand(0.5,0.5,0.5);
					List<EntityItem> itemToPick = world.getEntitiesWithinAABB(EntityItem.class, pickBBox);

					for (EntityItem i : itemToPick)
					{
						if(i.isDead)
						{
							break;
						}
						if (EEProxy.hasSpace(data.inventory, i.getEntityItem()))
						{
							ItemStack remain = EEProxy.pushStackInInv(data.inventory, i.getEntityItem());
							worldObj.playSoundEffect(i.posX,i.posY,i.posZ,"random.pop", 0.2F, ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
							if (remain == null)
							{
								i.setDead();
							}
						}
					}
				}

				ItemStack rTalisman = EEProxy.getStackFromInv(data.inventory, new ItemStack(EELimited.Repair));

				if (rTalisman != null)
				{
					byte coolDown = rTalisman.stackTagCompound.getByte("Cooldown");

					if (coolDown > 0)
					{
						rTalisman.stackTagCompound.setByte("Cooldown", (byte) (coolDown - 1));
					}
					else
					{
						boolean hasAction = false;

						for (int i = 0; i < data.inventory.length; i++)
						{
							ItemStack invStack = data.inventory[i];

							if (invStack == null || invStack.getItem() instanceof ItemEE)
							{
								continue;
							}

							if (!invStack.getHasSubtypes() && invStack.getMaxDamage() != 0 && invStack.getItemDamage() > 0)
							{
								invStack.setItemDamage(invStack.getItemDamage() - 1);
								data.inventory[i] = invStack;

								if (!hasAction)
								{
									hasAction = true;
								}
							}
						}

						if (hasAction)
						{
							rTalisman.stackTagCompound.setByte("Cooldown", (byte) 19);
						}
					}
				}
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs cTab, List list)
	{
		for (int i = 0; i < 16; ++i)
			list.add(new ItemStack(item, 1, i));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return icons[MathHelper.clamp_int(par1, 0, 15)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		icons = new IIcon[16];

		for (int i = 0; i < 16; i++)
		{
			icons[i] = register.registerIcon("ee:AlchBag_"+i);
		}
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y,int z, int par7, float par8, float par9, float par10)
    {
		player.openGui(EELimited.instance,EELimited.ALCH_BAG_ID, world,player.chunkCoordX,player.chunkCoordY,player.chunkCoordZ);
		return true;
    }
	public static BagData getBagData(ItemStack item, World world)
	{
		BagData data = null;
		if(item != null && item.getItem() instanceof ItemAlchemyBag)
		{
			data = ((ItemAlchemyBag)item.getItem()).getData(item, world);
		}
		return data;
	}
	public BagData getData(ItemStack is, World w)
	{
		String itemName = "AlchBag";
		int itemDamage = MathHelper.clamp_int(is.getItemDamage(),0,15);
		String var3 = String.format("%s_%s", itemName, itemDamage);
		BagData var4 = (BagData)w.loadItemData(BagData.class, var3);

		if (var4 == null)
		{
			var4 = new BagData(var3);
			var4.markDirty();
			w.setItemData(var3, var4);
		}

		return var4;
	}
}
