package ee.features;

import static ee.features.Level.*;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.blocks.BlockEE;
import ee.features.blocks.BlockEETorch;
import ee.features.items.ItemCovalenceDust;
import ee.features.items.ItemDMAxe;
import ee.features.items.ItemDMHoe;
import ee.features.items.ItemDMPickaxe;
import ee.features.items.ItemDMShears;
import ee.features.items.ItemDMShovel;
import ee.features.items.ItemDMSword;
import ee.features.items.ItemDamageDisabler;
import ee.features.items.ItemEE;
import ee.features.items.ItemEvertide;
import ee.features.items.ItemPhilToolBase;
import ee.features.items.ItemPhilToolFMP;
import ee.features.items.ItemPhilToolGT;
import ee.features.items.ItemPhilToolGTFMP;
import ee.features.items.ItemPhilosophersStone;
import ee.features.items.ItemRepairCharm;
import ee.features.items.ItemSwiftwolfsRing;
import ee.features.items.ItemVolcanite;
import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_Utility;

@Mod(modid = "EELimited",name = "EELimited",version = "rev.a1")
public class EELimited {

	public static EELimited instance;
    public static CreativeTabs TabEE = new CreativeTabEE();

    public static Achievement getPhil;
    public static Achievement getDM;

    public File suggestedConfig;
    public Logger log = LogManager.getLogger("EELimited");
    public Logger logFinder = LogManager.getLogger("EEItemFinder");

