package ee.wailacompat;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class WailaAddon
{
	public static Class coloredChest;
	public static Class emcMachine;
	public static Class emcCharger;

	public static Field content;
	public static Field emc;
	public static Field maxEmc;

	public static final Logger log = LogManager.getLogger("EELimited|WailaAddon");

	public static void register()
	{
		ModuleRegistrar reg = ModuleRegistrar.instance();
		try {
			coloredChest = Class.forName("ee.features.tiles.TileEntityColoredAlchChest");
			emcMachine = Class.forName("ee.features.tiles.TileEmc");
			emcCharger = Class.forName("ee.features.tiles.TileEMCCharger");

			emc = emcMachine.getDeclaredField("EMC");
			maxEmc = emcMachine.getDeclaredField("maxAmount");
			content = emcCharger.getDeclaredField("content");

			emc.setAccessible(true);
			maxEmc.setAccessible(true);
			content.setAccessible(true);

			reg.registerBodyProvider(new HudColoredChest(), coloredChest);

			HudEMCMachine machineProv = new HudEMCMachine();
			reg.registerBodyProvider(machineProv, emcMachine);
			reg.registerNBTProvider(machineProv, emcMachine);
		} catch (ClassNotFoundException e)
		{
			log.log(Level.WARN, "Class not found. " + e);
			return;
		} catch (NoSuchFieldException e)
		{
			log.log(Level.WARN, "Field not found." + e);
		}
	}
}
