package ee.features;

import static ee.features.EEBlocks.Aggregator;
import static ee.features.EEBlocks.AlchChest;
import static ee.features.EEBlocks.DMBlock;
import static ee.features.EEBlocks.EETorch;
import static ee.features.EEBlocks.EMCCharger;
import static ee.features.EEBlocks.EMCCollector;
import static ee.features.EEBlocks.EMCCollectorMk2;
import static ee.features.EEBlocks.EMCCollectorMk3;
import static ee.features.EEBlocks.FuelBurner;
import static ee.features.EEBlocks.FuelBurnerOn;
import static ee.features.EEBlocks.Locus;
import static ee.features.EEBlocks.NovaTNT;
import static ee.features.EEBlocks.RMBlock;
import static ee.features.EEBlocks.cAlchChest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ee.features.blocks.BlockAggregator;
import ee.features.blocks.BlockAlchChest;
import ee.features.blocks.BlockColoredAlchChest;
import ee.features.blocks.BlockDMPedestal;
import ee.features.blocks.BlockEE;
import ee.features.blocks.BlockEETorch;
import ee.features.blocks.BlockLocus;
import ee.features.blocks.BlockNovaTNT;
import ee.features.blocks.emc.BlockEMCCharger;
import ee.features.blocks.emc.BlockEMCCollector;
import ee.features.blocks.emc.BlockEMCCollectorMk2;
import ee.features.blocks.emc.BlockEMCCollectorMk3;
import ee.features.blocks.emc.BlockFuelBurner;
import ee.features.items.ItemAlchemyBag;
import ee.features.items.ItemArchangelSmite;
import ee.features.items.ItemBlackHoleRing;
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
import ee.features.items.ItemKleinStar;
import ee.features.items.ItemMiniumStone;
import ee.features.items.ItemPhilosophersStone;
import ee.features.items.ItemRMPickaxe;
import ee.features.items.ItemRepairCharm;
import ee.features.items.ItemSwiftwolfsRing;
import ee.features.items.ItemVolcanite;
import ee.features.items.entities.ItemLavaOrb;
import ee.features.items.entities.ItemMobRandomizer;
import ee.features.items.entities.ItemWaterOrb;
import ee.features.tiles.DMPedestalTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class EEBlocks
{

	public static boolean isConfigLoaded;
	private static Map<String,Boolean> loadedConfigList = new HashMap<String, Boolean>();
	private static Configuration config;


    /**
     * Blocks
     */
    public static Block EETorch;
    public static Block DMBlock;
    public static Block AlchChest;
    public static Block Aggregator;
    public static Block Locus;
    public static Block EMCCollector;
    public static Block EMCCollectorMk2;
    public static Block EMCCollectorMk3;
    public static Block EMCCharger;
    public static Block FuelBurner;
    public static Block FuelBurnerOn;
    public static Block RMBlock;
    public static Block NovaTNT;
    public static Block cAlchChest;
    public static Block DMPedestal;

    public static void init()
    {
    	if(isItemEnabled("AlchemicalChest"))
    	{
    		AlchChest = new BlockAlchChest();
        }
    	if(isItemEnabled("DarkmatterBlock"))
    	{
    		DMBlock = new BlockEE(Material.rock,NameRegistry.DMBlock).setHardness(500);
        }
    	if(isItemEnabled("InterdictionTorch"))
    	{
    		EETorch = new BlockEETorch();
        }
    	if(isItemEnabled("CollectorMK1"))
    	{
    		EMCCollector = new BlockEMCCollector();
        }
    	if(isItemEnabled("CollectorMK2"))
       	{
    		EMCCollectorMk2 = new BlockEMCCollectorMk2();
        }
    	if(isItemEnabled("CollectorMK3"))
       	{
    		EMCCollectorMk3 = new BlockEMCCollectorMk3();
        }
    	if(isItemEnabled("EMCCharger"))
       	{
    		EMCCharger = new BlockEMCCharger();
        }
    	if(isItemEnabled("FuelBurner"))
        {
    		FuelBurner = new BlockFuelBurner(false);
    		FuelBurnerOn = new BlockFuelBurner(true);
        }
    	if(isItemEnabled("RedmatterBlock"))
       	{
    		RMBlock = new BlockEE(Material.rock,NameRegistry.RMBlock).setHardness(800);
        }
    	if(isItemEnabled("GlowstoneAggregator"))
       	{
    		Aggregator = new BlockAggregator();
        }
    	if(isItemEnabled("DarkmatterLocus"))
        {
    		Locus = new BlockLocus();
        }
    	if(isItemEnabled("NovaCatalyst"))
       	{
    		NovaTNT = new BlockNovaTNT();
       	}
    	if(isItemEnabled("ColorFilteredAlchemicalChest"))
       	{
    		cAlchChest = new BlockColoredAlchChest();
       	}
    	if(isItemEnabled("DMPedestal"))
    	{
    		DMPedestal = new BlockDMPedestal();
    	}
    }
    public static void loadConfig(FMLPreInitializationEvent event)
    {
    	File file = new File(event.getModConfigurationDirectory(),"EELimited_blocks.cfg");
    	config = new Configuration(file);
    	config.load();
    	config.addCustomCategoryComment("blocks", "is block enabled?");
    	isConfigLoaded = true;
    }
    public static boolean isItemEnabled(String name)
    {
    	if(loadedConfigList.containsKey(name))
    	{
    		return loadedConfigList.get(name);
    	}
    	boolean value = config.getBoolean(name + "Enabled", "blocks", true, "is " + name + " enabled?");
    	loadedConfigList.put(name, value);
    	config.save();
    	return value;
    }
}