package ee.handler;

import static ee.features.EELimited.*;
import static ee.util.EEProxy.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ee.addons.extrautil.AddonExtraUtilities;
import ee.features.EELimited;
import ee.features.PlayerTimers;
import ee.util.EEProxy;
import ee.util.Timer1s;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonHandler
{
	public static boolean hasStarted = false;
	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent e) throws Exception
	{
		PlayerChecks.update();
		Timer1s.Tick();
		EntityPlayer p = e.player;
		if(!hasStarted)
		{
			hasStarted = true;
			EELimited.instance.addSmeltingExchange();
		}
		if(!p.worldObj.isRemote)
		{
			return;
		}
		if(EELimited.loadEU && AddonExtraUtilities.isAngelEnabled(p))
		{
			p.capabilities.allowFlying = true;
			return;
		}
		if(p.capabilities.isCreativeMode)
		{
			return;
		}
		ItemStack wolf = getStackFromInv(p.inventory,gs(Swift,1,1));
		if(wolf != null)
		{
			if(Timer1s.isTime()&&useResource(p, 1, true))
			{
				p.capabilities.allowFlying = true;
			}
			else if(!useResource(p, 1, false))
			{
				p.capabilities.allowFlying = false;
				p.capabilities.isFlying = false;
				wolf.setItemDamage(0);
			}
		}
		else
		{
			p.capabilities.allowFlying = false;
			p.capabilities.isFlying = false;
		}
	}
	@SubscribeEvent
	public void onCrafted(PlayerEvent.ItemCraftedEvent e)
	{
		EntityPlayer p = e.player;
		Item i = e.crafting.getItem();
		if(i == Phil)
		{
			p.triggerAchievement(getPhil);
		}
		if(i == DM)
		{
			p.triggerAchievement(getDM);
		}
	}
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			PlayerTimers.update();
		}
	}
}
