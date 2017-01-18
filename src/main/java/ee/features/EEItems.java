package ee.features;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ee.features.items.ItemAlchemyBag;
import ee.features.items.ItemArchangelSmite;
import ee.features.items.ItemBlackHoleRing;
import ee.features.items.ItemBodyStone;
import ee.features.items.ItemCovalenceDust;
import ee.features.items.ItemDMAxe;
import ee.features.items.ItemDMHoe;
import ee.features.items.ItemDMPickaxe;
import ee.features.items.ItemDMShears;
import ee.features.items.ItemDMShovel;
import ee.features.items.ItemDMSword;
import ee.features.items.ItemDestructionCatalyst;
import ee.features.items.ItemEE;
import ee.features.items.ItemEvertide;
import ee.features.items.ItemIgnitionRing;
import ee.features.items.ItemInventoryOperator;
import ee.features.items.ItemKleinStar;
import ee.features.items.ItemMiniumStone;
import ee.features.items.ItemPhilosophersStone;
import ee.features.items.ItemRMPickaxe;
import ee.features.items.ItemRepairCharm;
import ee.features.items.ItemSwiftwolfsRing;
import ee.features.items.ItemTimeWatch;
import ee.features.items.ItemVolcanite;
import ee.features.items.entities.ItemLavaOrb;
import ee.features.items.entities.ItemMobRandomizer;
import ee.features.items.entities.ItemWaterOrb;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class EEItems
{

	public static boolean isConfigLoaded;
	private static Map<String,Boolean> loadedConfigList = new HashMap<String, Boolean>();
	private static Configuration config;

    /**
     * Items
     */
    public static Item Phil;
    public static Item AlchCoal;
    public static Item Cov;
    public static Item mobiusFuel;
    public static Item Volc;
    public static Item Ever;
    public static Item DM;
    public static Item Swift;
    public static Item Food;
    public static Item DMAxe;
    public static Item DMSword;
    public static Item DMPickaxe;
    public static Item DMShovel;
    public static Item ironband;
    public static Item DMShears;
    public static Item DMHoe;
    public static Item NIron;
    public static Item PhilTool;
    public static Item Repair;
    public static Item AlchBag;
    public static Item Klein;
    public static Item BHR;
    public static Item ArchAngel;
    public static Item RM;
    public static Item RMPickaxe;
    public static Item destCatal;
    public static Item miniumStone;
    public static Item ignitionRing;
    public static Item timeWatch;
    public static Item bodyStone;

    public static Item inventoryOperator;

    /**
     * Projectiles
     */
    public static Item LavaOrb;
    public static Item WaterOrb;
    public static Item Randomizer;

    public static void init()
    {
    	if(isItemEnabled("PhilosophersStone"))
    	{
    		Phil = new ItemPhilosophersStone();
    	}
    	if(isItemEnabled("Volcanite"))
    	{
    		Volc = new ItemVolcanite();
    	}
    	if(isItemEnabled("Evertide"))
    	{
    		Ever = new ItemEvertide();
    	}
    	if(isItemEnabled("CovalenceDust"))
    	{
    		Cov = new ItemCovalenceDust();
    	}
    	if(isItemEnabled("SwiftwolfsRing"))
    	{
    		Swift = new ItemSwiftwolfsRing();
    	}
    	if(isItemEnabled("AlchemicalCoal"))
    	{
    		AlchCoal = new ItemEE(NameRegistry.AlchemicalCoal);
    	}
    	if(isItemEnabled("MobiusFuel"))
    	{
    		mobiusFuel = new ItemEE(NameRegistry.Mobius);
    	}
    	if(isItemEnabled("DarkmatterAxe"))
    	{
    		DMAxe = new ItemDMAxe();
    	}
    	if(isItemEnabled("DarkmatterHoe"))
    	{
    		DMHoe = new ItemDMHoe();
    	}
    	if(isItemEnabled("DarkmatterSword"))
    	{
    		DMSword = new ItemDMSword();
    	}
    	if(isItemEnabled("DarkmatterShovel"))
    	{
    		DMShovel = new ItemDMShovel();
    	}
    	if(isItemEnabled("DarkmatterShears"))
    	{
    		DMShears = new ItemDMShears();
    	}
    	if(isItemEnabled("DarkmatterPickaxe"))
    	{
    		DMPickaxe = new ItemDMPickaxe();
    	}
    	if(isItemEnabled("RedmatterPickaxe"))
    	{
    		RMPickaxe = new ItemRMPickaxe();
    	}
    	if(isItemEnabled("Darkmatter"))
    	{
    		DM = new ItemEE(NameRegistry.DM);
    	}
    	if(isItemEnabled("IronBand"))
    	{
    		ironband = new ItemEE(NameRegistry.IronBand);
    	}
    	if(isItemEnabled("TalismanOfRepair"))
    	{
    		Repair = new ItemRepairCharm();
    	}
    	if(isItemEnabled("AlchemyBag"))
    	{
    		AlchBag = new ItemAlchemyBag();
    	}
    	if(isItemEnabled("KleinStar"))
    	{
    		Klein = new ItemKleinStar();
    	}
    	LavaOrb = new ItemLavaOrb();
    	WaterOrb = new ItemWaterOrb();
    	Randomizer = new ItemMobRandomizer();
    	if(isItemEnabled("BlackHoleRing"))
    	{
    		BHR = new ItemBlackHoleRing();
    	}
    	if(isItemEnabled("ArchangelsSmite"))
    	{
    		ArchAngel = new ItemArchangelSmite();
    	}
    	if(isItemEnabled("Redmatter"))
    	{
    		RM = new ItemEE("RM");
    	}
    	if(isItemEnabled("DestructionCatalyst"))
    	{
    		destCatal = new ItemDestructionCatalyst();
    	}
    	if(isItemEnabled("MiniumStone"))
    	{
    		miniumStone = new ItemMiniumStone();
    	}
    	if(isItemEnabled("IgnitionRing"))
    	{
    		ignitionRing = new ItemIgnitionRing();
    	}
    	if(isItemEnabled("timeWatch"))
    	{
    		timeWatch = new ItemTimeWatch();
    	}
    	if(isItemEnabled("bodyStone"))
    	{
    		bodyStone = new ItemBodyStone();
    	}
    	if(isItemEnabled("inventoryOperator"))
    	{
    		inventoryOperator = new ItemInventoryOperator();
    	}
    }
    public static void loadConfig(FMLPreInitializationEvent event)
    {
    	File file = new File(event.getModConfigurationDirectory(),"EELimited_items.cfg");
    	config = new Configuration(file);
    	config.load();
    	config.addCustomCategoryComment("items", "is item enabled?");
    	isConfigLoaded = true;
    }
    public static boolean isItemEnabled(String name)
    {
    	if(loadedConfigList.containsKey(name))
    	{
    		return loadedConfigList.get(name);
    	}
    	boolean value = config.getBoolean(name + "Enabled", "items", true, "is " + name + " enabled?");
    	loadedConfigList.put(name, value);
    	config.save();
    	return value;
    }
}