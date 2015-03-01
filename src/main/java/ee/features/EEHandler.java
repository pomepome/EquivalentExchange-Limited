package ee.features;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ee.features.items.ItemDMSword;

public class EEHandler {
	public static boolean hasStarted = false;

	boolean allowFly = false,disableDamage = false;
	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent e)
	{
		if(!hasStarted)
		{
			hasStarted = true;
			((EELimited) EELimited.instance).addSmeltingExchange();
		}
		EntityLivingBase l = e.entityLiving;
		if(l instanceof EntityPlayer)
		{
			Timer1s.Tick();
			EntityPlayer p = (EntityPlayer)l;
			InventoryPlayer ip = p.inventory;
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
			ItemStack[] inv = ip.mainInventory;
			for(ItemStack is : inv)
			{
				if(is == null)
				{
					continue;
				}
				if(is.getItem() == EELimited.Swift)
				{
					if(is.getItemDamage() == 0)
					{
						allowFly = true;
					}
					else
					{
						allowFly = false;
					}
				}
				if(is.getItem() == EELimited.DD && is.getItemDamage() == 0)
				{
					disableDamage = true;
				}
				else
				{
					disableDamage = false;
				}
			}
			pc.allowFlying = allowFly;
			pc.disableDamage = disableDamage;
		}
		if(EELimited.noBats&& l instanceof EntityBat)
		{
			l.attackEntityFrom(DamageSource.drown,20);
		}
		if(EELimited.dropVillagerInventory&& l instanceof EntityVillager)
		{
			InventoryBasic villagerInventory = ((EntityVillager)l).func_175551_co();
			for(int i = 0;i < villagerInventory.getSizeInventory();i++)
			{
				ItemStack is = villagerInventory.getStackInSlot(i);
				if(is == null)
				{
					continue;
				}
				villagerInventory.setInventorySlotContents(i, null);
				l.dropItem(is.getItem(),is.stackSize);
			}
		}
	}
	@SubscribeEvent
	public void livingHurt(LivingHurtEvent e)
	{
		Entity cause = e.source.getEntity();
		EntityLivingBase hurted = e.entityLiving;
		if(cause!=null&&cause instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)cause;
			ItemStack current = p.getCurrentEquippedItem();
			if(current != null && current.getItem() instanceof ItemDMSword)
			{
				hurted.setFire(20);
			}
		}
	}
	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent event)
	{
		if(event.entityLiving instanceof EntityEnderman&&EELimited.noTeleport)
		{
			event.setCanceled(true);
		}
	}
}
