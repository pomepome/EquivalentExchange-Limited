package ee.handler;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import ee.features.EELimited;
import ee.features.items.ItemVolcanite;
import ee.util.EEProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class PlayerChecks
{
	private static final List<EntityPlayerMP> fireChecks = Lists.newArrayList();

	public static void update()
	{
		for(int i = 0;i < fireChecks.size();i++)
		{
			EntityPlayerMP player = fireChecks.get(i);

			if (!isPlayerFireImmune(player))
			{
				if (player.isImmuneToFire())
				{
					EEProxy.setEntityImmuneToFire(player, false);
				}

				fireChecks.remove(i);
				EELimited.instance.log.debug("Removed " + player.getCommandSenderName() + " from fire checks.");
			}
		}
	}
	public static void addPlayerFireChecks(EntityPlayerMP player)
	{
		if (!fireChecks.contains(player))
		{
			fireChecks.add(player);
			EELimited.instance.log.debug("Added " + player.getCommandSenderName() + " to fire checks.");
		}
	}
	public static void removePlayerFireChecks(EntityPlayerMP player)
	{
		if (fireChecks.contains(player))
		{
			for(int i = 0;i < fireChecks.size();i++)
			{
				if (fireChecks.get(i).equals(player))
				{
					fireChecks.remove(i);
					EELimited.instance.log.debug("Removed " + player + " from fire checks.");
					return;
				}
			}
		}
	}
	private static boolean isPlayerFireImmune(EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode)
		{
			return true;
		}

		for (int i = 0; i <= 8; i++)
		{
			ItemStack stack = player.inventory.getStackInSlot(i);

			if (stack != null && stack.getItem() instanceof ItemVolcanite)
			{
				return true;
			}
		}

		return false;
	}
}
