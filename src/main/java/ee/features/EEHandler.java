package ee.features;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
		EntityLivingBase l = e.entityLiving;
		if(l instanceof EntityPlayer)
		{
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
			boolean allowFly = false,disableDamage = false;
			ItemStack[] inv = p.inventory.mainInventory;
			for(ItemStack is : inv)
			{
				if(is == null)
				{
					continue;
				}
				if(is.getItem() == EELimited.Swift && is.getItemDamage() > 0)
				{
					allowFly = true;
				}
				if(is.getItem() == EELimited.DD && is.getItemDamage() > 0)
				{
					disableDamage = true;
				}
			}
			pc.allowFlying = allowFly;
			pc.disableDamage = disableDamage;
		}
		if(EELimited.noBats&&l instanceof EntityBat)
		{
			l.attackEntityFrom(DamageSource.drown,80);
		}
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
			if(p.worldObj.getBlock(p.playerLocation.posX,p.playerLocation.posY,p.playerLocation.posZ).isBed(p.worldObj,p.playerLocation.posX,p.playerLocation.posY,p.playerLocation.posZ, p))
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
				e.setFire(30);
			}
		}
	}
}
