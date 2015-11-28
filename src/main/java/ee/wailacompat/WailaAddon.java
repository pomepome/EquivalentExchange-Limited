package ee.wailacompat;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class WailaAddon
{
	public static Class coloredChest;

	public static final Logger log = LogManager.getLogger("EELimited|WailaAddon");

	public static void register()
	{
		try {
			coloredChest = Class.forName("ee.features.tiles.TileEntityColoredAlchChest");
			ModuleRegistrar.instance().registerBodyProvider(new HudColoredChest(), coloredChest);
		} catch (ClassNotFoundException e) {
			log.log(Level.WARN, "Class not found. " + e);
			return;
		}
	}
}
