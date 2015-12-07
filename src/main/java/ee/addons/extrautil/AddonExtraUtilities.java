package ee.addons.extrautil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.util.EEProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AddonExtraUtilities
{
	private static Item angWings;
	private static final Logger log = LogManager.getLogger("EELimited|EUAddon");
	
	public static void load()
	{
		try
		{
			Class c = Class.forName("com.rwtema.extrautils.ExtraUtils");
			angWings = (Item)c.getField("angelRing").get(null);
		}
		catch(Exception e)
		{
			log.error("A error has occured while loading....");
			log.error(e.getMessage());
		}
	}
	public static Item getAngelRing()
	{
		return angWings;
	}
	public static boolean isAngelEnabled(EntityPlayer p)
	{
		ItemStack stack = EEProxy.getStackFromInv(p.inventory, new ItemStack(angWings,1,32767));
		return stack != null;
	}
}
