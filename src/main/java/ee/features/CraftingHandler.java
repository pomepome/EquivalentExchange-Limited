package ee.features;

import static ee.features.EELimited.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class CraftingHandler {
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
}
