package ee.features;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntityHandler {
	@ForgeSubscribe
	public void entityUpdate(LivingUpdateEvent e)
	{
		if(e.entityLiving != null && e.entityLiving instanceof EntityPlayer)
		{
			EELimited.ticks++;
			EntityPlayer p = (EntityPlayer)e.entityLiving;
			if(EELimited.getMod().consumeItem(EELimited.Food,4))
			{
			}
			ItemStack[] inv = p.inventory.mainInventory;
			boolean fly = false;
			boolean muteki = false;
			for(int i = 0;i<inv.length;i++)
			{
				if(inv[i] == null)
				{
					continue;
				}
				if(inv[i].itemID == EELimited.IDWolf&&inv[i].getItemDamage() == 1)
				{
					fly = true;
				}
				if(inv[i].itemID == EELimited.IDVolc)
				{
					p.extinguish();
				}
				if(inv[i].itemID == EELimited.IDDD&&inv[i].getItemDamage() == 1)
				{
					muteki = true;
				}
			}
			if(!p.capabilities.isCreativeMode)
			{
				p.capabilities.allowFlying = fly;
				p.capabilities.disableDamage = muteki;
			}
		}
	}
	@ForgeSubscribe
	public void entityHurt(LivingHurtEvent e)
	{
		if(e.entityLiving instanceof EntityPlayer &&(e.source == DamageSource.lava || e.source == DamageSource.inFire || e.source == DamageSource.onFire))
		{
			if((EELimited.instance.getPlayer().inventory.hasItem(EELimited.IDVolc)))
			{
				e.setCanceled(true);
			}
		}
	}

}
