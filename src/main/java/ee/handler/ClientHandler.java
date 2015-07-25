package ee.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.features.EELimited;
import ee.features.items.ItemChargeable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class ClientHandler
{
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
