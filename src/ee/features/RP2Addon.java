package ee.features;

import static ee.features.EELimited.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.oredict.OreDictionary;

public class RP2Addon {
	public static boolean rp2BaseIsInstalled;
    public static boolean rp2WorldIsInstalled;

    public static String rp2Name = "com.eloraam.redpower.";

    public static Block rp2Ores = null;
    public static Block rp2Leaves = null;
    public static Block rp2Logs = null;
    public static Block rp2Stone = null;
    public static Item rp2Resources = null;
    public static Block rp2Plants = null;
    public static Item rp2Seeds = null;
    public static Item rp2PickaxeRuby = null;
    public static Item rp2PickaxeSapphire = null;
    public static Item rp2PickaxeEmerald = null;
    public static Item rp2ShovelRuby = null;
    public static Item rp2ShovelSapphire = null;
    public static Item rp2ShovelEmerald = null;
    public static Item rp2AxeRuby = null;
    public static Item rp2AxeSapphire = null;
    public static Item rp2AxeEmerald = null;
    public static Item rp2SwordRuby = null;
    public static Item rp2SwordSapphire = null;
    public static Item rp2SwordEmerald = null;
    public static Item rp2HoeRuby = null;
    public static Item rp2HoeSapphire = null;
    public static Item rp2HoeEmerald = null;
    public static Item rp2SickleWood = null;
    public static Item rp2SickleStone = null;
    public static Item rp2SickleIron = null;
    public static Item rp2SickleGold = null;
    public static Item rp2SickleDiamond = null;
    public static Item rp2SickleRuby = null;
    public static Item rp2SickleSapphire = null;
    public static Item rp2SickleEmerald = null;
    public static Item rp2HandsawIron = null;
    public static Item rp2HandsawDiamond = null;
    public static Item rp2HandsawRuby = null;
    public static Item rp2HandsawSapphire = null;
    public static Item rp2HandsawEmerald = null;
    public static Item rp2IndigoDye = null;
    public static Item rp2Nikolite = null;
    public static Item rp2Alloy = null;

    public static void initBase()
    {
        try
        {
            rp2HandsawIron = (Item)Class.forName(rp2Name+"RedPowerBase").getField("itemHandsawIron").get((Object)null);
            rp2HandsawDiamond = (Item)Class.forName(rp2Name+"RedPowerBase").getField("itemHandsawDiamond").get((Object)null);
            rp2IndigoDye = (Item)Class.forName(rp2Name+"RedPowerBase").getField("itemDyeIndigo").get((Object)null);
            rp2Alloy = ((ItemStack)(Class.forName(rp2Name+"RedPowerBase").getField("itemIngotRed").get((Object)null))).getItem();
            rp2BaseIsInstalled = true;
            OreDictionary.registerOre(INameRegistry.IngotRed,gs(rp2Alloy,1,0));
    		OreDictionary.registerOre(INameRegistry.IngotBlue,gs(rp2Alloy,1,1));
    		OreDictionary.registerOre(INameRegistry.IngotBrass,gs(rp2Alloy,1,2));
    		OreDictionary.registerOre(INameRegistry.BouleSilicon,gs(rp2Alloy,1,3));
    		OreDictionary.registerOre(INameRegistry.WaferSilicon,gs(rp2Alloy,1,4));
    		OreDictionary.registerOre(INameRegistry.WaferBlue,gs(rp2Alloy,1,5));
    		OreDictionary.registerOre(INameRegistry.WaferRed,gs(rp2Alloy,1,6));
    		OreDictionary.registerOre(INameRegistry.Tinplate,gs(rp2Alloy,1,7));
            ModLoader.getLogger().fine("[EE2] Loaded EE2-RP2 Core Addon");
        }
        catch (Exception var1)
        {
            rp2BaseIsInstalled = false;
            ModLoader.getLogger().warning("[EE2] Could not load EE2-RP2 Core Addon");
            var1.printStackTrace(System.err);
        }
    }

    public static void initWorld()
    {
        try
        {
            rp2Ores = (Block)Class.forName(rp2Name+"RedPowerWorld").getField("blockOres").get((Object)null);
            rp2Logs = (Block)Class.forName(rp2Name+"RedPowerWorld").getField("blockLogs").get((Object)null);
            rp2Stone = (Block)Class.forName(rp2Name+"RedPowerWorld").getField("blockStone").get((Object)null);
            rp2Plants = (Block)Class.forName(rp2Name+"RedPowerWorld").getField("blockPlants").get((Object)null);
            rp2Leaves = (Block)Class.forName(rp2Name+"RedPowerWorld").getField("blockLeaves").get(null);
            rp2Seeds = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSeeds").get((Object)null);
            rp2PickaxeRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemPickaxeRuby").get((Object)null);
            rp2PickaxeSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemPickaxeSapphire").get((Object)null);
            rp2PickaxeEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemPickaxeEmerald").get((Object)null);
            rp2ShovelRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemShovelRuby").get((Object)null);
            rp2ShovelSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemShovelSapphire").get((Object)null);
            rp2ShovelEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemShovelGreenSapphire").get((Object)null);
            rp2AxeRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemAxeRuby").get((Object)null);
            rp2AxeSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemAxeSapphire").get((Object)null);
            rp2AxeEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemAxeGreenSapphire").get((Object)null);
            rp2SwordRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSwordRuby").get((Object)null);
            rp2SwordSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSwordSapphire").get((Object)null);
            rp2SwordEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSwordGreenSapphire").get((Object)null);
            rp2HoeRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHoeRuby").get((Object)null);
            rp2HoeSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHoeSapphire").get((Object)null);
            rp2HoeEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHoeGreenSapphire").get((Object)null);
            rp2SickleWood = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleWood").get((Object)null);
            rp2SickleStone = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleStone").get((Object)null);
            rp2SickleIron = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleIron").get((Object)null);
            rp2SickleGold = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleGold").get((Object)null);
            rp2SickleDiamond = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleDiamond").get((Object)null);
            rp2SickleRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleRuby").get((Object)null);
            rp2SickleEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleGreenSapphire").get((Object)null);
            rp2SickleSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemSickleSapphire").get((Object)null);
            rp2HandsawSapphire = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHandsawSapphire").get((Object)null);
            rp2HandsawRuby = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHandsawRuby").get((Object)null);
            rp2HandsawEmerald = (Item)Class.forName(rp2Name+"RedPowerWorld").getField("itemHandsawGreenSapphire").get((Object)null);
            rp2WorldIsInstalled = true;
            ModLoader.getLogger().fine("[EELimited] Loaded EE2-RP2 World Addon");
        }
        catch (Exception var1)
        {
            rp2WorldIsInstalled = false;
            ModLoader.getLogger().warning("[EELimited] Could not load EE2-RP2 World Addon");
            var1.printStackTrace(System.err);
        }
    }
}