    /**
     * Options
     */
    public static boolean cutDown;
    public static boolean Debug;
    public static boolean Hard;
    public static boolean noBats;
    public static boolean noTeleport;
    public static boolean dontCarry;
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
    public static Item NIron;
    public static Item PhilTool;
    public static Item Repair;
    /**
     * Blocks
     */
    public static Block EETorch;
    public static Block DMBlock;
    public static Block AlchChest;
    /**
     * Addon
     */
    public static boolean loadGT,loadFMP,loadTinCo;
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(this,new GuiHandler());
    	instance = this;
    	EEProxy.Init(FMLClientHandler.instance().getClient(),this);
    	loadConfig();
    	Blocks.command_block.setCreativeTab(CreativeTabs.tabRedstone);
    	RecipeSorter.register("eelimited.fixrecipe",FixRecipe.class,Category.SHAPELESS,"after:minecraft:shapeless");
    	Phil = new ItemPhilosophersStone();
    	Volc = new ItemVolcanite();
    	Ever = new ItemEvertide();
    	Cov = new ItemCovalenceDust();
    	Swift = new ItemSwiftwolfsRing();
    	AlchCoal = new ItemEE(NameRegistry.AlchemicalCoal);
    	mobiusFuel = new ItemEE(NameRegistry.Mobius);
    	DMAxe = new ItemDMAxe();
    	DMHoe = new ItemDMHoe();
    	DMSword = new ItemDMSword();
    	DMShovel = new ItemDMShovel();
    	DMShears = new ItemDMShears();
    	DMPickaxe = new ItemDMPickaxe();
    	DD = new ItemDamageDisabler();
    	DM = new ItemEE(NameRegistry.DM);
    	DMBlock = new BlockEE(Material.rock,NameRegistry.DMBlock).setHardness(500);
    	EETorch = new BlockEETorch();
    	ironband = new ItemEE(NameRegistry.IronBand);
    	PhilTool = new ItemPhilToolBase();
    	Repair = new ItemRepairCharm();
    	//AlchChest = new BlockAlchChest();
    	if(Hard)
    	{
    		removeRecipes();
    	}
    	addRecipe(gs(Items.water_bucket), "S", "B", 'S', Items.snowball, 'B', Items.bucket);
        addRecipe(gs(Items.lava_bucket), "GRG", " B ", 'G', Items.gunpowder, 'R', Items.redstone, 'B', Items.bucket);
        addRecipe(gs(Items.milk_bucket), "B", "W", "E", 'B', gs(Items.dye, 1, 0xF), 'W', Items.water_bucket, 'E', Items.bucket);
        addRecipe(gs(Phil), "RGR", "GSG", "RGR", 'R', Items.redstone, 'G', Items.glowstone_dust, 'S', Items.slime_ball);
        addRecipe(gs(Phil), "RGR", "GSG", "RGR", 'R', Items.glowstone_dust, 'G', Items.redstone, 'S', Items.slime_ball);
        addSRecipe(gs(Phil),gs(Phil),gs(Items.slime_ball),gs(Items.glowstone_dust),gs(Items.redstone));
        addSRecipe(gs(Items.glowstone_dust, 4), gs(Items.coal), gs(Items.redstone));
        addSRecipe(gs(Items.redstone, 4), gs(Items.coal), gs(Blocks.cobblestone));
        addSRecipe(gs(DM, 4), gs(DMBlock));
        addRecipe(gs(DMBlock), "DD", "DD", 'D', DM);
        ItemStack is = new ItemStack(DMPickaxe);
        is.addEnchantment(Enchantment.fortune,10);
        addRecipe(is, "DDD", " X ", " X ", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMAxe), "DD ", "DX ", " X ", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMShears), " D", "X ", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMShovel), "D", "X", "X", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMSword), "D", "D", "X", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMHoe), "DD ", " X ", " X ", 'D', DM, 'X', Items.diamond);
        addRecipe(gs(DMHoe), " DD", " X ", " X ", 'D', DM, 'X', Items.diamond);
        addSRecipe(gs(Items.potionitem,1,0),Ever,Items.glass_bottle);
    	addRelicRecipe();
    	addAlchemicalRecipe();
    	addCovalenceRecipe();
    	addFixRecipe();
    	addRingRecipe();
    	registerFuel();
    	registerHarvestLevel();
    	registerAchievements();
    }
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	suggestedConfig = e.getSuggestedConfigurationFile();
    }
    public int convertCountToTick(int count)
    {
    	return 200 * count;
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) throws Exception
    {
    	loadGT = Loader.isModLoaded("gregtech");
    	loadFMP = Loader.isModLoaded("ForgeMultipart");
    	loadTinCo = Loader.isModLoaded("TConstruct");
    	if(loadGT&&!loadFMP)
    	{
    		PhilTool = new ItemPhilToolGT();
    	}
    	else if(loadGT && loadFMP)
    	{
    		PhilTool = new ItemPhilToolGTFMP();
    	}
    	else if(!loadGT && loadFMP)
    	{
    		PhilTool = new ItemPhilToolFMP();
    	}
    	MinecraftForge.EVENT_BUS.register(new EEHandler());
    	FMLCommonHandler.instance().bus().register(new CraftingHandler());
    	if(Loader.isModLoaded("IC2"))
    	{
    		IC2Addon();
    	}
    	if(Loader.isModLoaded("Tubestuff"))
    	{
    		addTSRecipe();
    	}
    	if(Loader.isModLoaded("ProjRed|Core"))
    	{
    		PRAddon();
    	}
    	if(Loader.isModLoaded("gregtech"))
    	{
    		GTAddon();
    	}
    }
    /*
     * Repair Check
     */
    public static boolean isRepairable(Item item)
    {
    	if(!(item instanceof ItemDMShears))
		{
			/*
			 * For Vannila Items
			 */
			if(item instanceof ItemTool||item instanceof ItemSword||item instanceof ItemShears||item instanceof ItemBow||item instanceof ItemHoe||item instanceof ItemFlintAndSteel)
			{
				return true;
			}
		}
		if(loadGT&&loadTinCo)
		{
			if(item instanceof ToolCore||item instanceof GT_MetaGenerated_Tool)
			{
				return true;
			}
		}
		if(loadTinCo)
		{
			if(item instanceof ToolCore)
			{
				return true;
			}
		}
		return false;
    }
    /*
     * Config
     */
    public void registerFuel()
    {
    	FuelHandler.register(AlchCoal,convertCountToTick(40));
    	FuelHandler.register(mobiusFuel,convertCountToTick(120));
    	GameRegistry.registerFuelHandler(new FuelHandler());
    }
    public void registerHarvestLevel()
    {
    	DMPickaxe.setHarvestLevel("pickaxe",10);
    	DMShovel.setHarvestLevel("shovel",4);
    	DMBlock.setHarvestLevel("pickaxe",5);
    }
    public void loadConfig()
    {
    	Configuration config = new Configuration(suggestedConfig);
    	config.load();
    	Hard = config.getBoolean("Hard Mode",config.CATEGORY_GENERAL,false,"removes some vanilla recipes and adds harder recipes!");
    	cutDown = config.getBoolean("Cut Down",config.CATEGORY_GENERAL,true,"cut down from root");
    	Debug = config.getBoolean("Debug",config.CATEGORY_GENERAL,false,"Debug mode");
    	noBats= config.getBoolean("noBats",config.CATEGORY_GENERAL,false,"No more bats!");
    	noTeleport= config.getBoolean("noTeleport",config.CATEGORY_GENERAL,false,"now Enderman can't teleport!");
    	dontCarry= config.getBoolean("noCarry",config.CATEGORY_GENERAL,false,"now Enderman can't carry blocks!");
    	config.save();
    }
    public void registerAchievements()
    {
    	getPhil = new Achievement("getPhil","getPhil",0,0,Phil,AchievementList.portal);
    	getDM = new Achievement("getDM","getDM",2,1,DM,getPhil);
    	AchievementPage page = new AchievementPage("EELimited",getPhil,getDM);
    	AchievementPage.registerAchievementPage(page);
    }
    /*
     * Addons
     */
    public Item findItemFromName(String name)
    {
    	return findItemFromName(name,true);
    }
    public Item findItemFromName(String name,boolean part)
    {
    	for(Object obj : GameData.getItemRegistry())
    	{
    		Item i = (Item)obj;
    		if(i != null)
    		{
    			String itemName = i.getUnlocalizedName();
    			boolean isRightItem;
    			if(part)
    			{
    				isRightItem = itemName.toLowerCase().contains(name.toLowerCase());
    			}
    			else
    			{
    				isRightItem = itemName == name;
    			}
    			if(isRightItem)
    			{
    				logFinder.info("Item Found:"+gs(i).getDisplayName());
    				return i;
    			}
    		}
    	}
    	return null;
    }
    public ItemStack getIC2(String name)
    {
    	return IC2Items.getItem(name);
    }
    public void addTSRecipe()
    {
        ItemStack BHC;
		try {
			BHC = gs(getTSBlock("block"), 1, 2);
		} catch (Exception e)
		{
			return;
		}
        addRecipe(BHC, "DDD", "DCD", "DDD", 'D', DM, 'C', Blocks.chest);
    }
    public void CallGTMethod(String method)
    {
    }
    public void IC2Addon()
	{
		addFixRecipe(LOW, IC2Items.getItem("treetap").getItem(), 5);
	    addFixRecipe(LOW, IC2Items.getItem("hazmatHelmet").getItem(), 4);
	    addFixRecipe(LOW, IC2Items.getItem("hazmatChestplate").getItem(), 6);
	    addFixRecipe(LOW, IC2Items.getItem("hazmatLeggings").getItem(), 6);
	    addFixRecipe(LOW, IC2Items.getItem("hazmatBoots").getItem(), 6);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeHelmet").getItem(), 5);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeChestplate").getItem(), 8);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeLeggings").getItem(), 7);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeBoots").getItem(), 4);
	    addFixRecipe(MIDDLE, IC2Items.getItem("wrench").getItem(), 6);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzePickaxe"), 3);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeAxe"), 3);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeSword"), 2);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeShovel"), 1);
	    addFixRecipe(MIDDLE, IC2Items.getItem("bronzeHoe"), 2);
	    addRecipe(new ShapelessOreRecipe(getIC2("platecopper"),gs(PhilTool),"ingotCopper"));
	    addRecipe(new ShapelessOreRecipe(getIC2("platetin"),gs(PhilTool),"ingotTin"));
	    addRecipe(new ShapelessOreRecipe(getIC2("plategold"),gs(PhilTool),gs(Items.gold_ingot)));
	    addRecipe(new ShapelessOreRecipe(getIC2("plateiron"),gs(PhilTool),gs(Items.iron_ingot)));
	    addRecipe(new ShapelessOreRecipe(getIC2("platebronze"),gs(PhilTool),"ingotBronze"));
	    addRecipe(new ShapelessOreRecipe(getIC2("platelead"),gs(PhilTool),"ingotLead"));
	    addSRecipe(changeAmount(getIC2("ironCableItem"),4),gs(PhilTool),getIC2("plateiron"));
	    addSRecipe(changeAmount(getIC2("copperCableItem"),4),gs(PhilTool),getIC2("platecopper"));
	    addSRecipe(changeAmount(getIC2("goldCableItem"),4),gs(PhilTool),getIC2("plategold"));
	    addSRecipe(changeAmount(getIC2("tinCableItem"),4),gs(PhilTool),getIC2("platetin"));
	    ItemStack dp = IC2Items.getItem("diamondDust");
	    Recipes.macerator.addRecipe(new RecipeInputItemStack(gs(Blocks.diamond_ore)), null, changeAmount(dp, 2));
	    GameRegistry.addSmelting(dp, gs(Items.diamond), 10);
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
    public void addSawRecipe(ItemStack is,Object obj,int count)
    {
    	if(obj instanceof ItemStack)
    	{
    		List<ItemStack> list = Arrays.asList(new ItemStack[]{gs(PhilTool,1,1)});
    		for(int i = 0;i < count;i++)
    		{
    			list.add((ItemStack)obj);
    		}
    		addSRecipe(is,list.toArray());
    	}
    	else
    	{
    		addSawRecipe(is,gs(obj),count);
    	}
    }
    public void PRAddon()
    {
    	try
    	{
    		Item screwdriver = findItemFromName("projectred.core.screwdriver");
    		Item Part = findItemFromName("projectred.core.part");
    		Item RubyAxe = findItemFromName("exploration.axeruby");
    		Item SapphireAxe = findItemFromName("exploration.axesapphire");
    		Item PeridotAxe = findItemFromName("exploration.axeperidot");
    		Item RubyHoe = findItemFromName("exploration.hoeruby");
    		Item SapphireHoe = findItemFromName("exploration.hoesapphire");
    		Item PeridotHoe = findItemFromName("exploration.hoeperidot");
    		Item RubyShovel = findItemFromName("exploration.shovelruby");
    		Item SapphireShovel = findItemFromName("exploration.shovelsapphire");
    		Item PeridotShovel = findItemFromName("exploration.shovelperidot");
    		Item RubyPickaxe = findItemFromName("exploration.pickaxeruby");
    		Item SapphirePickaxe = findItemFromName("exploration.pickaxesapphire");
    		Item PeridotPickaxe = findItemFromName("exploration.pickaxeperidot");
    		Item RubySword = findItemFromName("exploration.swordruby");
    		Item SapphireSword = findItemFromName("exploration.swordsapphire");
    		Item PeridotSword = findItemFromName("exploration.axeperidot");
    		Item RubySaw = findItemFromName("exploration.sawruby");
    		Item SapphireSaw = findItemFromName("exploration.sawsapphire");
    		Item PeridotSaw = findItemFromName("exploration.sawperidot");
    		Item GoldSaw = findItemFromName("exploration.sawgold");
    		ItemStack RedAlloy = gs(Part,1,10);
    		ItemStack BouleSilicon = gs(Part,1,11);
    		ItemStack Silicon = gs(Part,1,12);
    		ItemStack RedWafer = gs(Part,1,13);
    		ItemStack GlowWafer = gs(Part,1,14);
    		addFixRecipe(MIDDLE,screwdriver,3);
    		addFixRecipe(MIDDLE,RubyAxe,3);
    		addFixRecipe(MIDDLE,SapphireAxe,3);
    		addFixRecipe(MIDDLE,PeridotAxe,3);
    		addFixRecipe(MIDDLE,RubyHoe,2);
    		addFixRecipe(MIDDLE,SapphireHoe,2);
    		addFixRecipe(MIDDLE,PeridotHoe,2);
    		addFixRecipe(MIDDLE,RubyShovel,1);
    		addFixRecipe(MIDDLE,SapphireShovel,1);
    		addFixRecipe(MIDDLE,PeridotShovel,1);
    		addFixRecipe(MIDDLE,RubySword,2);
    		addFixRecipe(MIDDLE,SapphireSword,2);
    		addFixRecipe(MIDDLE,PeridotSword,2);
    		addFixRecipe(MIDDLE,RubySaw,1);
    		addFixRecipe(MIDDLE,SapphireSaw,1);
    		addFixRecipe(MIDDLE,PeridotSaw,1);
    		addFixRecipe(MIDDLE,GoldSaw,1);
    		addSRecipe(gs(RedAlloy),gs(Phil),gs(Items.iron_ingot),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone));
    		addOSRecipe(gs(RedAlloy),gs(Phil),"ingotCopper",gs(Items.redstone),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone));
    		addExchange(gs(BouleSilicon),Blocks.sand,gs(Items.coal,1,-1));
    		for(int i = 1;i <= 8;i++)
    		{
    			addSawRecipe(changeAmount(Silicon,8 * i),BouleSilicon,i);
    		}
    		addExchange(gs(RedWafer),gs(Silicon),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone));
    		addExchange(gs(GlowWafer),gs(Silicon),gs(Items.glowstone_dust),gs(Items.glowstone_dust),gs(Items.glowstone_dust),gs(Items.glowstone_dust));
    	}
    	catch(Exception e){}
    }
    public void registerTool(ItemStack is,Collection<GT_ItemStack>aToolList)
    {
    	aToolList.add(new GT_ItemStack(GT_Utility.copyAmount(1,is)));
    	GregTech_API.sToolList.add(new GT_ItemStack(GT_Utility.copyAmount(1,is)));
    }
    public void GTAddon()
    {
    	 registerTool(gs(PhilTool,1,1),GregTech_API.sCrowbarList);
    	 registerTool(gs(PhilTool,1,2),GregTech_API.sHardHammerList);
    	 registerTool(gs(PhilTool,1,3),GregTech_API.sScrewdriverList);
    	 registerTool(gs(PhilTool,1,4),GregTech_API.sSoftHammerList);
    	 registerTool(gs(PhilTool,1,5),GregTech_API.sWrenchList);
    }
    /*
     * Recipe Remove
     */
    public void removeRecipes()
    {
    	removeSmeltingRecipe(Blocks.iron_ore);
    	NIron = new ItemEE(NameRegistry.NuggetIron);
    	GameRegistry.addSmelting(Blocks.iron_ore,gs(NIron),10);
    	addRecipe(gs(Items.iron_ingot),"XXX","XXX","XXX",'X',NIron);
    	removeSmeltingRecipe(Blocks.gold_ore);
    	GameRegistry.addSmelting(Blocks.gold_ore,gs(Items.gold_nugget),10);
    }
    public static boolean areStacksEqual(ItemStack is1,ItemStack is2)
    {
    	if(is1==null||is2==null)
    	{
    		return false;
    	}
    	return is1.getItem() == is2.getItem();
    }
    public static boolean removeSmeltingRecipe(Object aInput) {
		if (aInput != null) {
			if(aInput instanceof ItemStack)
			{
				for (Object tInput : FurnaceRecipes.smelting().getSmeltingList().keySet()) {
					if (areStacksEqual((ItemStack)aInput, (ItemStack)tInput)) {
						FurnaceRecipes.smelting().getSmeltingList().remove(tInput);
						return true;
					}
				}
			}
			else
			{
				ItemStack is = gs(aInput);
	    		if(is==null)
	    		{
	    			return false;
	    		}
	    		return removeSmeltingRecipe(is);
			}
		}
		return false;
	}
    public static boolean removeRecipe(Object aOutput)
    {
    	return removeRecipe(aOutput, true);
    }
    public static boolean removeRecipe(Object aOutput,boolean ignoreAmount) {
    	if (aOutput == null) return false;
    	boolean rReturn = false;
    	if(aOutput instanceof ItemStack)
    	{
    		ArrayList<IRecipe> tList = (ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList();
    		for (int i = 0; i < tList.size(); i++) {
    			ItemStack tStack = tList.get(i).getRecipeOutput();
	    		if (areStacksEqual(tStack, (ItemStack)aOutput)) {
	    			if(!ignoreAmount && (tStack.stackSize != ((ItemStack)aOutput).stackSize))
	    			{
	    				continue;
	    			}
					tList.remove(i);
					rReturn = true;
	    		}
			}
    	}
    	else
    	{
    		ItemStack is = gs(aOutput);
    		if(is==null)
    		{
    			return false;
    		}
    		return removeRecipe(is, ignoreAmount);
    	}
		return rReturn;
    }
    public void addSmeltingExchange()
    {
    	Map<Object,ItemStack> map = FurnaceRecipes.smelting().getSmeltingList();
    	for(Map.Entry<Object,ItemStack> entry : map.entrySet())
    	{
    		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    		list.add(gs(PhilTool));
    		list.add(gs(entry.getKey()));
    		addSRecipe(entry.getValue(),list.toArray());
    	}
    	for(Object obj : GameData.getItemRegistry())
    	{
    			Item item = (Item)obj;
    			if(!(item instanceof ItemDMShears)&&isRepairable(item))
    			{
    				addFixRecipe(EXTREME,item,1);
    			}
    	}
    	if(dontCarry)
    	{
    		for(Object obj : GameData.getBlockRegistry())
    		{
    			Block block = (Block)obj;
    			EntityEnderman.setCarriable(block, false);
    		}
    	}
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
    public static void addORecipe(ItemStack dest,Object...objs)
    {
    	addRecipe(new ShapedOreRecipe(dest,objs));
    }
    public static void addOSRecipe(ItemStack dest,Object...objs)
    {
    	addRecipe(new ShapelessOreRecipe(dest,objs));
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
    public static ItemStack getCov(Level lv)
    {
        if (lv == LOW)
        {
            return gs(Cov, 1, 0);
        }
        else if (lv == MIDDLE)
        {
            return gs(Cov, 1, 1);
        }
        else if (lv == EXTREME)
        {
        	return gs(Repair);
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
        if(list.size() > 9)
        {
        	return;
        }
        addSRecipe(dest, list.toArray());
    }
    public void addExchange(ItemStack dest, Object obj, int amount,int phil)
    {
        List<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < amount; i++)
        {
            list.add(gs(obj));
        }
        for(int i = 0;i < phil;i++)
        {
        	list.add(gs(Phil));
        }
        if(list.size() > 9)
        {
        	return;
        }
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
        if(list.size() > 9)
        {
        	return;
        }
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
    public Class getClassObj(String pa,String className)
    {
    	try {
			return Class.forName(pa + "." + className);
		} catch (ClassNotFoundException e) {
			return null;
		}
    }
    public Block getBPBlock(String name)
    {
    	Class c = getClassObj("com.bluepowermod.init","BPBlocks");
    	if(c != null)
    	{
    		try {
				return (Block)c.getField(name).get(null);
			} catch (Exception e){}
    	}
    	return null;
    }
    public Item getBPItem(String name)
    {
    	Class c = getClassObj("com.bluepowermod.init","BPItems");
    	if(c != null)
    	{
    		try {
				return (Item)c.getField(name).get(null);
			} catch (Exception e){}
    	}
    	return null;
    }
    /*
     * recipes
     */
    public void addRingRecipe()
    {
        addRingRecipe(Blocks.log, 4);
        addRingRecipe(Blocks.sapling, 4);
        addRingRecipe(Blocks.stonebrick, 4);
        addRingRecipe(Blocks.wool, 0x10);
        addRingRecipe(Items.dye, 0x10);
        addRingRecipe(gti(gs(Items.porkchop), gs(Items.beef), gs(Items.chicken), gs(Items.fish)));
        addRingRecipe(gti(gs(Items.cooked_porkchop), gs(Items.cooked_beef), gs(Items.cooked_chicken), gs(Items.cooked_fished)));
    }
    public void addFixRecipe()
    {
        addFixRecipe(LOW, Items.wooden_shovel, 1);
        addFixRecipe(LOW, Items.wooden_hoe, 2);
        addFixRecipe(LOW, Items.wooden_sword, 2);
        addFixRecipe(LOW, Items.wooden_axe, 3);
        addFixRecipe(LOW, Items.wooden_pickaxe, 3);
        addFixRecipe(LOW, Items.stone_shovel, 1);
        addFixRecipe(LOW, Items.stone_hoe, 2);
        addFixRecipe(LOW, Items.stone_sword, 2);
        addFixRecipe(LOW, Items.stone_axe, 3);
        addFixRecipe(LOW, Items.stone_pickaxe, 3);
        addFixRecipe(LOW, Items.bow, 2);
        addFixRecipe(LOW, Items.fishing_rod, 2);
        addFixRecipe(MIDDLE, Items.iron_shovel, 1);
        addFixRecipe(MIDDLE, Items.iron_hoe, 2);
        addFixRecipe(MIDDLE, Items.iron_sword, 2);
        addFixRecipe(MIDDLE, Items.iron_axe, 3);
        addFixRecipe(MIDDLE, Items.iron_pickaxe, 3);
        addFixRecipe(MIDDLE, Items.golden_shovel, 1);
        addFixRecipe(MIDDLE, Items.golden_hoe, 2);
        addFixRecipe(MIDDLE, Items.golden_sword, 2);
        addFixRecipe(MIDDLE, Items.golden_axe, 3);
        addFixRecipe(MIDDLE, Items.golden_pickaxe, 3);
        addFixRecipe(MIDDLE, Items.flint_and_steel, 1);
        addFixRecipe(MIDDLE, Items.shears, 2);
        addFixRecipe(HIGH, Items.diamond_shovel, 1);
        addFixRecipe(HIGH, Items.diamond_hoe, 2);
        addFixRecipe(HIGH, Items.diamond_sword, 2);
        addFixRecipe(HIGH, Items.diamond_axe, 3);
        addFixRecipe(HIGH, Items.diamond_pickaxe, 3);
        addFixRecipe(LOW, Items.leather_helmet, 5);
        addFixRecipe(MIDDLE, Items.iron_helmet, 5);
        addFixRecipe(MIDDLE, Items.golden_helmet, 5);
        addFixRecipe(HIGH, Items.diamond_helmet, 5);
        addFixRecipe(LOW, Items.leather_chestplate, 8);
        addFixRecipe(MIDDLE, Items.iron_chestplate, 8);
        addFixRecipe(MIDDLE, Items.golden_chestplate, 8);
        addFixRecipe(HIGH, Items.diamond_chestplate, 8);
        addFixRecipe(LOW, Items.leather_leggings, 7);
        addFixRecipe(MIDDLE, Items.iron_leggings, 7);
        addFixRecipe(MIDDLE, Items.golden_leggings, 7);
        addFixRecipe(HIGH, Items.diamond_leggings, 7);
        addFixRecipe(LOW, Items.leather_boots, 4);
        addFixRecipe(MIDDLE, Items.iron_boots, 4);
        addFixRecipe(MIDDLE, Items.golden_boots, 4);
        addFixRecipe(HIGH, Items.diamond_boots, 4);
    }
    public void addRelicRecipe()
    {
        addRecipe(gs(EETorch, 2), "RDR", "DPD", "GGG", 'R', Blocks.redstone_torch, 'D', Items.diamond, 'P', Phil, 'G', Items.glowstone_dust);
        addRecipe(gs(Ever), "WWW", "DDD", "WWW", 'W', Items.water_bucket, 'D', DM);
        addRecipe(gs(Volc), "WWW", "DDD", "WWW", 'W', Items.lava_bucket, 'D', DM);
        addRecipe(gs(Ever), "IDI", "WEW", "WIW", 'W', Items.water_bucket, 'I', Items.iron_ingot, 'D', Items.diamond, 'E', Ever);
        addRecipe(gs(Volc), "IDI", "WEW", "WIW", 'W', Items.lava_bucket, 'I', Items.iron_ingot, 'D', Items.diamond, 'E', Volc);
        addRecipe(gs(Ever), "IDI", "WEW", "WIW", 'W', Ever, 'I', Items.iron_ingot, 'D', Items.diamond, 'E', Ever);
        addRecipe(gs(Volc), "IDI", "WEW", "WIW", 'W', Volc, 'I', Items.iron_ingot, 'D', Items.diamond, 'E', Volc);
        addRecipe(gs(ironband), "III", "ILI", "III", 'I', Items.iron_ingot, 'L', Items.lava_bucket);
        addRecipe(gs(ironband), "III", "ILI", "III", 'I', Items.iron_ingot, 'L', Volc);
        addRecipe(gs(Swift), "DFD", "FBF", "DFD", 'D', DM, 'F', Items.feather, 'B', ironband);
        addRecipe(gs(DD), "DDD", "DBD", "DDD", 'D', DM, 'B', ironband);
        addRecipe(gs(Repair),"HML","SPS","LMH",'H',getCov(HIGH),'M',getCov(MIDDLE),'L',getCov(LOW),'S',Items.string,'P',Items.paper);
        addRecipe(gs(Repair),"HML","SPS","LMH",'L',getCov(HIGH),'M',getCov(MIDDLE),'H',getCov(LOW),'S',Items.string,'P',Items.paper);
    }
    public void addCovalenceRecipe()
    {
    	addSRecipe(changeAmount(getCov(LOW), 3), gs(Blocks.cobblestone), gs(Blocks.cobblestone), gs(Items.coal));
        addSRecipe(changeAmount(getCov(MIDDLE), 3), gs(Items.iron_ingot), gs(Items.iron_ingot), gs(Items.coal));
        addSRecipe(changeAmount(getCov(HIGH), 3), gs(Items.diamond), gs(Items.diamond), gs(Items.coal));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Blocks.cobblestone), gs(Blocks.cobblestone), gs(Blocks.cobblestone), gs(Items.redstone));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Items.iron_ingot), gs(Items.iron_ingot), gs(Items.iron_ingot), gs(Items.redstone));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Items.diamond), gs(Items.diamond), gs(Items.diamond), gs(Items.redstone));
        addSRecipe(changeAmount(getCov(LOW), 2), gs(Blocks.cobblestone), gs(Items.glowstone_dust), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 2), gs(Items.iron_ingot), gs(Items.glowstone_dust), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 2), gs(Items.diamond), gs(Items.glowstone_dust), gs(Phil));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Blocks.cobblestone), gs(Blocks.cobblestone), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Items.iron_ingot), gs(Items.iron_ingot), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Items.diamond), gs(Items.diamond), gs(AlchCoal), gs(Phil));
        addSRecipe(changeAmount(getCov(LOW), 5), gs(Blocks.cobblestone), gs(mobiusFuel), gs(Phil));
        addSRecipe(changeAmount(getCov(MIDDLE), 5), gs(Items.iron_ingot), gs(mobiusFuel), gs(Phil));
        addSRecipe(changeAmount(getCov(HIGH), 5), gs(Items.diamond), gs(mobiusFuel), gs(Phil));
    }
    public void addAlchemicalRecipe()
    {
    	addRecipe(gs(DMBlock), "OMO", "DDD", "OMO", 'D', Blocks.diamond_block, 'M', mobiusFuel, 'O', Blocks.obsidian);
        addExchange(gs(Blocks.diamond_block, 4), DM);
        addExchange(gs(Items.coal), gs(Items.coal, 1, 1));
        // Dirt
        addExchange(gs(Blocks.sand), Blocks.dirt);
        addExchange(gs(Blocks.clay), Blocks.dirt, 2);
        addExchange(gs(Blocks.sand, 3), Blocks.dirt, 3);
        addExchange(gs(Blocks.cobblestone), Blocks.dirt, 4);
        addExchange(gs(Blocks.sand, 5), Blocks.dirt, 5);
        addExchange(gs(Blocks.clay, 3), Blocks.dirt, 6);
        addExchange(gs(Blocks.sand, 7), Blocks.dirt, 7);
        addExchange(gs(Blocks.cobblestone, 2), Blocks.dirt, 8);
        //Sand
        addExchange(gs(Blocks.dirt), Blocks.sand, 1);
        addExchange(gs(Blocks.clay), Blocks.sand, 2);
        addExchange(gs(Blocks.dirt, 3), Blocks.sand, 3);
        addExchange(gs(Blocks.clay, 2), Blocks.sand, 4);
        addExchange(gs(Blocks.dirt, 5), Blocks.sand, 5);
        addExchange(gs(Blocks.clay, 3), Blocks.sand, 6);
        addExchange(gs(Blocks.dirt, 7), Blocks.sand, 7);
        addExchange(gs(Blocks.clay, 4), Blocks.sand, 8);
        //sand + glowStoneDust
        addExchange(gs(Blocks.soul_sand), Blocks.sand, Items.glowstone_dust);
        addExchange(gs(Blocks.soul_sand, 2), Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust);
        addExchange(gs(Blocks.soul_sand, 3), Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust);
        addExchange(gs(Blocks.soul_sand, 4), Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust, Blocks.sand, Items.glowstone_dust);
        //cobbleStone + glowStone
        addExchange(gs(Blocks.soul_sand, 4), Blocks.cobblestone, Blocks.glowstone);
        addExchange(gs(Blocks.soul_sand, 8), Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone);
        addExchange(gs(Blocks.soul_sand, 12), Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone);
        addExchange(gs(Blocks.soul_sand, 16), Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone, Blocks.cobblestone, Blocks.glowstone);
        //Sandstone
        addExchange(gs(Blocks.sand, 4), Blocks.sandstone, 1);
        addExchange(gs(Blocks.sand, 8), Blocks.sandstone, 2);
        addExchange(gs(Blocks.sand, 12), Blocks.sandstone, 3);
        addExchange(gs(Blocks.cobblestone, 4), Blocks.sandstone, 4);
        addExchange(gs(Blocks.sand, 20), Blocks.sandstone, 5);
        addExchange(gs(Blocks.sand, 24), Blocks.sandstone, 6);
        addExchange(gs(Blocks.sand, 28), Blocks.sandstone, 7);
        addExchange(gs(Blocks.redstone_ore, 1), Blocks.sandstone, 8);
        //dirt + sand
        addExchange(gs(Blocks.gravel), Blocks.dirt, Blocks.sand);
        addExchange(gs(Blocks.gravel, 2), Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand);
        addExchange(gs(Blocks.gravel, 3), Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand);
        addExchange(gs(Blocks.gravel, 4), Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand, Blocks.dirt, Blocks.sand);
        //gravel
        addExchange(gs(Blocks.sand, 2), Blocks.gravel, 1);
        addExchange(gs(Blocks.dirt, 4), Blocks.gravel, 2);
        addExchange(gs(Items.flint, 2), Blocks.gravel, 3);
        addExchange(gs(Blocks.dirt, 8), Blocks.gravel, 4);
        addExchange(gs(Blocks.sand, 10), Blocks.gravel, 5);
        addExchange(gs(Items.flint, 4), Blocks.gravel, 6);
        addExchange(gs(Blocks.sand, 14), Blocks.gravel, 7);
        addExchange(gs(Blocks.dirt, 16), Blocks.gravel, 8);
        //flint
        addExchange(gs(Items.clay_ball, 3), Items.flint, 1);
        addExchange(gs(Blocks.gravel, 3), Items.flint, 2);
        addExchange(gs(Items.clay_ball, 9), Items.flint, 3);
        addExchange(gs(Blocks.gravel, 6), Items.flint, 4);
        addExchange(gs(Items.clay_ball, 15), Items.flint, 5);
        addExchange(gs(Blocks.gravel, 9), Items.flint, 6);
        addExchange(gs(Items.clay_ball, 21), Items.flint, 7);
        addExchange(gs(Blocks.gravel, 12), Items.flint, 8);
        //clayBlock
        addExchange(gs(Items.clay_ball, 4), Blocks.clay, 1);
        addExchange(gs(Blocks.cobblestone), Blocks.clay, 2);
        addExchange(gs(Items.clay_ball, 12), Blocks.clay, 3);
        addExchange(gs(Blocks.cobblestone, 2), Blocks.clay, 4);
        addExchange(gs(Items.clay_ball, 20), Blocks.clay, 5);
        addExchange(gs(Blocks.cobblestone, 3), Blocks.clay, 6);
        addExchange(gs(Items.clay_ball, 28), Blocks.clay, 7);
        addExchange(gs(Blocks.cobblestone, 4), Blocks.clay, 8);
        //clay
        addExchange(gs(Blocks.dirt), Items.clay_ball, 2);
        addExchange(gs(Blocks.dirt, 2), Items.clay_ball, 4);
        addExchange(gs(Blocks.dirt, 3), Items.clay_ball, 6);
        addExchange(gs(Blocks.dirt, 4), Items.clay_ball, 8);
        //cobblestone
        addExchange(gs(Blocks.dirt, 4), Blocks.cobblestone, 1);
        addExchange(gs(Blocks.clay, 4), Blocks.cobblestone, 2);
        addExchange(gs(Blocks.dirt, 12), Blocks.cobblestone, 3);
        addExchange(gs(Blocks.clay, 8), Blocks.cobblestone, 4);
        addExchange(gs(Blocks.dirt, 20), Blocks.cobblestone, 5);
        addExchange(gs(Blocks.clay, 12), Blocks.cobblestone, 6);
        addExchange(gs(Blocks.dirt, 28), Blocks.cobblestone, 7);
        addExchange(gs(Blocks.redstone_ore, 1), Blocks.cobblestone, 8);
        //cobblestone + redstone
        addExchange(gs(Blocks.netherrack), Blocks.cobblestone, Items.redstone);
        addExchange(gs(Blocks.netherrack, 2), Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone);
        addExchange(gs(Blocks.netherrack, 3), Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone);
        addExchange(gs(Blocks.netherrack, 4), Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone, Blocks.cobblestone, Items.redstone);
        //stone
        addExchange(gs(Blocks.cobblestone), Blocks.stone, 1);
        addExchange(gs(Blocks.cobblestone, 2), Blocks.stone, 2);
        addExchange(gs(Blocks.cobblestone, 3), Blocks.stone, 3);
        addExchange(gs(Blocks.cobblestone, 4), Blocks.stone, 4);
        addExchange(gs(Blocks.cobblestone, 5), Blocks.stone, 5);
        addExchange(gs(Blocks.cobblestone, 6), Blocks.stone, 6);
        addExchange(gs(Blocks.cobblestone, 7), Blocks.stone, 7);
        addExchange(gs(Blocks.redstone_ore), Blocks.stone, 8);
        //redstoneOre
        addExchange(gs(Blocks.cobblestone, 8), Blocks.redstone_ore, 1);
        addExchange(gs(Items.redstone, 9), Blocks.redstone_ore, 2);
        addExchange(gs(Blocks.cobblestone, 24), Blocks.redstone_ore, 3);
        addExchange(gs(Blocks.iron_ore), Blocks.redstone_ore, 4);
        addExchange(gs(Blocks.cobblestone, 40), Blocks.redstone_ore, 5);
        addExchange(gs(Items.redstone, 27), Blocks.redstone_ore, 6);
        addExchange(gs(Blocks.cobblestone, 56), Blocks.redstone_ore, 7);
        addExchange(gs(Blocks.iron_ore, 2), Blocks.redstone_ore, 8);
        //ironOre
        addExchange(gs(Blocks.cobblestone, 32), Blocks.iron_ore, 1);
        addExchange(gs(Blocks.cobblestone, 64), Blocks.iron_ore, 2);
        addExchange(gs(Blocks.redstone_ore, 12), Blocks.iron_ore, 3);
        addExchange(gs(Blocks.gold_ore, 1), Blocks.iron_ore, 4);
        addExchange(gs(Blocks.redstone_ore, 20), Blocks.iron_ore, 5);
        addExchange(gs(Blocks.redstone_ore, 24), Blocks.iron_ore, 6);
        addExchange(gs(Blocks.redstone_ore, 28), Blocks.iron_ore, 7);
        addExchange(gs(Blocks.gold_ore, 2), Blocks.iron_ore, 8);
        //goldOre
        addExchange(gs(Blocks.iron_ore, 4), Blocks.gold_ore, 1);
        addExchange(gs(Blocks.iron_ore, 8), Blocks.gold_ore, 2);
        addExchange(gs(Blocks.iron_ore, 12), Blocks.gold_ore, 3);
        addExchange(gs(Blocks.diamond_ore, 1), Blocks.gold_ore, 4);
        addExchange(gs(Blocks.iron_ore, 20), Blocks.gold_ore, 5);
        addExchange(gs(Blocks.iron_ore, 24), Blocks.gold_ore, 6);
        addExchange(gs(Blocks.iron_ore, 28), Blocks.gold_ore, 7);
        addExchange(gs(Blocks.diamond_ore, 2), Blocks.gold_ore, 8);
        //diamondOre
        addExchange(gs(Blocks.gold_ore, 4), Blocks.diamond_ore, 1);
        addExchange(gs(Blocks.gold_ore, 8), Blocks.diamond_ore, 2);
        addExchange(gs(Blocks.gold_ore, 12), Blocks.diamond_ore, 3);
        addExchange(gs(Blocks.gold_ore, 16), Blocks.diamond_ore, 4);
        addExchange(gs(Blocks.gold_ore, 20), Blocks.diamond_ore, 5);
        addExchange(gs(Blocks.gold_ore, 24), Blocks.diamond_ore, 6);
        addExchange(gs(Blocks.gold_ore, 28), Blocks.diamond_ore, 7);
        addExchange(gs(Blocks.gold_ore, 32), Blocks.diamond_ore, 8);
        //soulSand
        addExchange(gs(Blocks.sand, 2), Blocks.soul_sand, 1);
        addExchange(gs(Blocks.sand, 4), Blocks.soul_sand, 2);
        addExchange(gs(Blocks.sand, 6), Blocks.soul_sand, 3);
        addExchange(gs(Blocks.sand, 8), Blocks.soul_sand, 4);
        addExchange(gs(Blocks.sand, 10), Blocks.soul_sand, 5);
        addExchange(gs(Blocks.sand, 12), Blocks.soul_sand, 6);
        addExchange(gs(Blocks.sand, 14), Blocks.soul_sand, 7);
        addExchange(gs(Blocks.sand, 16), Blocks.soul_sand, 8);
        //Netherrack
        addExchange(gs(Blocks.stone), Blocks.netherrack, 1);
        addExchange(gs(Blocks.stone, 2), Blocks.netherrack, 2);
        addExchange(gs(Blocks.stone, 3), Blocks.netherrack, 3);
        addExchange(gs(Blocks.stone, 4), Blocks.netherrack, 4);
        addExchange(gs(Blocks.stone, 5), Blocks.netherrack, 5);
        addExchange(gs(Blocks.stone, 6), Blocks.netherrack, 6);
        addExchange(gs(Blocks.stone, 7), Blocks.netherrack, 7);
        addExchange(gs(Blocks.redstone_ore), Blocks.netherrack, 8);
        //glass
        addExchange(gs(Blocks.sand, 3), Blocks.glass, 2);
        addExchange(gs(Blocks.sand, 6), Blocks.glass, 4);
        addExchange(gs(Blocks.sand, 9), Blocks.glass, 6);
        addExchange(gs(Blocks.ice), Blocks.glass, 8);
        //ice
        addExchange(gs(Blocks.glass, 8), Blocks.ice, 1);
        addExchange(gs(Blocks.glass, 16), Blocks.ice, 2);
        addExchange(gs(Blocks.glass, 24), Blocks.ice, 3);
        addExchange(gs(Blocks.glass, 32), Blocks.ice, 4);
        addExchange(gs(Blocks.glass, 40), Blocks.ice, 5);
        addExchange(gs(Blocks.glass, 48), Blocks.ice, 6);
        addExchange(gs(Blocks.glass, 56), Blocks.ice, 7);
        addExchange(gs(Items.dye, 1, 4), Blocks.ice, 8);
        //lapisLazuli
        addExchange(gs(Blocks.ice, 16), gs(Items.dye, 1, 4), 2);
        addExchange(gs(Blocks.obsidian, 3), gs(Items.dye, 1, 4), 3);
        addExchange(gs(Blocks.ice, 32), gs(Items.dye, 1, 4), 4);
        addExchange(gs(Blocks.obsidian, 5), gs(Items.dye, 1, 4), 5);
        addExchange(gs(Blocks.ice, 48), gs(Items.dye, 1, 4), 6);
        addExchange(gs(Blocks.obsidian, 7), gs(Items.dye, 1, 4), 7);
        addExchange(gs(Blocks.lapis_ore), gs(Items.dye, 1, 4), 8);
        //lapisBlock
        addExchange(gs(Blocks.obsidian,  9), Blocks.lapis_block, 1);
        addExchange(gs(Blocks.obsidian, 18), Blocks.lapis_block, 2);
        addExchange(gs(Blocks.obsidian, 27), Blocks.lapis_block, 3);
        addExchange(gs(Blocks.obsidian, 36), Blocks.lapis_block, 4);
        addExchange(gs(Blocks.obsidian, 45), Blocks.lapis_block, 5);
        addExchange(gs(Blocks.obsidian, 54), Blocks.lapis_block, 6);
        addExchange(gs(Blocks.obsidian, 63), Blocks.lapis_block, 7);
        addExchange(gs(Items.diamond,    2), Blocks.lapis_block, 8);
        //obsidian
        addExchange(gs(Items.dye, 1, 4), Blocks.obsidian);
        addExchange(gs(Items.dye, 2, 4), Blocks.obsidian, 2);
        addExchange(gs(Items.dye, 3, 4), Blocks.obsidian, 3);
        addExchange(gs(Items.dye, 4, 4), Blocks.obsidian, 4);
        addExchange(gs(Items.dye, 5, 4), Blocks.obsidian, 5);
        addExchange(gs(Items.dye, 6, 4), Blocks.obsidian, 6);
        addExchange(gs(Items.dye, 7, 4), Blocks.obsidian, 7);
        addExchange(gs(Blocks.lapis_ore), Blocks.obsidian, 8);
        //lapisOre
        addExchange(gs(Items.dye, 8, 4), Blocks.lapis_ore, 1);
        addExchange(gs(Blocks.obsidian, 16), Blocks.lapis_ore, 2);
        addExchange(gs(Items.dye, 24, 4), Blocks.lapis_ore, 3);
        addExchange(gs(Items.diamond), Blocks.lapis_ore, 4);
        addExchange(gs(Items.dye, 40, 4), Blocks.lapis_ore, 5);
        addExchange(gs(Blocks.obsidian, 48), Blocks.lapis_ore, 6);
        addExchange(gs(Items.dye, 56, 4), Blocks.lapis_ore, 7);
        addExchange(gs(Items.diamond, 2), Blocks.lapis_ore, 8);
        //iron
        addExchange(gs(Items.glowstone_dust, 6), Items.iron_ingot, 1);
        addExchange(gs(Blocks.glowstone, 3), Items.iron_ingot, 2);
        addExchange(gs(Items.glowstone_dust, 18), Items.iron_ingot, 3);
        addExchange(gs(Items.gold_ingot), Items.iron_ingot, 4);
        addExchange(gs(Items.glowstone_dust, 30), Items.iron_ingot, 5);
        addExchange(gs(Blocks.glowstone, 9), Items.iron_ingot, 6);
        addExchange(gs(Items.glowstone_dust, 42), Items.iron_ingot, 7);
        addExchange(gs(Items.gold_ingot, 2), Items.iron_ingot, 8);
        //gold
        addExchange(gs(Items.iron_ingot, 4), Items.gold_ingot, 1);
        addExchange(gs(Items.iron_ingot, 8), Items.gold_ingot, 2);
        addExchange(gs(Items.iron_ingot, 12), Items.gold_ingot, 3);
        addExchange(gs(Items.diamond), Items.gold_ingot, 4);
        addExchange(gs(Items.iron_ingot, 20), Items.gold_ingot, 5);
        addExchange(gs(Items.iron_ingot, 24), Items.gold_ingot, 6);
        addExchange(gs(Items.iron_ingot, 28), Items.gold_ingot, 7);
        addExchange(gs(Items.diamond, 2), Items.gold_ingot, 8);

        //diamond
        for (int i = 1; i <= 8; i++)
        {
            addExchange(gs(Items.gold_ingot, 4 * i), Items.diamond, i);
        }

        //cobblestone + seed
        addExchange(gs(Blocks.mossy_cobblestone), Blocks.cobblestone, Items.wheat_seeds);
        addExchange(gs(Blocks.mossy_cobblestone, 2), Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds);
        addExchange(gs(Blocks.mossy_cobblestone, 3), Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds);
        addExchange(gs(Blocks.mossy_cobblestone, 4), Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds, Blocks.cobblestone, Items.wheat_seeds);
        //dirt + seed
        addExchange(gs(Blocks.grass), Blocks.dirt, Items.wheat_seeds);
        addExchange(gs(Blocks.grass, 2), Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds);
        addExchange(gs(Blocks.grass, 3), Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds);
        addExchange(gs(Blocks.grass, 4), Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds, Blocks.dirt, Items.wheat_seeds);
        //Gold + apple
        addExchange(gs(Items.golden_apple), Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.apple);
        //coal
        addExchange(gs(Blocks.cobblestone), Items.coal, 1);
        addExchange(gs(Items.redstone, 3), Items.coal, 2);
        addExchange(gs(Blocks.cobblestone, 2), Items.coal, 3);
        addExchange(gs(Items.glowstone_dust), Items.coal, 4);
        addExchange(gs(Blocks.cobblestone, 5), Items.coal, 5);
        addExchange(gs(Items.redstone, 9), Items.coal, 6);
        addExchange(gs(Blocks.cobblestone, 7), Items.coal, 7);
        addExchange(gs(Items.glowstone_dust, 2), Items.coal, 8);
        //sugar
        addExchange(gs(Items.redstone), Items.sugar, 1);
        addExchange(gs(Items.redstone, 2), Items.sugar, 2);
        addExchange(gs(Items.coal, 2), Items.sugar, 3);
        addExchange(gs(Items.gunpowder), Items.sugar, 4);
        addExchange(gs(Items.redstone, 5), Items.sugar, 5);
        addExchange(gs(Items.glowstone_dust), Items.sugar, 6);
        addExchange(gs(Items.redstone, 7), Items.sugar, 7);
        addExchange(gs(Items.gunpowder, 2), Items.sugar, 8);
        //redstone
        addExchange(gs(Items.coal, 2), Items.redstone, 3);
        addExchange(gs(Items.gunpowder), Items.redstone, 4);
        addExchange(gs(Items.glowstone_dust), Items.redstone, 6);
        addExchange(gs(Items.gunpowder, 2), Items.redstone, 8);
        //redstone + coal
        addExchange(gs(Items.gunpowder), Items.redstone, Items.coal, Items.coal);
        addExchange(gs(Items.gunpowder, 2), Items.redstone, Items.coal, Items.coal, Items.redstone, Items.coal, Items.coal);
        //gunpowder
        addExchange(gs(Items.redstone, 4), Items.gunpowder, 1);
        addExchange(gs(Items.redstone, 8), Items.gunpowder, 2);
        addExchange(gs(Items.glowstone_dust, 2), Items.gunpowder, 3);
        addExchange(gs(Items.redstone, 16), Items.gunpowder, 4);
        addExchange(gs(Items.redstone, 20), Items.gunpowder, 5);
        addExchange(gs(Blocks.glowstone, 4), Items.gunpowder, 6);
        addExchange(gs(Items.redstone, 28), Items.gunpowder, 7);
        addExchange(gs(Items.redstone, 32), Items.gunpowder, 8);
        //glowstone powder
        addExchange(gs(Items.redstone, 6), Items.glowstone_dust, 1);
        addExchange(gs(Items.gunpowder, 3), Items.glowstone_dust, 2);
        addExchange(gs(Items.redstone, 18), Items.glowstone_dust, 3);
        addExchange(gs(Items.gunpowder, 6), Items.glowstone_dust, 4);
        addExchange(gs(Items.redstone, 30), Items.glowstone_dust, 5);
        addExchange(gs(Items.gunpowder, 9), Items.glowstone_dust, 6);
        addExchange(gs(Items.redstone, 42), Items.glowstone_dust, 7);
        addExchange(gs(Items.gunpowder, 12), Items.glowstone_dust, 8);
        //glowstone powder
        addExchange(gs(Items.glowstone_dust, 4), Blocks.glowstone, 1);
        addExchange(gs(Items.glowstone_dust, 8), Blocks.glowstone, 2);
        addExchange(gs(Items.glowstone_dust, 12), Blocks.glowstone, 3);
        addExchange(gs(Blocks.iron_ore, 2), Blocks.glowstone, 4);
        addExchange(gs(Items.glowstone_dust, 20), Blocks.glowstone, 5);
        addExchange(gs(Items.glowstone_dust, 24), Blocks.glowstone, 6);
        addExchange(gs(Items.glowstone_dust, 28), Blocks.glowstone, 7);
        addExchange(gs(Blocks.gold_ore), Blocks.glowstone, 8);
        //water recipe
        addExchange(gs(Items.water_bucket), Blocks.cactus, Blocks.cactus, Items.bucket);
        addExchange(gs(Items.water_bucket), Blocks.ice, Items.bucket);
        addSRecipe(gs(Items.water_bucket), gs(Ever), gs(Items.bucket));
        //lava recipe
        addExchange(gs(Items.lava_bucket), Items.coal, Items.redstone, Items.bucket);
        addSRecipe(gs(Items.lava_bucket), gs(Volc), gs(Items.redstone), gs(Items.bucket));
        //sand + gunpowder
        addExchange(gs(Blocks.tnt), Items.gunpowder, Items.gunpowder, Blocks.sand);
        addExchange(gs(Blocks.tnt, 2), Items.gunpowder, Items.gunpowder, Blocks.sand, Items.gunpowder, Items.gunpowder, Blocks.sand);
        //Alchemical Coal Recipe
        addExchange(gs(AlchCoal), Items.coal, Items.glowstone_dust,Items.lava_bucket);
        addExchange(gs(AlchCoal, 2), Items.coal, Items.glowstone_dust, Items.coal, Items.glowstone_dust,Items.lava_bucket);
        addExchange(gs(AlchCoal, 3), Items.coal, Items.coal, Items.coal, Items.glowstone_dust, Items.glowstone_dust, Items.glowstone_dust,Items.lava_bucket);
        addExchange(gs(AlchCoal), Items.coal, Items.glowstone_dust,Volc);
        addExchange(gs(AlchCoal, 2), Items.coal, Items.glowstone_dust, Items.coal, Items.glowstone_dust,Volc);
        addExchange(gs(AlchCoal, 3), Items.coal, Items.coal, Items.coal, Items.glowstone_dust, Items.glowstone_dust, Items.glowstone_dust,Volc);
        //Mobius Fuel Recipe
        addExchange(gs(mobiusFuel), AlchCoal, 3);
        addExchange(gs(mobiusFuel, 2), AlchCoal, 6);

        for (int i = 1; i <= 8; i++)
        {
            addExchange(gs(AlchCoal, 3 * i), mobiusFuel, i);
        }

        //Wood Recipe
        addExchange(gs(Blocks.log), Blocks.cactus, 2);
        addExchange(gs(Blocks.log, 2), Blocks.cactus, 4);
        addExchange(gs(Blocks.log, 3), Blocks.cactus, 6);
        addExchange(gs(Blocks.log, 4), Blocks.cactus, 8);
        //mushroom Recipe
        addExchange(gs(Blocks.red_mushroom), Items.glowstone_dust, Blocks.red_flower);
        addExchange(gs(Blocks.red_mushroom, 2), Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower);
        addExchange(gs(Blocks.red_mushroom, 3), Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower);
        addExchange(gs(Blocks.red_mushroom, 4), Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower, Items.glowstone_dust, Blocks.red_flower);
        addExchange(gs(Blocks.brown_mushroom), Items.glowstone_dust, Blocks.yellow_flower);
        addExchange(gs(Blocks.brown_mushroom, 2), Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower);
        addExchange(gs(Blocks.brown_mushroom, 3), Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower);
        addExchange(gs(Blocks.brown_mushroom, 4), Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower, Items.glowstone_dust, Blocks.yellow_flower);
        //apple recipe
        addExchange(gs(Items.apple), Items.redstone, Blocks.red_flower);
        addExchange(gs(Items.apple, 2), Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower);
        addExchange(gs(Items.apple, 3), Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower);
        addExchange(gs(Items.apple, 4), Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower, Items.redstone, Blocks.red_flower);
        //pumpkin recipe
        addExchange(gs(Blocks.pumpkin), Blocks.yellow_flower, Blocks.red_flower);
        addExchange(gs(Blocks.pumpkin, 2), Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower);
        addExchange(gs(Blocks.pumpkin, 3), Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower);
        addExchange(gs(Blocks.pumpkin, 4), Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower, Blocks.yellow_flower, Blocks.red_flower);
        //slimeball recipe
        addSRecipe(gs(Items.slime_ball), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Items.water_bucket));
        addSRecipe(gs(Items.slime_ball), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Ever));
        addSRecipe(gs(Items.slime_ball, 2), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Items.bucket));
        addSRecipe(gs(Items.slime_ball, 2), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Items.wheat_seeds), gs(Items.reeds), gs(Blocks.sapling), gs(Ever));
        //material block recipe
        addExchange(gs(Blocks.gold_block), Blocks.iron_block, 4);
        addExchange(gs(Blocks.gold_block, 2), Blocks.iron_block, 8);
        for(int i = 4;i <= 8;i++)
        {
        	if(i == 4||i == 8)
        	{
        		continue;
        	}
        	addExchange(gs(Blocks.iron_block,4 * i),Blocks.gold_block,i);
        }
        addExchange(gs(Blocks.diamond_block), Blocks.gold_block, 4);
        addExchange(gs(Blocks.diamond_block, 2), Blocks.gold_block, 8);
        for(int i = 1;i <= 8;i++)
        {
        	addExchange(gs(Blocks.gold_block,4 * i),Blocks.diamond_block,i);
        }
        //dye Recipe
        for (int i = 0; i < 16; i++)
        {
            addExchange(gs(Items.dye, 2, 15 - i), gs(Blocks.wool, 1, i), 3);
        }
    }
}
