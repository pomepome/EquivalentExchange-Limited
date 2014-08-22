package ee.features;

import static ee.features.Level.*;
import ic2.api.item.Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import forestry.api.core.ItemInterface;

@Mod(modid = EELimited.modid, name = "EELimited", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EELimited
{
    File configFile;//config file instance

    public static EELimited instance;
    public static boolean isStarted = false;
    public static final String modid = "EELimited";
    public static final int GuiID = 255;
    public static CreativeTabs TabEE;

    /**
     * Options
     */
    public static boolean cutDown;
    public static boolean Debug;
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
    public static Item DD;
    public static Item DMAxe;
    public static Item DMSword;
    public static Item DMPickaxe;
    public static Item DMShovel;
    public static Item ironband;
    public static Item DMShears;
    public static Item DMHoe;
    /**
     * Blocks
     */
    public static Block EETorch;
    public static Block DMBlock;
    /**
     * Item IDs
     */
    public static int IDPhil;
    public static int IDAlchCoal;
    public static int IDCov;
    public static int IDMobius;
    public static int IDVolc;
    public static int IDEver;
    public static int IDDM;
    public static int IDSwift;
    public static int IDFood;
    public static int IDDD;
    public static int IDDMAxe;
    public static int IDDMSword;
    public static int IDDMPickaxe;
    public static int IDDMShovel;
    public static int IDIronband;
    public static int IDDMShears;
    public static int IDDMHoe;
    /**
     * Block IDs
     */
    public static int IDEETorch;
    public static int IDDMB;

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        EEProxy.Init(Minecraft.getMinecraft(), this);
        Block.commandBlock.setCreativeTab(CreativeTabs.tabRedstone);
        instance = this;
        loadConfig();
        TabEE = new CreativeTabEE("EELimited");
        Phil = new ItemPhilosophersStone(IDPhil);
        AlchCoal = new ItemEE(IDAlchCoal, NameRegistry.AlchemicalCoal);
        mobiusFuel = new ItemEE(IDMobius, NameRegistry.Mobius).setMaxStackSize(64);
        DM = new ItemEE(IDDM, NameRegistry.DM);
        Cov = new ItemCovalenceDust(IDCov);
        Volc = new ItemVolcanite(IDVolc);
        Ever = new ItemEvertide(IDEver);
        EETorch = new BlockEETorch(IDEETorch);
        DMBlock = new BlockEE(IDDMB, NameRegistry.DMBlock);
        Swift = new ItemSwiftwolfsRing(IDSwift).setMaxStackSize(1);
        Food = new ItemFood(IDFood, 200, false).setTextureName("bread").setCreativeTab(TabEE);
        DD = new ItemDamageDisabler(IDDD);
        DMAxe = new ItemDMAxe(IDDMAxe).setMaxStackSize(1);
        DMSword = new ItemDMSword(IDDMSword).setMaxStackSize(1);
        DMPickaxe = new ItemDMPickaxe(IDDMPickaxe).setMaxStackSize(1);
        DMShovel = new ItemDMShovel(IDDMShovel).setMaxStackSize(1);
        DMShears = new ItemShears(IDDMShears).setMaxStackSize(1).setMaxDamage(0).setTextureName("ee:" + NameRegistry.DMShears).setUnlocalizedName(NameRegistry.DMShears).setCreativeTab(TabEE);
        ironband = new ItemEE(IDIronband, NameRegistry.IronBand).setMaxStackSize(64);
        DMHoe = new ItemDMHoe(IDDMHoe);
        naming(Phil, "Philosopher's Stone", "賢者の石");
        naming(AlchCoal, "Alchemical Coal", "錬金炭");
        naming(getCov(LOW), "Covalence Dust(Low)", "共有結合粉(低)");
        naming(getCov(MIDDLE), "Covalence Dust(Mid)", "共有結合粉(中)");
        naming(getCov(HIGH), "Covalence Dust(High)", "共有結合粉(高)");
        naming(mobiusFuel, "Mobius Fuel", "メビウス燃料");
        naming(DM, "Darkmetter", "暗黒物質");
        naming(Volc, "Volcanite Amulet", "溶岩の御守り");
        naming(Ever, "Evertide Amulet", "湧水の御守り");
        naming(EETorch, "Interdiction Torch", "結界灯火");
        naming(DMBlock, "Darkmatter Block", "暗黒物質ブロック");
        naming(Swift, "Swiftwolf's Rending Gale", "風統べる狼王の指輪");
        naming(Food, "Dummy Food", "ダミーの食料");
        naming(DD, "Damage Disabler", "ダメージ無効化の石");
        naming(DMAxe, "Darkmater Axe", "暗黒物質の斧");
        naming(DMSword, "Darkmatter Sword", "暗黒物質の剣");
        naming(DMPickaxe, "Darkmatter Pickaxe", "暗黒物質のツルハシ");
        naming(DMShovel, "Darkmatter Shovel", "暗黒物質のシャベル");
        naming(DMShears, "Darkmatter Shears", "暗黒物質のハサミ");
        naming(ironband, "Iron Band", "鉄の腕輪");
        naming(DMHoe, "Darkmatter Hoe", "ダークマターのクワ");
        /*
         * adding recipes
         */
        addRecipe(gs(Item.bucketWater), "S", "B", 'S', Item.snowball, 'B', Item.bucketEmpty);
        addRecipe(gs(Item.bucketLava), "GRG", " B ", 'G', Item.gunpowder, 'R', Item.redstone, 'B', Item.bucketEmpty);
        addRecipe(gs(Item.bucketMilk), "B", "W", "E", 'B', gs(Item.dyePowder, 1, 0xF), 'W', Item.bucketWater, 'E', Item.bucketEmpty);
        addRecipe(gs(Phil), "RGR", "GSG", "RGR", 'R', Item.redstone, 'G', Item.glowstone, 'S', Item.slimeBall);
        addSRecipe(gs(Item.glowstone, 4), gs(Item.coal.itemID), gs(Item.redstone));
        addSRecipe(gs(Item.redstone, 4), gs(Item.coal.itemID), gs(Block.cobblestone));
        addSRecipe(gs(DM, 4), DMBlock);
        addRecipe(gs(DMBlock), "DD", "DD", 'D', DM);
        addRecipe(gs(DMPickaxe), "DDD", " X ", " X ", 'D', DM, 'X', Item.diamond);
        addRecipe(gs(DMAxe), "DD ", "DX ", " X ", 'D', DM, 'X', Item.diamond);
        addRecipe(gs(DMShears), " D", "X ", 'D', DM, 'X', Item.diamond);
        addRecipe(gs(DMShovel), "D", "X", "X", 'D', DM, 'X', Item.diamond);
        addRecipe(gs(DMSword), "D", "D", "X", 'D', DM, 'X', Item.diamond);
        addRecipe(gs(DMHoe), "DD ", " X ", " X ", 'D', DM, 'X', Item.diamond);
        addRelicRecipe();
        addAlchemicalRecipe();
        addRingRecipe();
        addCovalenceRecipe();
        addFixRecipe();
        registerFuel();
    }
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        configFile = e.getSuggestedConfigurationFile();
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        GameRegistry.registerFuelHandler(new FuelHandler());
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException
    {
        MinecraftForge.setToolClass(DMPickaxe, "pickaxe", 4);
        MinecraftForge.setToolClass(DMShovel, "shovel", 4);

        if (ModLoader.isModLoaded("IC2"))
        {
            addIC2Recipe();
        }

        if (ModLoader.isModLoaded("Forestry"))
        {
            addForestryRecipe();
        }

        if (ModLoader.isModLoaded("Tubestuff"))
        {
            addTSRecipe();
        }
    }

    void naming(Object obj, String en, String jp)
    {
        LanguageRegistry.addName(obj, en);
        LanguageRegistry.instance().addNameForObject(obj, "ja_JP", jp);
    }

    void loadConfig()
    {
        Configuration config = new Configuration(configFile);
        config.load();
        /**
         * General
         */
        cutDown = config.get(config.CATEGORY_GENERAL, "Cut down from root", true).getBoolean(true);
        Debug = config.get(config.CATEGORY_GENERAL, "Debug Mode", false).getBoolean(false);
        /**
         * Items
         */
        IDPhil = config.getItem("Philosopher's stone ID", 174).getInt();
        IDAlchCoal = config.getItem("Alchemical Coal ID", 175).getInt();
        IDCov = config.getItem("Covalence Dust ID", 176).getInt();
        IDMobius = config.getItem("Mobius Fuel ID", 177).getInt();
        IDVolc = config.getItem("Volcanite ID", 178).getInt();
        IDEver = config.getItem("Evertide ID", 179).getInt();
        IDDM = config.getItem("Darkmatter ID", 180).getInt();
        IDSwift = config.getItem("Swiftwolf's Rending Gale ID", 181).getInt();
        IDFood = config.getItem("Dummy Food ID", 182).getInt();
        IDDD = config.getItem("Damage Disabler ID", 183).getInt();
        IDDMAxe = config.getItem("Darkmatter Axe ID", 184).getInt();
        IDDMSword = config.getItem("Darkmatter Sword ID", 185).getInt();
        IDDMPickaxe = config.getItem("Darkmatter Pickaxe ID", 186).getInt();
        IDDMShovel = config.getItem("Darkmatter Shovel ID", 187).getInt();
        IDIronband = config.getItem("Iron Band ID", 188).getInt();
        IDDMShears = config.getItem("Darkmatter Shears ID", 189).getInt();
        IDDMHoe = config.getItem("Darkmatter Hoe ID", 190).getInt();
        /**
         * Blocks
         */
        IDEETorch = config.get(config.CATEGORY_BLOCK, "Interdiction Torch ID", 200).getInt();
        IDDMB = config.get(config.CATEGORY_BLOCK, "Darkmatter Block ID", 201).getInt();
        config.save();
    }

    /*
     * recipe wrapper
     */
    public static void addRecipe(IRecipe recipe)
    {
        GameRegistry.addRecipe(recipe);
    }
    public static void addRecipe(ItemStack dest, Object...objs)
    {
        GameRegistry.addRecipe(dest, objs);
    }
    public static void addSRecipe(ItemStack dest, Object...objs)
    {
        GameRegistry.addShapelessRecipe(dest, objs);
    }
    /*
     * itemStack Generate Methods
     */
    public static ItemStack changeAmount(ItemStack is, int amount)
    {
        ItemStack ret = is.copy();
        ret.stackSize = amount;
        return ret;
    }
    public static ItemStack gs(Object obj)
    {
        if (obj instanceof Item)
        {
            return new ItemStack((Item)obj);
        }
        else if (obj instanceof Block)
        {
            return new ItemStack((Block)obj);
        }
        else if (obj instanceof ItemStack)
        {
            return (ItemStack)obj;
        }
        else if (obj instanceof Integer)
        {
            return gs(((Integer)obj).intValue());
        }
        else
        {
            return null;
        }
    }

    public static ItemStack gs(Object obj, int amount)
    {
        ItemStack is = gs(obj);

        if (is != null)
        {
            is.stackSize = amount;
        }

        return is;
    }

    public static ItemStack gs(Object obj, int amount, int damage)
    {
        ItemStack is = gs(obj);

        if (is != null)
        {
            is.stackSize = amount;

            if (damage == -1)
            {
                damage = 32767;
            }

            is.setItemDamage(damage);
        }

        return is;
    }
    public static ItemStack gs(int obj)
    {
        return new ItemStack(obj, 1, 32767);
    }

    public static ItemStack gs(int obj, int amount)
    {
        return new ItemStack(obj, amount, 32767);
    }

    public static ItemStack gs(int obj, int amount, int damage)
    {
        return new ItemStack(obj, amount, damage);
    }
    public static ItemStack getCov(Level lv)
    {
        if (lv == LOW)
        {
            return gs(Cov, 1, 0);
        }

        if (lv == MIDDLE)
        {
            return gs(Cov, 1, 1);
        }

        return gs(Cov, 1, 2);
    }
    /*
     * util
     */
    public static Object[] gti(Object...objs)
    {
        return objs;
    }
    public void addFixRecipe(Level level, Item item, int amount)
    {
        ItemStack cov = getCov(level);
        List<ItemStack> list = new ArrayList<ItemStack>();
        list.add(gs(item, 1, -1));

        for (int i = 0; i < amount; i++)
        {
            list.add(cov);
        }

        addRecipe(new FixRecipe(gs(item), list));
    }
    public void addFixRecipe(Level level, ItemStack item, int amount)
    {
        ItemStack cov = getCov(level);
        List<ItemStack> list = new ArrayList<ItemStack>();
        list.add(gs(item, 1, -1));

        for (int i = 0; i < amount; i++)
        {
            list.add(cov);
        }

        addRecipe(new FixRecipe(gs(item), list));
    }
    public void addExchange(ItemStack dest, Object obj, int amount)
    {
        List<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < amount; i++)
        {
            list.add(gs(obj));
        }

        list.add(gs(Phil));
        addSRecipe(dest, list.toArray());
    }
    public void addExchange(ItemStack dest, Object... objs)
    {
        List<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < objs.length; i++)
        {
            list.add(gs(objs[i]));
        }

        list.add(gs(Phil));
        addSRecipe(dest, list.toArray());
    }
    public List<ItemStack> getPhils(int count)
    {
        List<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < count; i++)
        {
            list.add(gs(Phil));
        }

        return list;
    }

    public void addRingRecipe(Object obj, int count)
    {
        for (int i = 0; i < count; i++)
        {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            list.add(gs(Phil));
            list.add(gs(obj, 1, i));
            addSRecipe(gs(obj, 1, (i + 1) % count), list.toArray());
        }
    }
    public void addRingRecipe(Object[] objs)
    {
        int l = objs.length;

        for (int i = 0; i < l; i++)
        {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            list.add(gs(Phil));
            list.add(gs(objs[i]));
            addSRecipe(gs(objs[(i + 1) % l]), list.toArray());
        }
    }
    public Block getTSBlock(String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException
    {
        return (Block)(Class.forName("mods.immibis.tubestuff.TubeStuff").getField(name).get(null));
    }
    /*
     * recipes
     */
    public void addRingRecipe()
    {
        addRingRecipe(Block.wood, 4);
        addRingRecipe(Block.sapling, 4);
        addRingRecipe(Block.stoneBrick, 4);
        addRingRecipe(Block.cloth, 0x10);
        addRingRecipe(Item.dyePowder, 0x10);
        addRingRecipe(gti(gs(Item.porkRaw), gs(Item.beefRaw), gs(Item.chickenRaw), gs(Item.fishRaw)));
        addRingRecipe(gti(gs(Item.porkCooked), gs(Item.beefCooked), gs(Item.chickenCooked), gs(Item.fishCooked)));
    }
    public ItemStack getFItem(String name)
    {
        return ItemInterface.getItem(name);
    }

    public void addForestryRecipe()
    {
        ItemStack fertilizer = getFItem("fertilizerCompound");
        ItemStack Ash = getFItem("ash");
        addRecipe(changeAmount(fertilizer, 2), "AAA", "ASA", "AAA", 'A', Ash, 'S', Block.sand);
    }
    public void addTSRecipe() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException
    {
        ItemStack BHC = gs(getTSBlock("block"), 1, 2);
        addRecipe(BHC, "DDD", "DCD", "DDD", 'D', DM, 'C', Block.chest);
    }
    public void addIC2Recipe()
    {
        addFixRecipe(LOW, Items.getItem("treetap").getItem(), 5);
        addFixRecipe(LOW, Items.getItem("hazmatHelmet").getItem(), 4);
        addFixRecipe(LOW, Items.getItem("hazmatChestplate").getItem(), 6);
        addFixRecipe(LOW, Items.getItem("hazmatLeggings").getItem(), 6);
        addFixRecipe(LOW, Items.getItem("hazmatBoots").getItem(), 6);
        addFixRecipe(MIDDLE, Items.getItem("bronzeHelmet").getItem(), 5);
        addFixRecipe(MIDDLE, Items.getItem("bronzeChestplate").getItem(), 8);
        addFixRecipe(MIDDLE, Items.getItem("bronzeLeggings").getItem(), 7);
        addFixRecipe(MIDDLE, Items.getItem("bronzeBoots").getItem(), 4);
        addFixRecipe(MIDDLE, Items.getItem("wrench").getItem(), 6);
        addFixRecipe(MIDDLE, Items.getItem("bronzePickaxe"), 3);
        addFixRecipe(MIDDLE, Items.getItem("bronzeAxe"), 3);
        addFixRecipe(MIDDLE, Items.getItem("bronzeSword"), 2);
        addFixRecipe(MIDDLE, Items.getItem("bronzeShovel"), 1);
        addFixRecipe(MIDDLE, Items.getItem("bronzeHoe"), 2);
        ItemStack dp = Items.getItem("diamondDust");
        Recipes.macerator.addRecipe(new RecipeInputItemStack(gs(Block.oreDiamond)), null, changeAmount(dp, 2));
        FurnaceRecipes.smelting().addSmelting(dp.itemID, dp.getItemDamage(), gs(Item.diamond), 10f);
        /*{
        	IMachineRecipeManager mac = Recipes.macerator;
        	List<ItemStack> list = getPhils(3);
        	Map<IRecipeInput,RecipeOutput> recipe = mac.getRecipes();
        	Set<Entry<IRecipeInput,RecipeOutput>> es = recipe.entrySet();
        	Entry<IRecipeInput,RecipeOutput> e = (Entry<IRecipeInput,RecipeOutput>)es.toArray()[0];
        	IRecipeInput in = e.getKey();
        	RecipeOutput op = e.getValue();
        	ItemStack input = in.getInputs().get(0);
        	for(int i = 0;i < in.getAmount();i++)
        	{
        		list.add(input);
        	}
        	addSRecipe(op.items.get(0),list.toArray());
        }*/
    }
    public void addCovalenceRecipe()
    {
        addSRecipe(changeAmount(getCov(LOW), 3), gs(Block.cobblestone), gs(Block.cobblestone), gs(Item.coal));
        addSRecipe(changeAmount(getCov(MIDDLE), 3), gs(Item.ingotIron), gs(Item.ingotIron), gs(Item.coal));
        addSRecipe(changeAmount(getCov(HIGH), 3), gs(Item.diamond), gs(Item.diamond), gs(Item.coal));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Block.cobblestone), gs(Block.cobblestone), gs(Block.cobblestone), gs(Item.redstone));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Item.ingotIron), gs(Item.ingotIron), gs(Item.ingotIron), gs(Item.redstone));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Item.diamond), gs(Item.diamond), gs(Item.diamond), gs(Item.redstone));
        addSRecipe(changeAmount(getCov(LOW), 2), gs(Block.cobblestone), gs(Item.glowstone), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 2), gs(Item.ingotIron), gs(Item.glowstone), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 2), gs(Item.diamond), gs(Item.glowstone), gs(Phil));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Block.cobblestone), gs(Block.cobblestone), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Item.ingotIron), gs(Item.ingotIron), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Item.diamond), gs(Item.diamond), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Block.cobblestone), gs(mobiusFuel), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Item.ingotIron), gs(mobiusFuel), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Item.diamond), gs(mobiusFuel), gs(Phil));
    }
    public void addSmeltingExchange()
    {
        FurnaceRecipes fr = FurnaceRecipes.smelting();
        /*
         * Non-Meta Smelting
         */
        HashMap<Integer, ItemStack> map1 = (HashMap<Integer, ItemStack>)fr.getSmeltingList();
        Set<Entry<Integer, ItemStack>> entryset = map1.entrySet();
        Iterator it = entryset.iterator();

        while (it.hasNext())
        {
            Entry<Integer, ItemStack> entry = (Entry<Integer, ItemStack>)it.next();
            int itemID = entry.getKey();
            ItemStack dest = entry.getValue();
            addSRecipe(dest, gs(Phil), gs(Phil), gs(itemID));
        }

        /*
         * Meta-Smelting
         */
        Map<List<Integer>, ItemStack> map2 = fr.getMetaSmeltingList();
        Set<Entry<List<Integer>, ItemStack>> entryset2 = map2.entrySet();
        it = entryset2.iterator();

        while (it.hasNext())
        {
            Entry<List<Integer>, ItemStack> entry = (Entry<List<Integer>, ItemStack>)it.next();
            List<Integer> l = entry.getKey();
            int itemID = l.get(0);
            int damage = l.get(1);
            addSRecipe(entry.getValue(), gs(Phil), gs(Phil), gs(itemID, 1, damage));
        }
    }
    public void addFixRecipe()
    {
        addFixRecipe(LOW, Item.shovelWood, 1);
        addFixRecipe(LOW, Item.hoeWood, 2);
        addFixRecipe(LOW, Item.swordWood, 2);
        addFixRecipe(LOW, Item.axeWood, 3);
        addFixRecipe(LOW, Item.pickaxeWood, 3);
        addFixRecipe(LOW, Item.shovelStone, 1);
        addFixRecipe(LOW, Item.hoeStone, 2);
        addFixRecipe(LOW, Item.swordStone, 2);
        addFixRecipe(LOW, Item.axeStone, 3);
        addFixRecipe(LOW, Item.pickaxeStone, 3);
        addFixRecipe(LOW, Item.bow, 2);
        addFixRecipe(LOW, Item.fishingRod, 2);
        addFixRecipe(MIDDLE, Item.shovelIron, 1);
        addFixRecipe(MIDDLE, Item.hoeIron, 2);
        addFixRecipe(MIDDLE, Item.swordIron, 2);
        addFixRecipe(MIDDLE, Item.axeIron, 3);
        addFixRecipe(MIDDLE, Item.pickaxeIron, 3);
        addFixRecipe(MIDDLE, Item.shovelGold, 1);
        addFixRecipe(MIDDLE, Item.hoeGold, 2);
        addFixRecipe(MIDDLE, Item.swordGold, 2);
        addFixRecipe(MIDDLE, Item.axeGold, 3);
        addFixRecipe(MIDDLE, Item.pickaxeGold, 3);
        addFixRecipe(MIDDLE, Item.flintAndSteel, 1);
        addFixRecipe(MIDDLE, Item.shears, 2);
        addFixRecipe(HIGH, Item.shovelDiamond, 1);
        addFixRecipe(HIGH, Item.hoeDiamond, 2);
        addFixRecipe(HIGH, Item.swordDiamond, 2);
        addFixRecipe(HIGH, Item.axeDiamond, 3);
        addFixRecipe(HIGH, Item.pickaxeDiamond, 3);
        addFixRecipe(LOW, Item.helmetLeather, 5);
        addFixRecipe(MIDDLE, Item.helmetIron, 5);
        addFixRecipe(MIDDLE, Item.helmetGold, 5);
        addFixRecipe(HIGH, Item.helmetDiamond, 5);
        addFixRecipe(LOW, Item.plateLeather, 8);
        addFixRecipe(MIDDLE, Item.plateIron, 8);
        addFixRecipe(MIDDLE, Item.plateGold, 8);
        addFixRecipe(HIGH, Item.plateDiamond, 8);
        addFixRecipe(LOW, Item.legsLeather, 7);
        addFixRecipe(MIDDLE, Item.legsIron, 7);
        addFixRecipe(MIDDLE, Item.legsGold, 7);
        addFixRecipe(HIGH, Item.legsDiamond, 7);
        addFixRecipe(LOW, Item.bootsLeather, 4);
        addFixRecipe(MIDDLE, Item.bootsIron, 4);
        addFixRecipe(MIDDLE, Item.bootsGold, 4);
        addFixRecipe(HIGH, Item.bootsDiamond, 4);
    }
    public void addAlchemicalRecipe()
    {
        addRecipe(gs(DM), "DBD", "BPB", "DBD", 'D', Block.blockDiamond, 'B', mobiusFuel, 'P', Phil);
        addRecipe(gs(DM), "DBD", "BPB", "DBD", 'B', Block.blockDiamond, 'D', mobiusFuel, 'P', Phil);
        addExchange(gs(Block.blockDiamond, 4), DM);
        addExchange(gs(Item.coal), gs(Item.coal, 1, 1));
        // Dirt
        addExchange(gs(Block.sand), Block.dirt);
        addExchange(gs(Block.blockClay), Block.dirt, 2);
        addExchange(gs(Block.sand, 3), Block.dirt, 3);
        addExchange(gs(Block.cobblestone), Block.dirt, 4);
        addExchange(gs(Block.sand, 5), Block.dirt, 5);
        addExchange(gs(Block.blockClay, 3), Block.dirt, 6);
        addExchange(gs(Block.sand, 7), Block.dirt, 7);
        addExchange(gs(Block.cobblestone, 2), Block.dirt, 8);
        //Sand
        addExchange(gs(Block.dirt), Block.sand, 1);
        addExchange(gs(Block.blockClay), Block.sand, 2);
        addExchange(gs(Block.dirt, 3), Block.sand, 3);
        addExchange(gs(Block.blockClay, 2), Block.sand, 4);
        addExchange(gs(Block.dirt, 5), Block.sand, 5);
        addExchange(gs(Block.blockClay, 3), Block.sand, 6);
        addExchange(gs(Block.dirt, 7), Block.sand, 7);
        addExchange(gs(Block.blockClay, 4), Block.sand, 8);
        //sand + glowStoneDust
        addExchange(gs(Block.slowSand), Block.sand, Item.glowstone);
        addExchange(gs(Block.slowSand, 2), Block.sand, Item.glowstone, Block.sand, Item.glowstone);
        addExchange(gs(Block.slowSand, 3), Block.sand, Item.glowstone, Block.sand, Item.glowstone, Block.sand, Item.glowstone);
        addExchange(gs(Block.slowSand, 4), Block.sand, Item.glowstone, Block.sand, Item.glowstone, Block.sand, Item.glowstone, Block.sand, Item.glowstone);
        //cobbleStone + glowStone
        addExchange(gs(Block.slowSand, 4), Block.cobblestone, Block.glowStone);
        addExchange(gs(Block.slowSand, 8), Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone);
        addExchange(gs(Block.slowSand, 12), Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone);
        addExchange(gs(Block.slowSand, 16), Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone, Block.cobblestone, Block.glowStone);
        //Sandstone
        addExchange(gs(Block.sand, 4), Block.sandStone, 1);
        addExchange(gs(Block.sand, 8), Block.sandStone, 2);
        addExchange(gs(Block.sand, 12), Block.sandStone, 3);
        addExchange(gs(Block.cobblestone, 4), Block.sandStone, 4);
        addExchange(gs(Block.sand, 20), Block.sandStone, 5);
        addExchange(gs(Block.sand, 24), Block.sandStone, 6);
        addExchange(gs(Block.sand, 28), Block.sandStone, 7);
        addExchange(gs(Block.oreRedstone, 1), Block.sandStone, 8);
        //dirt + sand
        addExchange(gs(Block.gravel), Block.dirt, Block.sand);
        addExchange(gs(Block.gravel, 2), Block.dirt, Block.sand, Block.dirt, Block.sand);
        addExchange(gs(Block.gravel, 3), Block.dirt, Block.sand, Block.dirt, Block.sand, Block.dirt, Block.sand);
        addExchange(gs(Block.gravel, 4), Block.dirt, Block.sand, Block.dirt, Block.sand, Block.dirt, Block.sand, Block.dirt, Block.sand);
        //gravel
        addExchange(gs(Block.sand, 2), Block.gravel, 1);
        addExchange(gs(Block.dirt, 4), Block.gravel, 2);
        addExchange(gs(Item.flint, 2), Block.gravel, 3);
        addExchange(gs(Block.dirt, 8), Block.gravel, 4);
        addExchange(gs(Block.sand, 10), Block.gravel, 5);
        addExchange(gs(Item.flint, 4), Block.gravel, 6);
        addExchange(gs(Block.sand, 14), Block.gravel, 7);
        addExchange(gs(Block.dirt, 16), Block.gravel, 8);
        //flint
        addExchange(gs(Item.clay, 3), Item.flint, 1);
        addExchange(gs(Block.gravel, 3), Item.flint, 2);
        addExchange(gs(Item.clay, 9), Item.flint, 3);
        addExchange(gs(Block.gravel, 6), Item.flint, 4);
        addExchange(gs(Item.clay, 15), Item.flint, 5);
        addExchange(gs(Block.gravel, 9), Item.flint, 6);
        addExchange(gs(Item.clay, 21), Item.flint, 7);
        addExchange(gs(Block.gravel, 12), Item.flint, 8);
        //clayBlock
        addExchange(gs(Item.clay, 4), Block.blockClay, 1);
        addExchange(gs(Block.cobblestone), Block.blockClay, 2);
        addExchange(gs(Item.clay, 12), Block.blockClay, 3);
        addExchange(gs(Block.cobblestone, 2), Block.blockClay, 4);
        addExchange(gs(Item.clay, 20), Block.blockClay, 5);
        addExchange(gs(Block.cobblestone, 3), Block.blockClay, 6);
        addExchange(gs(Item.clay, 28), Block.blockClay, 7);
        addExchange(gs(Block.cobblestone, 4), Block.blockClay, 8);
        //clay
        addExchange(gs(Block.dirt), Item.clay, 2);
        addExchange(gs(Block.dirt, 2), Item.clay, 4);
        addExchange(gs(Block.dirt, 3), Item.clay, 6);
        addExchange(gs(Block.dirt, 4), Item.clay, 8);
        //cobblestone
        addExchange(gs(Block.dirt, 4), Block.cobblestone, 1);
        addExchange(gs(Block.blockClay, 4), Block.cobblestone, 2);
        addExchange(gs(Block.dirt, 12), Block.cobblestone, 3);
        addExchange(gs(Block.blockClay, 8), Block.cobblestone, 4);
        addExchange(gs(Block.dirt, 20), Block.cobblestone, 5);
        addExchange(gs(Block.blockClay, 12), Block.cobblestone, 6);
        addExchange(gs(Block.dirt, 28), Block.cobblestone, 7);
        addExchange(gs(Block.oreRedstone, 1), Block.cobblestone, 8);
        //cobblestone + redstone
        addExchange(gs(Block.netherrack), Block.cobblestone, Item.redstone);
        addExchange(gs(Block.netherrack, 2), Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone);
        addExchange(gs(Block.netherrack, 3), Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone);
        addExchange(gs(Block.netherrack, 4), Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone, Block.cobblestone, Item.redstone);
        //stone
        addExchange(gs(Block.cobblestone), Block.stone, 1);
        addExchange(gs(Block.cobblestone, 2), Block.stone, 2);
        addExchange(gs(Block.cobblestone, 3), Block.stone, 3);
        addExchange(gs(Block.cobblestone, 4), Block.stone, 4);
        addExchange(gs(Block.cobblestone, 5), Block.stone, 5);
        addExchange(gs(Block.cobblestone, 6), Block.stone, 6);
        addExchange(gs(Block.cobblestone, 7), Block.stone, 7);
        addExchange(gs(Block.oreRedstone), Block.stone, 8);
        //redstoneOre
        addExchange(gs(Block.cobblestone, 8), Block.oreRedstone, 1);
        addExchange(gs(Item.redstone, 9), Block.oreRedstone, 2);
        addExchange(gs(Block.cobblestone, 24), Block.oreRedstone, 3);
        addExchange(gs(Block.oreIron), Block.oreRedstone, 4);
        addExchange(gs(Block.cobblestone, 40), Block.oreRedstone, 5);
        addExchange(gs(Item.redstone, 27), Block.oreRedstone, 6);
        addExchange(gs(Block.cobblestone, 56), Block.oreRedstone, 7);
        addExchange(gs(Block.oreIron, 2), Block.oreRedstone, 8);
        //ironOre
        addExchange(gs(Block.cobblestone, 32), Block.oreIron, 1);
        addExchange(gs(Block.cobblestone, 64), Block.oreIron, 2);
        addExchange(gs(Block.oreRedstone, 12), Block.oreIron, 3);
        addExchange(gs(Block.oreGold, 1), Block.oreIron, 4);
        addExchange(gs(Block.oreRedstone, 20), Block.oreIron, 5);
        addExchange(gs(Block.oreRedstone, 24), Block.oreIron, 6);
        addExchange(gs(Block.oreRedstone, 28), Block.oreIron, 7);
        addExchange(gs(Block.oreGold, 2), Block.oreIron, 8);
        //goldOre
        addExchange(gs(Block.oreIron, 4), Block.oreGold, 1);
        addExchange(gs(Block.oreIron, 8), Block.oreGold, 2);
        addExchange(gs(Block.oreIron, 12), Block.oreGold, 3);
        addExchange(gs(Block.oreDiamond, 1), Block.oreGold, 4);
        addExchange(gs(Block.oreIron, 20), Block.oreGold, 5);
        addExchange(gs(Block.oreIron, 24), Block.oreGold, 6);
        addExchange(gs(Block.oreIron, 28), Block.oreGold, 7);
        addExchange(gs(Block.oreDiamond, 2), Block.oreGold, 8);
        //diamondOre
        addExchange(gs(Block.oreGold, 4), Block.oreDiamond, 1);
        addExchange(gs(Block.oreGold, 8), Block.oreDiamond, 2);
        addExchange(gs(Block.oreGold, 12), Block.oreDiamond, 3);
        addExchange(gs(Block.oreGold, 16), Block.oreDiamond, 4);
        addExchange(gs(Block.oreGold, 20), Block.oreDiamond, 5);
        addExchange(gs(Block.oreGold, 24), Block.oreDiamond, 6);
        addExchange(gs(Block.oreGold, 28), Block.oreDiamond, 7);
        addExchange(gs(Block.oreGold, 32), Block.oreDiamond, 8);
        //soulSand
        addExchange(gs(Block.sand, 2), Block.slowSand, 1);
        addExchange(gs(Block.sand, 4), Block.slowSand, 2);
        addExchange(gs(Block.sand, 6), Block.slowSand, 3);
        addExchange(gs(Block.sand, 8), Block.slowSand, 4);
        addExchange(gs(Block.sand, 10), Block.slowSand, 5);
        addExchange(gs(Block.sand, 12), Block.slowSand, 6);
        addExchange(gs(Block.sand, 14), Block.slowSand, 7);
        addExchange(gs(Block.sand, 16), Block.slowSand, 8);
        //Netherrack
        addExchange(gs(Block.stone), Block.netherrack, 1);
        addExchange(gs(Block.stone, 2), Block.netherrack, 2);
        addExchange(gs(Block.stone, 3), Block.netherrack, 3);
        addExchange(gs(Block.stone, 4), Block.netherrack, 4);
        addExchange(gs(Block.stone, 5), Block.netherrack, 5);
        addExchange(gs(Block.stone, 6), Block.netherrack, 6);
        addExchange(gs(Block.stone, 7), Block.netherrack, 7);
        addExchange(gs(Block.oreRedstone), Block.netherrack, 8);
        //glass
        addExchange(gs(Block.sand, 3), Block.glass, 2);
        addExchange(gs(Block.sand, 6), Block.glass, 4);
        addExchange(gs(Block.sand, 9), Block.glass, 6);
        addExchange(gs(Block.ice), Block.glass, 8);
        //ice
        addExchange(gs(Block.glass, 8), Block.ice, 1);
        addExchange(gs(Block.glass, 16), Block.ice, 2);
        addExchange(gs(Block.glass, 24), Block.ice, 3);
        addExchange(gs(Block.glass, 32), Block.ice, 4);
        addExchange(gs(Block.glass, 40), Block.ice, 5);
        addExchange(gs(Block.glass, 48), Block.ice, 6);
        addExchange(gs(Block.glass, 56), Block.ice, 7);
        addExchange(gs(Item.dyePowder, 1, 4), Block.ice, 8);
        //lapisLazuli
        addExchange(gs(Block.ice, 16), gs(Item.dyePowder, 1, 4), 2);
        addExchange(gs(Block.obsidian, 3), gs(Item.dyePowder, 1, 4), 3);
        addExchange(gs(Block.ice, 32), gs(Item.dyePowder, 1, 4), 4);
        addExchange(gs(Block.obsidian, 5), gs(Item.dyePowder, 1, 4), 5);
        addExchange(gs(Block.ice, 48), gs(Item.dyePowder, 1, 4), 6);
        addExchange(gs(Block.obsidian, 7), gs(Item.dyePowder, 1, 4), 7);
        addExchange(gs(Block.oreLapis), gs(Item.dyePowder, 1, 4), 8);
        //lapisBlock
        addExchange(gs(Block.obsidian, 9), Block.blockLapis, 1);
        addExchange(gs(Block.obsidian, 18), Block.blockLapis, 2);
        addExchange(gs(Block.obsidian, 27), Block.blockLapis, 3);
        addExchange(gs(Block.obsidian, 36), Block.blockLapis, 4);
        addExchange(gs(Block.obsidian, 45), Block.blockLapis, 5);
        addExchange(gs(Block.obsidian, 54), Block.blockLapis, 6);
        addExchange(gs(Block.obsidian, 63), Block.blockLapis, 7);
        addExchange(gs(Item.diamond, 2), Block.blockLapis, Block.blockLapis, Block.blockLapis, Block.blockLapis, Block.blockLapis, Block.blockLapis, Block.blockLapis, Block.obsidian);
        //obsidian
        addExchange(gs(Item.dyePowder, 1, 4), Block.obsidian);
        addExchange(gs(Item.dyePowder, 2, 4), Block.obsidian, 2);
        addExchange(gs(Item.dyePowder, 3, 4), Block.obsidian, 3);
        addExchange(gs(Item.dyePowder, 4, 4), Block.obsidian, 4);
        addExchange(gs(Item.dyePowder, 5, 4), Block.obsidian, 5);
        addExchange(gs(Item.dyePowder, 6, 4), Block.obsidian, 6);
        addExchange(gs(Item.dyePowder, 7, 4), Block.obsidian, 7);
        addExchange(gs(Block.oreLapis), Block.obsidian, 8);
        //lapisOre
        addExchange(gs(Item.dyePowder, 8, 4), Block.oreLapis, 1);
        addExchange(gs(Block.obsidian, 16), Block.oreLapis, 2);
        addExchange(gs(Item.dyePowder, 24, 4), Block.oreLapis, 3);
        addExchange(gs(Item.diamond), Block.oreLapis, 4);
        addExchange(gs(Item.dyePowder, 40, 4), Block.oreLapis, 5);
        addExchange(gs(Block.obsidian, 48), Block.oreLapis, 6);
        addExchange(gs(Item.dyePowder, 56, 4), Block.oreLapis, 7);
        addExchange(gs(Item.diamond, 2), Block.oreLapis, 8);
        //iron
        addExchange(gs(Item.glowstone, 6), Item.ingotIron, 1);
        addExchange(gs(Block.glowStone, 3), Item.ingotIron, 2);
        addExchange(gs(Item.glowstone, 18), Item.ingotIron, 3);
        addExchange(gs(Item.ingotGold), Item.ingotIron, 4);
        addExchange(gs(Item.glowstone, 30), Item.ingotIron, 5);
        addExchange(gs(Block.glowStone, 9), Item.ingotIron, 6);
        addExchange(gs(Item.glowstone, 42), Item.ingotIron, 7);
        addExchange(gs(Item.ingotGold, 2), Item.ingotIron, 8);
        //gold
        addExchange(gs(Item.ingotIron, 4), Item.ingotGold, 1);
        addExchange(gs(Item.ingotIron, 8), Item.ingotGold, 2);
        addExchange(gs(Item.ingotIron, 12), Item.ingotGold, 3);
        addExchange(gs(Item.diamond), Item.ingotGold, 4);
        addExchange(gs(Item.ingotIron, 20), Item.ingotGold, 5);
        addExchange(gs(Item.ingotIron, 24), Item.ingotGold, 6);
        addExchange(gs(Item.ingotIron, 28), Item.ingotGold, 7);
        addExchange(gs(Item.diamond, 2), Item.ingotGold, 8);

        //diamond
        for (int i = 1; i <= 8; i++)
        {
            addExchange(gs(Item.ingotGold, 4 * i), Item.diamond, i);
        }

        //cobblestone + seed
        addExchange(gs(Block.cobblestoneMossy), Block.cobblestone, Item.seeds);
        addExchange(gs(Block.cobblestoneMossy, 2), Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds);
        addExchange(gs(Block.cobblestoneMossy, 3), Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds);
        addExchange(gs(Block.cobblestoneMossy, 4), Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds, Block.cobblestone, Item.seeds);
        //dirt + seed
        addExchange(gs(Block.grass), Block.dirt, Item.seeds);
        addExchange(gs(Block.grass, 2), Block.dirt, Item.seeds, Block.dirt, Item.seeds);
        addExchange(gs(Block.grass, 3), Block.dirt, Item.seeds, Block.dirt, Item.seeds, Block.dirt, Item.seeds);
        addExchange(gs(Block.grass, 4), Block.dirt, Item.seeds, Block.dirt, Item.seeds, Block.dirt, Item.seeds, Block.dirt, Item.seeds);
        //Gold + apple
        addExchange(gs(Item.appleGold), Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.appleRed);
        //coal
        addExchange(gs(Block.cobblestone), Item.coal, 1);
        addExchange(gs(Item.redstone, 3), Item.coal, 2);
        addExchange(gs(Block.cobblestone, 2), Item.coal, 3);
        addExchange(gs(Item.glowstone), Item.coal, 4);
        addExchange(gs(Block.cobblestone, 5), Item.coal, 5);
        addExchange(gs(Item.redstone, 9), Item.coal, 6);
        addExchange(gs(Block.cobblestone, 7), Item.coal, 7);
        addExchange(gs(Item.glowstone, 2), Item.coal, 8);
        //sugar
        addExchange(gs(Item.redstone), Item.sugar, 1);
        addExchange(gs(Item.redstone, 2), Item.sugar, 2);
        addExchange(gs(Item.coal, 2), Item.sugar, 3);
        addExchange(gs(Item.gunpowder), Item.sugar, 4);
        addExchange(gs(Item.redstone, 5), Item.sugar, 5);
        addExchange(gs(Item.glowstone), Item.sugar, 6);
        addExchange(gs(Item.redstone, 7), Item.sugar, 7);
        addExchange(gs(Item.gunpowder, 2), Item.sugar, 8);
        //redstone
        addExchange(gs(Item.coal, 2), Item.redstone, 3);
        addExchange(gs(Item.gunpowder), Item.redstone, 4);
        addExchange(gs(Item.glowstone), Item.redstone, 6);
        addExchange(gs(Item.gunpowder, 2), Item.redstone, 8);
        //redstone + coal
        addExchange(gs(Item.gunpowder), Item.redstone, Item.coal, Item.coal);
        addExchange(gs(Item.gunpowder, 2), Item.redstone, Item.coal, Item.coal, Item.redstone, Item.coal, Item.coal);
        //gunpowder
        addExchange(gs(Item.redstone, 4), Item.gunpowder, 1);
        addExchange(gs(Item.redstone, 8), Item.gunpowder, 2);
        addExchange(gs(Item.glowstone, 2), Item.gunpowder, 3);
        addExchange(gs(Item.redstone, 16), Item.gunpowder, 4);
        addExchange(gs(Item.redstone, 20), Item.gunpowder, 5);
        addExchange(gs(Block.glowStone, 4), Item.gunpowder, 6);
        addExchange(gs(Item.redstone, 28), Item.gunpowder, 7);
        addExchange(gs(Item.redstone, 32), Item.gunpowder, 8);
        //glowstone powder
        addExchange(gs(Item.redstone, 6), Item.glowstone, 1);
        addExchange(gs(Item.gunpowder, 3), Item.glowstone, 2);
        addExchange(gs(Item.redstone, 18), Item.glowstone, 3);
        addExchange(gs(Item.gunpowder, 6), Item.glowstone, 4);
        addExchange(gs(Item.redstone, 30), Item.glowstone, 5);
        addExchange(gs(Item.gunpowder, 9), Item.glowstone, 6);
        addExchange(gs(Item.redstone, 42), Item.glowstone, 7);
        addExchange(gs(Item.gunpowder, 12), Item.glowstone, 8);
        //glowstone powder
        addExchange(gs(Item.glowstone, 4), Block.glowStone, 1);
        addExchange(gs(Item.glowstone, 8), Block.glowStone, 2);
        addExchange(gs(Item.glowstone, 12), Block.glowStone, 3);
        addExchange(gs(Block.oreIron, 2), Block.glowStone, 4);
        addExchange(gs(Item.glowstone, 20), Block.glowStone, 5);
        addExchange(gs(Item.glowstone, 24), Block.glowStone, 6);
        addExchange(gs(Item.glowstone, 28), Block.glowStone, 7);
        addExchange(gs(Block.oreGold), Block.glowStone, 8);
        //water recipe
        addExchange(gs(Item.bucketWater), Block.cactus, Block.cactus, Item.bucketEmpty);
        addExchange(gs(Item.bucketWater), Block.ice, Item.bucketEmpty);
        addSRecipe(gs(Item.bucketWater), gs(Ever), gs(Item.bucketEmpty));
        //lava recipe
        addExchange(gs(Item.bucketLava), Item.coal, Item.redstone, Item.bucketEmpty);
        addSRecipe(gs(Item.bucketLava), gs(Volc), gs(Item.redstone), gs(Item.bucketEmpty));
        //sand + gunpowder
        addExchange(gs(Block.tnt), Item.gunpowder, Item.gunpowder, Block.sand);
        addExchange(gs(Block.tnt, 2), Item.gunpowder, Item.gunpowder, Block.sand, Item.gunpowder, Item.gunpowder, Block.sand);
        //Alchemical Coal Recipe
        addExchange(gs(AlchCoal), Item.coal, Item.glowstone, Item.bucketLava);
        addExchange(gs(AlchCoal), Item.coal, Item.glowstone, Volc);
        addExchange(gs(AlchCoal, 2), Item.coal, Item.glowstone, Item.coal, Item.glowstone, Item.bucketLava);
        addExchange(gs(AlchCoal, 2), Item.coal, Item.glowstone, Item.coal, Item.glowstone, Volc);
        addExchange(gs(AlchCoal, 3), Item.coal, Item.coal, Item.coal, Item.glowstone, Item.glowstone, Item.glowstone, Item.bucketLava);
        addExchange(gs(AlchCoal, 3), Item.coal, Item.coal, Item.coal, Item.glowstone, Item.glowstone, Item.glowstone, Volc);
        //Mobius Fuel Recipe
        addExchange(gs(mobiusFuel), AlchCoal, 3);
        addExchange(gs(mobiusFuel, 2), AlchCoal, 6);

        for (int i = 1; i <= 8; i++)
        {
            addExchange(gs(AlchCoal, 3 * i), mobiusFuel, i);
        }

        //Wood Recipe
        addExchange(gs(Block.wood), Block.cactus, 2);
        addExchange(gs(Block.wood, 2), Block.cactus, 4);
        addExchange(gs(Block.wood, 3), Block.cactus, 6);
        addExchange(gs(Block.wood, 4), Block.cactus, 8);
        //mushroom Recipe
        addExchange(gs(Block.mushroomRed), Item.glowstone, Block.plantRed);
        addExchange(gs(Block.mushroomRed, 2), Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed);
        addExchange(gs(Block.mushroomRed, 3), Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed);
        addExchange(gs(Block.mushroomRed, 4), Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed, Item.glowstone, Block.plantRed);
        addExchange(gs(Block.mushroomBrown), Item.glowstone, Block.plantRed);
        addExchange(gs(Block.mushroomBrown, 2), Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow);
        addExchange(gs(Block.mushroomBrown, 3), Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow);
        addExchange(gs(Block.mushroomBrown, 4), Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow, Item.glowstone, Block.plantYellow);
        //apple recipe
        addExchange(gs(Item.appleRed), Item.redstone, Block.plantRed);
        addExchange(gs(Item.appleRed, 2), Item.redstone, Block.plantRed, Item.redstone, Block.plantRed);
        addExchange(gs(Item.appleRed, 3), Item.redstone, Block.plantRed, Item.redstone, Block.plantRed, Item.redstone, Block.plantRed);
        addExchange(gs(Item.appleRed, 4), Item.redstone, Block.plantRed, Item.redstone, Block.plantRed, Item.redstone, Block.plantRed, Item.redstone, Block.plantRed);
        //pumpkin recipe
        addExchange(gs(Block.pumpkin), Block.plantYellow, Block.plantRed);
        addExchange(gs(Block.pumpkin, 2), Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed);
        addExchange(gs(Block.pumpkin, 3), Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed);
        addExchange(gs(Block.pumpkin, 4), Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed, Block.plantYellow, Block.plantRed);
        //slimeball recipe
        addSRecipe(gs(Item.slimeBall), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Item.bucketWater));
        addSRecipe(gs(Item.slimeBall), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Ever));
        addSRecipe(gs(Item.slimeBall, 2), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Item.bucketWater));
        addSRecipe(gs(Item.slimeBall, 2), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Item.seeds), gs(Item.reed), gs(Block.sapling), gs(Ever));
        //material block recipe
        addExchange(gs(Block.blockGold), Block.blockIron, 4);
        addExchange(gs(Block.blockGold, 2), Block.blockIron, 8);
        addExchange(gs(Block.blockDiamond), Block.blockGold, 4);
        addExchange(gs(Block.blockDiamond, 2), Block.blockGold, 8);

        //dye Recipe
        for (int i = 0; i < 16; i++)
        {
            addExchange(gs(Item.dyePowder, 2, 15 - i), gs(Block.cloth, 1, i), 3);
        }
    }
    public void addRelicRecipe()
    {
        addRecipe(gs(EETorch, 2), "RDR", "DPD", "GGG", 'R', Block.torchRedstoneActive, 'D', Item.diamond, 'P', Phil, 'G', Item.glowstone);
        addRecipe(gs(Ever), "WWW", "DDD", "WWW", 'W', Item.bucketWater, 'D', DM);
        addRecipe(gs(Volc), "WWW", "DDD", "WWW", 'W', Item.bucketLava, 'D', DM);
        addRecipe(gs(Ever), "IDI", "WEW", "WIW", 'W', Item.bucketWater, 'I', Item.ingotIron, 'D', Item.diamond, 'E', Ever);
        addRecipe(gs(Volc), "IDI", "WEW", "WIW", 'W', Item.bucketLava, 'I', Item.ingotIron, 'D', Item.diamond, 'E', Volc);
        addRecipe(gs(Ever), "IDI", "WEW", "WIW", 'W', Ever, 'I', Item.ingotIron, 'D', Item.diamond, 'E', Ever);
        addRecipe(gs(Volc), "IDI", "WEW", "WIW", 'W', Volc, 'I', Item.ingotIron, 'D', Item.diamond, 'E', Volc);
        addRecipe(gs(ironband), "III", "ILI", "III", 'I', Item.ingotIron, 'L', Item.bucketLava);
        addRecipe(gs(ironband), "III", "ILI", "III", 'I', Item.ingotIron, 'L', Volc);
        addRecipe(gs(Swift), "DFD", "FBF", "DFD", 'D', DM, 'F', Item.feather, 'B', ironband);
        addRecipe(gs(DD), "DDD", "DBD", "DDD", 'D', DM, 'B', ironband);
    }
    public void registerFuel()
    {
        FuelHandler.register(IDAlchCoal, 1600 * 6);
        FuelHandler.register(IDMobius, 1600 * 6 * 4);
    }
}
