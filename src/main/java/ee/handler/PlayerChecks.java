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
		Iterator<EntityPlayerMP> iter = fireChecks.iterator();

		while (iter.hasNext())
		{
			EntityPlayerMP player = iter.next();

			if (!isPlayerFireImmune(player))
			{
				if (player.isImmuneToFire())
				{
					EEProxy.setEntityImmuneToFire(player, false);
				}

				iter.remove();
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
			Iterator<EntityPlayerMP> iterator = fireChecks.iterator();

			while (iterator.hasNext())
			{
				if (iterator.next().equals(player))
				{
					iterator.remove();
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
