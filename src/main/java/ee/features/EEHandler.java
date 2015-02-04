package ee.features;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
	}
}
