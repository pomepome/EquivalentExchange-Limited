package ee.features;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.features.items.ItemChargeable;

public class EEHandler {

	public static boolean hasStarted = false;
	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent e)
	{
		if(!hasStarted)
		{
			hasStarted = true;
			EELimited.instance.addSmeltingExchange();
		}
		if(FMLCommonHandler.instance().getSide().isServer())
		{
			return;
		}
		EntityLivingBase l = e.entityLiving;
		if(l instanceof EntityPlayer)
		{
			if(!l.worldObj.isRemote)
			{
				Timer1s.Tick();
			}
			EntityPlayer p = (EntityPlayer)l;
			PlayerCapabilities pc = p.capabilities;
			/*{
				ItemStack is = p.getCurrentEquippedItem();
				if(is!=null)
				{
					p.addChatMessage(new ChatComponentText(is.getItem().toString()));
				}
			}*/
			if(pc.isCreativeMode)
			{
				return;
			}
			boolean disableDamage = false;
			InventoryPlayer inv = p.inventory;
			ItemStack wolf = EEProxy.getStackFromInv(inv,new ItemStack(EELimited.Swift,1,1));
			if(wolf == null)
			{
				pc.allowFlying = false;
				pc.isFlying = false;
			}
			else
			{
				if(wolf.getItemDamage() > 0)
				{
					if(Timer1s.isTime() && !EEProxy.useResource(p,1,true))
					{
						disable(p);
						wolf.setItemDamage(0);
					}
					else
					{
						if(EEProxy.useResource(p,1,false))
						{
							pc.allowFlying = true;
						}
						else
						{
							pc.allowFlying = false;
							pc.isFlying = false;
							wolf.setItemDamage(0);
						}
					}
				}
				else
				{
					pc.allowFlying = false;
					pc.isFlying = false;
				}
			}
			ItemStack dd = EEProxy.getStackFromInv(inv,new ItemStack(EELimited.DD,1,1));
			if(dd == null)
			{
				pc.disableDamage = false;
			}
			else
			{
				if(dd.getItemDamage() > 0)
				{
					if(Timer1s.isTime() && !EEProxy.useResource(p,1,true))
					{
						pc.disableDamage = false;
						dd.setItemDamage(0);
					}
					else
					{
						if(EEProxy.useResource(p,1,false))
						{
							pc.disableDamage = true;
						}
						else
						{
							pc.disableDamage = false;
							dd.setItemDamage(0);
						}
					}
				}
				else
				{
					pc.disableDamage = false;
				}
			}
			EEProxy.setEntityImmuneToFire(p, p.inventory.hasItem(EELimited.Volc));
			if(p.inventory.hasItem(EELimited.Volc)&&p.worldObj.getBlock((int)p.posX,(int)p.posY,(int)p.posZ).getMaterial() == Material.lava&&p.capabilities.getWalkSpeed() != 0.1F)
			{
				EEProxy.setPlayerSpeed(p, 0.1F);
			}
			p.inventory.markDirty();
			p.inventoryContainer.detectAndSendChanges();
		}
		if(EELimited.noBats&&l instanceof EntityBat)
		{
			l.attackEntityFrom(DamageSource.drown,80);
		}
	}
	public void disable(EntityPlayer p)
	{
		p.capabilities.allowFlying = false;
		p.capabilities.isFlying = false;
	}
	@SubscribeEvent
	 public void sleepHandle(PlayerSleepInBedEvent event)
	 {
		if(event.entityPlayer.getHealth() < 40)
		{
			event.entityPlayer.heal(1);
		}
	 }
	@SubscribeEvent
	public void livingHurt(LivingHurtEvent event)
	{
		EntityLivingBase e = event.entityLiving;
		Entity cause = event.source.getEntity();
		if(e instanceof EntityPlayer&&!(e instanceof EntityTameable)&&event.source == DamageSource.inWall)
		{
			EntityPlayer p = (EntityPlayer)e;
			if(p != null&&p!=null&&p.worldObj != null&&p.worldObj.getBlock((int)p.posX,(int)p.posY,(int)p.posZ).isBed(p.worldObj,(int)p.posX,(int)p.posY,(int)p.posZ, p))
			{
				event.setCanceled(true);
			}
		}
		if(cause instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)cause;
			ItemStack current = p.getCurrentEquippedItem();
			if(current != null&&current.getItem() == EELimited.DMSword)
			{
				if(((ItemChargeable)current.getItem()).getChargeLevel(current) > 0)
				e.setFire(30);
			}
		}
	}
	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent e)
	{
		if(e.entityLiving != null && e.entityLiving instanceof EntityEnderman)
		{
			e.setCanceled(EELimited.noTeleport);
		}
	}
}
