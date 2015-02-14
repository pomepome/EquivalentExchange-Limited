package ee.features;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CraftingHandler {

	@SubscribeEvent
	public void onCrafted(PlayerEvent.ItemCraftedEvent e)
	{
		ItemStack item = e.crafting;
		EntityPlayer p = e.player;
		if(item.getItem() == EELimited.Phil)
		{
			p.triggerAchievement(EELimited.getPhil);
		}
		if(item.getItem() == EELimited.DM)
		{
			p.triggerAchievement(EELimited.getDM);
		}
	}
}
