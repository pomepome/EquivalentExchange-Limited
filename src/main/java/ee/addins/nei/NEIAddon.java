package ee.addins.nei;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import ee.gui.GuiPhilWorkbench;

public class NEIAddon
{
	private static final Logger log = LogManager.getLogger("EELimited|NEIAddon");
	public static void register()
	{
		log.log(Level.INFO, "registering GUI overlay...");
		API.registerGuiOverlay(GuiPhilWorkbench.class, "crafting");
		API.registerGuiOverlayHandler(GuiPhilWorkbench.class, new DefaultOverlayHandler(), "crafting");
		log.log(Level.INFO, "registering ended!");
	}
}
