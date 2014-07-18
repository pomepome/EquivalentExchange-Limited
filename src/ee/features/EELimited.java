package ee.features;

import static ee.features.Level.*;
import static ee.features.RP2Addon.*;
import ic2.api.Ic2Recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = EELimited.modid,name = "EquivalentExchange Unofficial Limited edition",version = "1a")

public class EELimited {

	public static final String modid = "EELimited";
	public static long ticks = 0;
	public static boolean skip = false;
	public static boolean debug = false;
	public static boolean isIC2Loaded = false;
	public static boolean cutDown;
	public static final CreativeTabs TabEE = new CreativeTabEE("EELimited");

	public static int IDPhil;
	public static int IDDP;
	public static int IDTorch;
	public static int IDVolc;
	public static int IDAlchCoal;
	public static int IDCov;
	public static int IDFixer;
	public static int IDMob;
	public static int IDDMB;
	public static int IDDM;
	public static int IDBand;
	public static int IDPickaxe;
	public static int IDEver;
	public static int IDWolf;
	public static int IDDD;
	public static int IDAxe;
	public static int IDShears;
	public static int IDShovel;
	public static int IDSword;

	public static Item Phil;
	public static Item DP;
	public static Item Volc;
	public static Item AlchCoal;
	public static Item mobiusFuel;
	public static Item covalanceDust;
	public static Item DM;
	public static Item Band;
	public static Item DMPickaxe;
	public static Item Ever;
	public static Item Wolf;
	public static Item Food;
	public static Item DD;
	public static Item DMAxe;
	public static Item DMShears;
	public static Item DMShovel;
	public static Item DMSword;

	public static Block EETorch;
	public static Block Fixer;
	public static Block DMB;
	public static Block newLog;

	public static Map<Integer,Map<Level, Integer>> map = new LinkedHashMap<Integer,Map<Level,Integer>>();

	@Instance(modid)
	public static EELimited instance;

	public EntityPlayer getPlayer()
	{
		return FMLClientHandler.instance().getClient().thePlayer;
	}

	@Mod.Init
	public void Init(FMLInitializationEvent event)
	{
		GameRegistry.registerPlayerTracker(new TickHandler());
		IconManager.init();
		Block.commandBlock.setCreativeTab(CreativeTabs.tabRedstone);
		loadConfig();
		instance = this;
		Phil = new ItemPhilosopherStone(IDPhil);
		DP = new ItemNonFunction(IDDP,INameRegistry.diamondPowder);
		AlchCoal = new ItemNonFunction(IDAlchCoal,INameRegistry.AlchemicCoal);
		EETorch = new BlockEETorch(IDTorch);
		covalanceDust = new ItemCov(IDCov,INameRegistry.covalanceLow);
		mobiusFuel = new ItemNonFunction(IDMob,INameRegistry.MobiusFuel);
		DM = new ItemNonFunction(IDDM,INameRegistry.Darkmatter);
		DMB = new BlockEE(IDDMB,Material.rock,INameRegistry.DMB).setHardness(10.0F);
		Band = new ItemNonFunction(IDBand,INameRegistry.ironband);
		DMPickaxe = new ItemDMPickaxe(IDPickaxe);
		Volc = new ItemVolcanite(IDVolc);
		Ever = new ItemEvertide(IDEver);
		Wolf = new ItemSwiftwolfRing(IDWolf);
		Food = new ItemFood(172,200,false).setIconIndex(Item.bread.getIconFromDamage(0)).setItemName("Food!").setCreativeTab(TabEE);
		DD = new ItemDamageDisabler(IDDD);
		DMAxe = new ItemDMAxe(IDAxe);
		DMShears = new ItemDMShears(IDShears);
		DMShovel = new ItemDMShovel(IDShovel);
		DMSword = new ItemDMSword(IDSword);
		GameRegistry.registerItem(Food, "Food!");
		MinecraftForge.setToolClass(mobiusFuel,"pickaxe",5);
		MinecraftForge.setBlockHarvestLevel(DMB,"pickaxe",5);
		//Fixer = new BlockFixer(IDFixer,"Fixer");
		Localization(Phil,"Philosopher's Stone","賢者の石");
		Localization(DP,"Diamond Powder","ダイヤモンドの粉末");
		Localization(EETorch,"Interdiction Torch","結界灯火");
		Localization(getCovalence(LOW),"Covalence Dust(Low)","共有結合粉(低)");
		Localization(getCovalence(MIDDLE),"Covalence Dust(Mid)","共有結合粉(中)");
		Localization(getCovalence(HIGH),"Covalence Dust(High)","共有結合粉(高)");
		Localization(AlchCoal,"Alchemical Coal","錬金炭");
		Localization(mobiusFuel,"Mobius Fuel","メビウス燃料");
		Localization(DMB,"Darkmatter Block","暗黒物質ブロック");
		Localization(DM,"Darkmatter","暗黒物質");
		Localization(Band,"Iron Band","鉄の腕輪");
		Localization(DMPickaxe,"Darkmetter Pickaxe","暗黒物質のツルハシ");
		Localization(Volc,"Volcanite Amulet","溶岩の祭器");
		Localization(Ever,"Evertide Amulet","常潮の祭器");
		Localization(Wolf,"Swiftwolf's Rending Gale","風統べる狼王の指輪");
		Localization(DD,"Damage Disabler","ダメージ修復機");
		Localization(DMAxe,"Darkmatter Axe","暗黒物質のオノ");
		Localization(DMShears,"Darkmatter Shears","暗黒物質のハサミ");
		Localization(DMShovel,"Darkmatter Shovel","暗黒物質のシャベル");
		Localization(DMSword,"Darkmatter Sword","ダークマターの剣");
		registerFuel();
		// * Recipes(Don't require Philosopher's Stone)
		addRecipe(gs(Item.bucketWater),"S","B",'S',Item.snowball,'B',Item.bucketEmpty);
		addRecipe(gs(Item.bucketLava),"GRG"," B ",'G',Item.gunpowder,'R',Item.redstone,'B',Item.bucketEmpty);
		addRecipe(gs(Item.bucketMilk),"B","W","E",'B',gs(Item.dyePowder,1,0xF),'W',Item.bucketWater,'E',Item.bucketEmpty);
		addRecipe(gs(Phil),"RGR","GSG","RGR",'R',Item.redstone,'G',Item.lightStoneDust,'S',Item.slimeBall);
		addSRecipe(gs(Item.lightStoneDust,4),gs(Item.coal.itemID),gs(Item.redstone));
		addSRecipe(gs(Item.redstone,4),gs(Item.coal.itemID),gs(Block.cobblestone));
		addSRecipe(gs(DM,4),DMB);
		addRecipe(gs(DMB),"DD","DD",'D',DM);
		addRecipe(gs(DMPickaxe),"DDD"," X "," X ",'D',DM,'X',Item.diamond);
		addRecipe(gs(DMAxe),"DD ","DX "," X ",'D',DM,'X',Item.diamond);
		addRecipe(gs(DMShears)," D","X ",'D',DM,'X',Item.diamond);
		addRecipe(gs(DMShovel),"D","X","X",'D',DM,'X',Item.diamond);
		addRecipe(gs(DMSword),"D","D","X",'D',DM,'X',Item.diamond);
		addRelicRecipe();
		addAlchemicalRecipe();
		addRingRecipe();
		addCovalenceRecipe();
		addFixRecipe();
		/*
		 *  * Alchemical Recipes
		 */
		addExchange(gs(Phil),gs(Item.slimeBall),gs(Item.redstone),Item.lightStoneDust);
		addExchange(gs(Phil),gs(Item.enderPearl),gs(Item.coal,1,-1),gs(Item.egg));
	}
	public void addCovalenceRecipe()
	{
		addSRecipe(changeAmount(getCovalence(LOW),3),gs(Block.cobblestone),gs(Block.cobblestone),gs(Item.coal));
		addSRecipe(changeAmount(getCovalence(MIDDLE),3),gs(Item.ingotIron),gs(Item.ingotIron),gs(Item.coal));
		addSRecipe(changeAmount(getCovalence(HIGH),3),gs(Item.diamond),gs(Item.diamond),gs(Item.coal));
		addSRecipe(changeAmount(getCovalence(LOW),5),gs(Block.cobblestone),gs(Block.cobblestone),gs(Block.cobblestone),gs(Item.redstone));
		addSRecipe(changeAmount(getCovalence(MIDDLE),5),gs(Item.ingotIron),gs(Item.ingotIron),gs(Item.ingotIron),gs(Item.redstone));
		addSRecipe(changeAmount(getCovalence(HIGH),5),gs(Item.diamond),gs(Item.diamond),gs(Item.diamond),gs(Item.redstone));
		addSRecipe(changeAmount(getCovalence(LOW),2),gs(Block.cobblestone),gs(Item.lightStoneDust),gs(Phil));
		addSRecipe(changeAmount(getCovalence(MIDDLE),2),gs(Item.ingotIron),gs(Item.lightStoneDust),gs(Phil));
		addSRecipe(changeAmount(getCovalence(HIGH),2),gs(Item.diamond),gs(Item.lightStoneDust),gs(Phil));
		addSRecipe(changeAmount(getCovalence(LOW),5),gs(Block.cobblestone),gs(Block.cobblestone),gs(AlchCoal),gs(Phil));
		addSRecipe(changeAmount(getCovalence(MIDDLE),5),gs(Item.ingotIron),gs(Item.ingotIron),gs(AlchCoal),gs(Phil));
		addSRecipe(changeAmount(getCovalence(HIGH),5),gs(Item.diamond),gs(Item.diamond),gs(AlchCoal),gs(Phil));
		addSRecipe(changeAmount(getCovalence(LOW),5),gs(Block.cobblestone),gs(mobiusFuel),gs(Phil));
		addSRecipe(changeAmount(getCovalence(MIDDLE),5),gs(Item.ingotIron),gs(mobiusFuel),gs(Phil));
		addSRecipe(changeAmount(getCovalence(HIGH),5),gs(Item.diamond),gs(mobiusFuel),gs(Phil));
	}

	public void registerFuel()
	{
		FuelHandler.register(IDAlchCoal, 1600*4);
		FuelHandler.register(IDMob,1600*4*4);
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.addSmelting(IDDP,gs(Item.diamond),1.0F);
	}

	public static EELimited getMod()
	{
		return instance;
	}

	public boolean consumeItem(Item item,int count)
	{
		if(EELimited.ticks <= 12)
		{
			return false;
		}
		if(!skip)
		{
			skip = true;
			return false;
		}
		EntityPlayer p = getPlayer();
		if(p.inventory.hasItemStack(gs(item,count)))
		{
			for(int i = 0;i < count;i++)
			{
				p.inventory.consumeInventoryItem(item.itemID);
			}
			return true;
		}
		return false;
	}

	public ItemStack getCovalence(Level lev)
	{
		switch(lev)
		{
			case HIGH   : return gs(covalanceDust);
			case MIDDLE : return gs(covalanceDust,1,1);
			case LOW    : return gs(covalanceDust,1,2);
		}
		return null;
	}

	public void addFixRecipe()
	{
		addFixRecipe(LOW,Item.shovelWood,1);
		addFixRecipe(LOW,Item.hoeWood,2);
		addFixRecipe(LOW,Item.swordWood,2);
		addFixRecipe(LOW,Item.axeWood,3);
		addFixRecipe(LOW,Item.pickaxeWood,3);
		addFixRecipe(LOW,Item.shovelStone,1);
		addFixRecipe(LOW,Item.hoeStone,2);
		addFixRecipe(LOW,Item.swordStone,2);
		addFixRecipe(LOW,Item.axeStone,3);
		addFixRecipe(LOW,Item.pickaxeStone,3);
		addFixRecipe(LOW,Item.bow,2);
		addFixRecipe(LOW,Item.fishingRod,2);
		addFixRecipe(MIDDLE,Item.shovelSteel,1);
		addFixRecipe(MIDDLE,Item.hoeSteel,2);
		addFixRecipe(MIDDLE,Item.swordSteel,2);
		addFixRecipe(MIDDLE,Item.axeSteel,3);
		addFixRecipe(MIDDLE,Item.pickaxeSteel,3);
		addFixRecipe(MIDDLE,Item.shovelGold,1);
		addFixRecipe(MIDDLE,Item.hoeGold,2);
		addFixRecipe(MIDDLE,Item.swordGold,2);
		addFixRecipe(MIDDLE,Item.axeGold,3);
		addFixRecipe(MIDDLE,Item.pickaxeGold,3);
		addFixRecipe(MIDDLE,Item.flintAndSteel,1);
		addFixRecipe(MIDDLE,Item.shears,2);
		addFixRecipe(HIGH,Item.shovelDiamond,1);
		addFixRecipe(HIGH,Item.hoeDiamond,2);
		addFixRecipe(HIGH,Item.swordDiamond,2);
		addFixRecipe(HIGH,Item.axeDiamond,3);
		addFixRecipe(HIGH,Item.pickaxeDiamond,3);
		addFixRecipe(LOW,Item.helmetLeather,5);
		addFixRecipe(MIDDLE,Item.helmetSteel,5);
		addFixRecipe(MIDDLE,Item.helmetGold,5);
		addFixRecipe(HIGH,Item.helmetDiamond,5);
		addFixRecipe(LOW,Item.plateLeather,8);
		addFixRecipe(MIDDLE,Item.plateSteel,8);
		addFixRecipe(MIDDLE,Item.plateGold,8);
		addFixRecipe(HIGH,Item.plateDiamond,8);
		addFixRecipe(LOW,Item.legsLeather,7);
		addFixRecipe(MIDDLE,Item.legsSteel,7);
		addFixRecipe(MIDDLE,Item.legsGold,7);
		addFixRecipe(HIGH,Item.legsDiamond,7);
		addFixRecipe(LOW,Item.bootsLeather,4);
		addFixRecipe(MIDDLE,Item.bootsSteel,4);
		addFixRecipe(MIDDLE,Item.bootsGold,4);
		addFixRecipe(HIGH,Item.bootsDiamond,4);
	}

	public void addFixRecipe(Level level,Item item,int amount)
	{
		ItemStack cov = getCovalence(level);
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(gs(item,1,-1));
		for(int i = 0;i < amount;i++)
		{
			list.add(cov);
		}
		addRecipe(new FixRecipe(gs(item),list));
	}
	public void addFixRecipe(Level level,ItemStack item,int amount)
	{
		ItemStack cov = getCovalence(level);
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(gs(item,1,-1));
		for(int i = 0;i < amount;i++)
		{
			list.add(cov);
		}
		addRecipe(new FixRecipe(gs(item),list));
	}

	public static void addRecipe(IRecipe recipe)
	{
		GameRegistry.addRecipe(recipe);
	}

	public static void addRecipe(ItemStack dest,Object... objs)
	{
		GameRegistry.addRecipe(dest, objs);
	}

	public static void addSRecipe(ItemStack dest,Object... objs)
	{
		GameRegistry.addShapelessRecipe(dest, objs);
	}

	public void addExchange(ItemStack dest,Object obj,int amount)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0;i < amount;i++)
		{
			list.add(gs(obj));
		}
		list.add(gs(Phil));
		addSRecipe(dest,list.toArray());
	}
	public void addExchange(ItemStack dest,Object... objs)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0;i < objs.length;i++)
		{
			list.add(gs(objs[i]));
		}
		list.add(gs(Phil));
		addSRecipe(dest,list.toArray());
	}

	public void Localization(Object obj,String english,String japanese)
	{
		LanguageRegistry.instance().addName(obj, english);
		LanguageRegistry.instance().addNameForObject(obj, "ja_JP", japanese);
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForgeClient.preloadTexture("/ee/splites/eqexsheet.png");
		MinecraftForgeClient.preloadTexture("/ee/splites/eqexterra.png");
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
		//KeyBindingRegistry.registerKeyBinding(new EEKeyHandler());
	}

	public void loadConfig()
	{
		Configuration config = new Configuration(new File(Minecraft.getMinecraftDir(),"config/EELimited.cfg"));
		config.load();
		debug = config.get(Configuration.CATEGORY_GENERAL, "Debug Mod", false).getBoolean(false);
		cutDown = config.get(config.CATEGORY_GENERAL,"Cut down from root",true).getBoolean(true);
		IDPhil=config.get(config.CATEGORY_ITEM,"Philosopher's stone ID",146).getInt();
		IDDP=config.get(config.CATEGORY_ITEM,"Diamond powder ID",147).getInt();
		IDCov=config.get(config.CATEGORY_ITEM,"Covalance Dust ID",149).getInt();
		IDTorch=config.get(config.CATEGORY_BLOCK,"Interdiction Torch ID",148).getInt();
		IDAlchCoal=config.get(config.CATEGORY_ITEM,"Alchemical Coal ID",150).getInt();
		IDFixer=config.get(config.CATEGORY_BLOCK,"Alchemical Coal ID",151).getInt();
		IDMob=config.get(config.CATEGORY_ITEM,"Mobius Fuel ID",152).getInt();
		IDDM=config.get(config.CATEGORY_ITEM,"Darkmatter ID",153).getInt();
		IDDMB=config.get(config.CATEGORY_BLOCK,"Darkmatter Block ID",154).getInt();
		IDBand=config.get(config.CATEGORY_ITEM,"IronBand ID",155).getInt();
		IDPickaxe=config.get(config.CATEGORY_ITEM,"DM Pickaxe ID",156).getInt();
		IDVolc=config.get(config.CATEGORY_ITEM,"Volcanite ID",157).getInt();
		IDEver=config.get(config.CATEGORY_ITEM,"Evertide ID",158).getInt();
		IDWolf=config.get(config.CATEGORY_ITEM,"Swiftwolf's Ring ID",159).getInt();
		IDDD=config.get(config.CATEGORY_ITEM,"Damage Disabler ID",160).getInt();
		IDAxe=config.get(config.CATEGORY_ITEM,"Darkmatter Axe ID",161).getInt();
		IDShears=config.get(config.CATEGORY_ITEM,"Darkmatter Shears ID",162).getInt();
		IDShovel=config.get(config.CATEGORY_ITEM,"Darkmatter Shovel ID",163).getInt();
		IDSword=config.get(config.CATEGORY_ITEM,"Darkmatter Sword ID",164).getInt();
		config.save();
	}

	public static void addSmeltingExchange()
	{
		FurnaceRecipes recipes = FurnaceRecipes.smelting();
		Map<Integer,ItemStack> map0 = recipes.getSmeltingList();
		Set<Integer> keyset0 = map0.keySet();
		Iterator it0 = keyset0.iterator();
		while(it0.hasNext())
		{
			Integer itemID = (Integer)it0.next();
			ItemStack result = map0.get(itemID);
			addSRecipe(result,gs(Phil),gs(Phil),gs(itemID.intValue()));
		}
		Map<List<Integer>,ItemStack> map1 = recipes.getMetaSmeltingList();
		Set<List<Integer>> keyset1 = map1.keySet();
		Iterator it1 = keyset1.iterator();
		while(it1.hasNext())
		{
			List<Integer> list = (List<Integer>)it1.next();
			int itemID = list.get(0);
			int damage = list.get(1);
			ItemStack result = map1.get(list);
			addSRecipe(result,gs(Phil),gs(Phil),gs(itemID,1,damage));
		}
	}

	public boolean isOreAdded(String name)
	{
		return !OreDictionary.getOres(name).isEmpty();
	}

	public Item findItemFromName(String name)
	{
		for(int i = 0;i < Item.itemsList.length;i++)
		{
			if(Item.itemsList[i] != null&&Item.itemsList[i].getItemName()==name)
			{
				return Item.itemsList[i];
			}
		}
		return null;
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.registerFuelHandler(new FuelHandler());
		if(ModLoader.isModLoaded("RedPowerCore"))
		{
			RP2Addon();
		}
	}

	public ItemStack getIC2Item(String str)
	{
		try {
			return (ItemStack)(Class.forName("ic2.core.Ic2Items").getField(str).get(null));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addIC2Recipe()
	{
		addFixRecipe(LOW,getIC2Item(INameRegistry.treetap),5);
		addFixRecipe(MIDDLE,getIC2Item(INameRegistry.wrench),6);
		/*
		addFixRecipe(MIDDLE,getIC2Item("bronzeAxe"),3);
		addFixRecipe(MIDDLE,getIC2Item("bronzeHoe"),3);
		addFixRecipe(MIDDLE,getIC2Item("bronzeSword"),2);
		addFixRecipe(MIDDLE,getIC2Item("bronzeShovel"),1);
		addFixRecipe(MIDDLE,getIC2Item("bronzeHelmet"),5);
		addFixRecipe(MIDDLE,getIC2Item("bronzeChestplate"),8);
		addFixRecipe(MIDDLE,getIC2Item("bronzeLeggings"),7);
		addFixRecipe(MIDDLE,getIC2Item("bronzeBoots"),4);
		*/
		Ic2Recipes.addMaceratorRecipe(gs(Item.diamond),gs(DP));
		Ic2Recipes.addMaceratorRecipe(gs(Block.oreDiamond),gs(DP,2));
		List<Entry<ItemStack,ItemStack>> recipes0 = Ic2Recipes.getMaceratorRecipes();
		for(int i = 0;i < recipes0.size();i++)
		{
			Entry<ItemStack,ItemStack> entry = recipes0.get(i);
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			int count = input.stackSize;
			input.stackSize = 1;
			if(count > 6)
			{
				continue;
			}
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			for(int j = 0;j < count;j++)
			{
				list.add(input);
			}
			addSRecipe(output,list.toArray());
		}
		List<Entry<ItemStack,ItemStack>> recipes1 = Ic2Recipes.getExtractorRecipes();
		for(int i = 0;i < recipes1.size();i++)
		{
			Entry<ItemStack,ItemStack> entry = recipes1.get(i);
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			int count = input.stackSize;
			input.stackSize = 1;
			if(count > 5)
			{
				continue;
			}
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			for(int j = 0;j < count;j++)
			{
				list.add(input);
			}
			addSRecipe(output,list.toArray());
		}
		List<Entry<ItemStack,ItemStack>> recipes2 = Ic2Recipes.getCompressorRecipes();
		for(int i = 0;i < recipes2.size();i++)
		{
			Entry<ItemStack,ItemStack> entry = recipes2.get(i);
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			int count = input.stackSize;
			input.stackSize = 1;
			if(count > 4)
			{
				continue;
			}
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			list.add(gs(Phil));
			for(int j = 0;j < count;j++)
			{
				list.add(input);
			}
			addSRecipe(output,list.toArray());
		}
	}

	public static ItemStack gs(Object obj)
	{
		if(obj instanceof Item)
		{
			return new ItemStack((Item)obj);
		}
		else if(obj instanceof Block)
		{
			return new ItemStack((Block)obj);
		}
		else if(obj instanceof ItemStack)
		{
			return (ItemStack)obj;
		}
		else if(obj instanceof Integer)
		{
			return gs(((Integer)obj).intValue());
		}
		else
		{
			return null;
		}
	}

	public static ItemStack gs(Object obj,int amount)
	{
		ItemStack is = gs(obj);
		if(is!=null)
		{
			is.stackSize = amount;
		}
		return is;
	}

	public static ItemStack gs(Object obj,int amount,int damage)
	{
		ItemStack is = gs(obj);
		if(is!=null)
		{
			is.stackSize = amount;
			is.setItemDamage(damage);
		}
		return is;
	}

	public static ItemStack gs(int obj)
	{
		return new ItemStack(obj,1,-1);
	}

	public static ItemStack gs(int obj,int amount)
	{
		return new ItemStack(obj,amount,-1);
	}

	public static ItemStack gs(int obj,int amount,int damage)
	{
		return new ItemStack(obj,amount,damage);
	}

	public static ItemStack getOre(String name)
	{
		return OreDictionary.getOres(name).get(0);
	}

	public static ItemStack changeAmount(ItemStack is,int amount)
	{
		return gs(is.itemID,amount,is.getItemDamage());
	}

	public void addAlchemicalRecipe()
	{
		addRecipe(gs(DM),"DBD","BPB","DBD",'D',Block.blockDiamond,'B',mobiusFuel,'P',Phil);
		addRecipe(gs(DM),"DBD","BPB","DBD",'B',Block.blockDiamond,'D',mobiusFuel,'P',Phil);
		addExchange(gs(Block.blockDiamond,4),DM);
		addExchange(gs(Item.coal),gs(Item.coal,1,1));
		// Dirt
		addExchange(gs(Block.sand),Block.dirt);
		addExchange(gs(Block.blockClay),Block.dirt,2);
		addExchange(gs(Block.sand,3),Block.dirt,3);
		addExchange(gs(Block.cobblestone),Block.dirt,4);
		addExchange(gs(Block.sand,5),Block.dirt,5);
		addExchange(gs(Block.blockClay,3),Block.dirt,6);
		addExchange(gs(Block.sand,7),Block.dirt,7);
		addExchange(gs(Block.cobblestone,2),Block.dirt,8);
		//Sand
		addExchange(gs(Block.dirt),Block.sand,1);
		addExchange(gs(Block.blockClay),Block.sand,2);
		addExchange(gs(Block.dirt,3),Block.sand,3);
		addExchange(gs(Block.blockClay,2),Block.sand,4);
		addExchange(gs(Block.dirt,5),Block.sand,5);
		addExchange(gs(Block.blockClay,3),Block.sand,6);
		addExchange(gs(Block.dirt,7),Block.sand,7);
		addExchange(gs(Block.blockClay,4),Block.sand,8);
		//sand + glowStoneDust
		addExchange(gs(Block.slowSand),Block.sand,Item.lightStoneDust);
		addExchange(gs(Block.slowSand,2),Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust);
		addExchange(gs(Block.slowSand,3),Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust);
		addExchange(gs(Block.slowSand,4),Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust,Block.sand,Item.lightStoneDust);
		//cobbleStone + glowStone
		addExchange(gs(Block.slowSand,4),Block.cobblestone,Block.glowStone);
		addExchange(gs(Block.slowSand,8),Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone);
		addExchange(gs(Block.slowSand,12),Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone);
		addExchange(gs(Block.slowSand,16),Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone,Block.cobblestone,Block.glowStone);
		//Sandstone
		addExchange(gs(Block.sand,4),Block.sandStone,1);
		addExchange(gs(Block.sand,8),Block.sandStone,2);
		addExchange(gs(Block.sand,12),Block.sandStone,3);
		addExchange(gs(Block.cobblestone,4),Block.sandStone,4);
		addExchange(gs(Block.sand,20),Block.sandStone,5);
		addExchange(gs(Block.sand,24),Block.sandStone,6);
		addExchange(gs(Block.sand,28),Block.sandStone,7);
		addExchange(gs(Block.oreRedstone,1),Block.sandStone,8);
		//dirt + sand
		addExchange(gs(Block.gravel),Block.dirt,Block.sand);
		addExchange(gs(Block.gravel,2),Block.dirt,Block.sand,Block.dirt,Block.sand);
		addExchange(gs(Block.gravel,3),Block.dirt,Block.sand,Block.dirt,Block.sand,Block.dirt,Block.sand);
		addExchange(gs(Block.gravel,4),Block.dirt,Block.sand,Block.dirt,Block.sand,Block.dirt,Block.sand,Block.dirt,Block.sand);
		//gravel
		addExchange(gs(Block.sand,2),Block.gravel,1);
		addExchange(gs(Block.dirt,4),Block.gravel,2);
		addExchange(gs(Item.flint,2),Block.gravel,3);
		addExchange(gs(Block.dirt,8),Block.gravel,4);
		addExchange(gs(Block.sand,10),Block.gravel,5);
		addExchange(gs(Item.flint,4),Block.gravel,6);
		addExchange(gs(Block.sand,14),Block.gravel,7);
		addExchange(gs(Block.dirt,16),Block.gravel,8);
		//flint
		addExchange(gs(Item.clay,3),Item.flint,1);
		addExchange(gs(Block.gravel,3),Item.flint,2);
		addExchange(gs(Item.clay,9),Item.flint,3);
		addExchange(gs(Block.gravel,6),Item.flint,4);
		addExchange(gs(Item.clay,15),Item.flint,5);
		addExchange(gs(Block.gravel,9),Item.flint,6);
		addExchange(gs(Item.clay,21),Item.flint,7);
		addExchange(gs(Block.gravel,12),Item.flint,8);
		//clayBlock
		addExchange(gs(Item.clay,4),Block.blockClay,1);
		addExchange(gs(Block.cobblestone),Block.blockClay,2);
		addExchange(gs(Item.clay,12),Block.blockClay,3);
		addExchange(gs(Block.cobblestone,2),Block.blockClay,4);
		addExchange(gs(Item.clay,20),Block.blockClay,5);
		addExchange(gs(Block.cobblestone,3),Block.blockClay,6);
		addExchange(gs(Item.clay,28),Block.blockClay,7);
		addExchange(gs(Block.cobblestone,4),Block.blockClay,8);
		//clay
		addExchange(gs(Block.dirt),Item.clay,2);
		addExchange(gs(Block.dirt,2),Item.clay,4);
		addExchange(gs(Block.dirt,3),Item.clay,6);
		addExchange(gs(Block.dirt,4),Item.clay,8);
		//cobblestone
		addExchange(gs(Block.dirt,4),Block.cobblestone,1);
		addExchange(gs(Block.blockClay,4),Block.cobblestone,2);
		addExchange(gs(Block.dirt,12),Block.cobblestone,3);
		addExchange(gs(Block.blockClay,8),Block.cobblestone,4);
		addExchange(gs(Block.dirt,20),Block.cobblestone,5);
		addExchange(gs(Block.blockClay,12),Block.cobblestone,6);
		addExchange(gs(Block.dirt,28),Block.cobblestone,7);
		addExchange(gs(Block.oreRedstone,1),Block.cobblestone,8);
		//cobblestone + redstone
		addExchange(gs(Block.netherrack),Block.cobblestone,Item.redstone);
		addExchange(gs(Block.netherrack,2),Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone);
		addExchange(gs(Block.netherrack,3),Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone);
		addExchange(gs(Block.netherrack,4),Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone,Block.cobblestone,Item.redstone);
		//stone
		addExchange(gs(Block.cobblestone),Block.stone,1);
		addExchange(gs(Block.cobblestone,2),Block.stone,2);
		addExchange(gs(Block.cobblestone,3),Block.stone,3);
		addExchange(gs(Block.cobblestone,4),Block.stone,4);
		addExchange(gs(Block.cobblestone,5),Block.stone,5);
		addExchange(gs(Block.cobblestone,6),Block.stone,6);
		addExchange(gs(Block.cobblestone,7),Block.stone,7);
		addExchange(gs(Block.oreRedstone),Block.stone,8);
		//redstoneOre
		addExchange(gs(Block.cobblestone,8),Block.oreRedstone,1);
		addExchange(gs(Item.redstone,9),Block.oreRedstone,2);
		addExchange(gs(Block.cobblestone,24),Block.oreRedstone,3);
		addExchange(gs(Block.oreIron),Block.oreRedstone,4);
		addExchange(gs(Block.cobblestone,40),Block.oreRedstone,5);
		addExchange(gs(Item.redstone,27),Block.oreRedstone,6);
		addExchange(gs(Block.cobblestone,56),Block.oreRedstone,7);
		addExchange(gs(Block.oreIron,2),Block.oreRedstone,8);
		//ironOre
		addExchange(gs(Block.cobblestone,32),Block.oreIron,1);
		addExchange(gs(Block.cobblestone,64),Block.oreIron,2);
		addExchange(gs(Block.oreRedstone,12),Block.oreIron,3);
		addExchange(gs(Block.oreGold,1),Block.oreIron,4);
		addExchange(gs(Block.oreRedstone,20),Block.oreIron,5);
		addExchange(gs(Block.oreRedstone,24),Block.oreIron,6);
		addExchange(gs(Block.oreRedstone,28),Block.oreIron,7);
		addExchange(gs(Block.oreGold,2),Block.oreIron,8);
		//goldOre
		addExchange(gs(Block.oreIron,4),Block.oreGold,1);
		addExchange(gs(Block.oreIron,8),Block.oreGold,2);
		addExchange(gs(Block.oreIron,12),Block.oreGold,3);
		addExchange(gs(Block.oreDiamond,1),Block.oreGold,4);
		addExchange(gs(Block.oreIron,20),Block.oreGold,5);
		addExchange(gs(Block.oreIron,24),Block.oreGold,6);
		addExchange(gs(Block.oreIron,28),Block.oreGold,7);
		addExchange(gs(Block.oreDiamond,2),Block.oreGold,8);
		//diamondOre
		addExchange(gs(Block.oreGold,4),Block.oreDiamond,1);
		addExchange(gs(Block.oreGold,8),Block.oreDiamond,2);
		addExchange(gs(Block.oreGold,12),Block.oreDiamond,3);
		addExchange(gs(Block.oreGold,16),Block.oreDiamond,4);
		addExchange(gs(Block.oreGold,20),Block.oreDiamond,5);
		addExchange(gs(Block.oreGold,24),Block.oreDiamond,6);
		addExchange(gs(Block.oreGold,28),Block.oreDiamond,7);
		addExchange(gs(Block.oreGold,32),Block.oreDiamond,8);
		//soulSand
		addExchange(gs(Block.sand,2),Block.slowSand,1);
		addExchange(gs(Block.sand,4),Block.slowSand,2);
		addExchange(gs(Block.sand,6),Block.slowSand,3);
		addExchange(gs(Block.sand,8),Block.slowSand,4);
		addExchange(gs(Block.sand,10),Block.slowSand,5);
		addExchange(gs(Block.sand,12),Block.slowSand,6);
		addExchange(gs(Block.sand,14),Block.slowSand,7);
		addExchange(gs(Block.sand,16),Block.slowSand,8);
		//Netherrack
		addExchange(gs(Block.stone),Block.netherrack,1);
		addExchange(gs(Block.stone,2),Block.netherrack,2);
		addExchange(gs(Block.stone,3),Block.netherrack,3);
		addExchange(gs(Block.stone,4),Block.netherrack,4);
		addExchange(gs(Block.stone,5),Block.netherrack,5);
		addExchange(gs(Block.stone,6),Block.netherrack,6);
		addExchange(gs(Block.stone,7),Block.netherrack,7);
		addExchange(gs(Block.oreRedstone),Block.netherrack,8);
		//glass
		addExchange(gs(Block.sand,3),Block.glass,2);
		addExchange(gs(Block.sand,6),Block.glass,4);
		addExchange(gs(Block.sand,9),Block.glass,6);
		addExchange(gs(Block.ice),Block.glass,8);
		//ice
		addExchange(gs(Block.glass,8),Block.ice,1);
		addExchange(gs(Block.glass,16),Block.ice,2);
		addExchange(gs(Block.glass,24),Block.ice,3);
		addExchange(gs(Block.glass,32),Block.ice,4);
		addExchange(gs(Block.glass,40),Block.ice,5);
		addExchange(gs(Block.glass,48),Block.ice,6);
		addExchange(gs(Block.glass,56),Block.ice,7);
		addExchange(gs(Item.dyePowder,1,4),Block.ice,8);
		//lapisLazuli
		addExchange(gs(Block.ice,16),gs(Item.dyePowder,1,4),2);
		addExchange(gs(Block.obsidian,3),gs(Item.dyePowder,1,4),3);
		addExchange(gs(Block.ice,32),gs(Item.dyePowder,1,4),4);
		addExchange(gs(Block.obsidian,5),gs(Item.dyePowder,1,4),5);
		addExchange(gs(Block.ice,48),gs(Item.dyePowder,1,4),6);
		addExchange(gs(Block.obsidian,7),gs(Item.dyePowder,1,4),7);
		addExchange(gs(Block.oreLapis),gs(Item.dyePowder,1,4),8);
		//lapisBlock
		addExchange(gs(Block.obsidian,9),Block.blockLapis,1);
		addExchange(gs(Block.obsidian,18),Block.blockLapis,2);
		addExchange(gs(Block.obsidian,27),Block.blockLapis,3);
		addExchange(gs(Block.obsidian,36),Block.blockLapis,4);
		addExchange(gs(Block.obsidian,45),Block.blockLapis,5);
		addExchange(gs(Block.obsidian,54),Block.blockLapis,6);
		addExchange(gs(Block.obsidian,63),Block.blockLapis,7);
		addExchange(gs(Item.diamond,2),Block.blockLapis,Block.blockLapis,Block.blockLapis,Block.blockLapis,Block.blockLapis,Block.blockLapis,Block.blockLapis,Block.obsidian);
		//obsidian
		addExchange(gs(Item.dyePowder,1,4),Block.obsidian);
		addExchange(gs(Item.dyePowder,2,4),Block.obsidian,2);
		addExchange(gs(Item.dyePowder,3,4),Block.obsidian,3);
		addExchange(gs(Item.dyePowder,4,4),Block.obsidian,4);
		addExchange(gs(Item.dyePowder,5,4),Block.obsidian,5);
		addExchange(gs(Item.dyePowder,6,4),Block.obsidian,6);
		addExchange(gs(Item.dyePowder,7,4),Block.obsidian,7);
		addExchange(gs(Block.oreLapis),Block.obsidian,8);
		//lapisOre
		addExchange(gs(Item.dyePowder,8,4),Block.oreLapis,1);
		addExchange(gs(Block.obsidian,16),Block.oreLapis,2);
		addExchange(gs(Item.dyePowder,24,4),Block.oreLapis,3);
		addExchange(gs(Item.diamond),Block.oreLapis,4);
		addExchange(gs(Item.dyePowder,40,4),Block.oreLapis,5);
		addExchange(gs(Block.obsidian,48),Block.oreLapis,6);
		addExchange(gs(Item.dyePowder,56,4),Block.oreLapis,7);
		addExchange(gs(Item.diamond,2),Block.oreLapis,8);
		//iron
		addExchange(gs(Item.lightStoneDust,6),Item.ingotIron,1);
		addExchange(gs(Block.glowStone,3),Item.ingotIron,2);
		addExchange(gs(Item.lightStoneDust,18),Item.ingotIron,3);
		addExchange(gs(Item.ingotGold),Item.ingotIron,4);
		addExchange(gs(Item.lightStoneDust,30),Item.ingotIron,5);
		addExchange(gs(Block.glowStone,9),Item.ingotIron,6);
		addExchange(gs(Item.lightStoneDust,42),Item.ingotIron,7);
		addExchange(gs(Item.ingotGold,2),Item.ingotIron,8);
		//gold
		addExchange(gs(Item.ingotIron,4),Item.ingotGold,1);
		addExchange(gs(Item.ingotIron,8),Item.ingotGold,2);
		addExchange(gs(Item.ingotIron,12),Item.ingotGold,3);
		addExchange(gs(Item.diamond),Item.ingotGold,4);
		addExchange(gs(Item.ingotIron,20),Item.ingotGold,5);
		addExchange(gs(Item.ingotIron,24),Item.ingotGold,6);
		addExchange(gs(Item.ingotIron,28),Item.ingotGold,7);
		addExchange(gs(Item.diamond,2),Item.ingotGold,8);
		//cobblestone + seed
		addExchange(gs(Block.cobblestoneMossy),Block.cobblestone,Item.seeds);
		addExchange(gs(Block.cobblestoneMossy,2),Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds);
		addExchange(gs(Block.cobblestoneMossy,3),Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds);
		addExchange(gs(Block.cobblestoneMossy,4),Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds,Block.cobblestone,Item.seeds);
		//dirt + seed
		addExchange(gs(Block.grass),Block.dirt,Item.seeds);
		addExchange(gs(Block.grass,2),Block.dirt,Item.seeds,Block.dirt,Item.seeds);
		addExchange(gs(Block.grass,3),Block.dirt,Item.seeds,Block.dirt,Item.seeds,Block.dirt,Item.seeds);
		addExchange(gs(Block.grass,4),Block.dirt,Item.seeds,Block.dirt,Item.seeds,Block.dirt,Item.seeds,Block.dirt,Item.seeds);
		//Gold + apple
		addExchange(gs(Item.appleGold),Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.ingotGold,Item.appleRed);
		//coal
		addExchange(gs(Block.cobblestone),Item.coal,1);
		addExchange(gs(Item.redstone,3),Item.coal,2);
		addExchange(gs(Block.cobblestone,3),Item.coal,3);
		addExchange(gs(Item.lightStoneDust),Item.coal,4);
		addExchange(gs(Block.cobblestone,5),Item.coal,5);
		addExchange(gs(Item.redstone,9),Item.coal,6);
		addExchange(gs(Block.cobblestone,7),Item.coal,7);
		addExchange(gs(Item.lightStoneDust,2),Item.coal,8);
		//sugar
		addExchange(gs(Item.redstone),Item.sugar,1);
		addExchange(gs(Item.redstone,2),Item.sugar,2);
		addExchange(gs(Item.coal,2),Item.sugar,3);
		addExchange(gs(Item.gunpowder),Item.sugar,4);
		addExchange(gs(Item.redstone,5),Item.sugar,5);
		addExchange(gs(Item.lightStoneDust),Item.sugar,6);
		addExchange(gs(Item.redstone,7),Item.sugar,7);
		addExchange(gs(Item.gunpowder,2),Item.sugar,8);
		//redstone
		addExchange(gs(Item.coal,2),Item.redstone,3);
		addExchange(gs(Item.gunpowder),Item.redstone,4);
		addExchange(gs(Item.lightStoneDust),Item.redstone,6);
		addExchange(gs(Item.gunpowder,2),Item.redstone,8);
		//redstone + coal
		addExchange(gs(Item.gunpowder),Item.redstone,Item.coal,Item.coal);
		addExchange(gs(Item.gunpowder,2),Item.redstone,Item.coal,Item.coal,Item.redstone,Item.coal,Item.coal);
		//gunpowder
		addExchange(gs(Item.redstone,4),Item.gunpowder,1);
		addExchange(gs(Item.redstone,8),Item.gunpowder,2);
		addExchange(gs(Item.lightStoneDust,2),Item.gunpowder,3);
		addExchange(gs(Item.redstone,16),Item.gunpowder,4);
		addExchange(gs(Item.redstone,20),Item.gunpowder,5);
		addExchange(gs(Block.glowStone,4),Item.gunpowder,6);
		addExchange(gs(Item.redstone,28),Item.gunpowder,7);
		addExchange(gs(Item.redstone,32),Item.gunpowder,8);
		//glowstone powder
		addExchange(gs(Item.redstone,6),Item.lightStoneDust,1);
		addExchange(gs(Item.gunpowder,3),Item.lightStoneDust,2);
		addExchange(gs(Item.redstone,18),Item.lightStoneDust,3);
		addExchange(gs(Item.gunpowder,6),Item.lightStoneDust,4);
		addExchange(gs(Item.redstone,30),Item.lightStoneDust,5);
		addExchange(gs(Item.gunpowder,9),Item.lightStoneDust,6);
		addExchange(gs(Item.redstone,42),Item.lightStoneDust,7);
		addExchange(gs(Item.gunpowder,12),Item.lightStoneDust,8);
		//glowstone powder
		addExchange(gs(Item.lightStoneDust,4),Block.glowStone,1);
		addExchange(gs(Item.lightStoneDust,8),Block.glowStone,2);
		addExchange(gs(Item.lightStoneDust,12),Block.glowStone,3);
		addExchange(gs(Block.oreIron,2),Block.glowStone,4);
		addExchange(gs(Item.lightStoneDust,20),Block.glowStone,5);
		addExchange(gs(Item.lightStoneDust,24),Block.glowStone,6);
		addExchange(gs(Item.lightStoneDust,28),Block.glowStone,7);
		addExchange(gs(Block.oreGold),Block.glowStone,8);
		//water recipe
		addExchange(gs(Item.bucketWater),Block.cactus,Block.cactus,Item.bucketEmpty);
		addExchange(gs(Item.bucketWater),Block.ice,Item.bucketEmpty);
		addSRecipe(gs(Item.bucketWater),gs(Ever),gs(Item.bucketEmpty));
		//lava recipe
		addExchange(gs(Item.bucketLava),Item.coal,Item.redstone,Item.bucketEmpty);
		addSRecipe(gs(Item.bucketLava),gs(Volc),gs(Item.redstone),gs(Item.bucketEmpty));
		//sand + gunpowder
		addExchange(gs(Block.tnt),Item.gunpowder,Item.gunpowder,Block.sand);
		addExchange(gs(Block.tnt,2),Item.gunpowder,Item.gunpowder,Block.sand,Item.gunpowder,Item.gunpowder,Block.sand);
		//Alchemical Coal Recipe
		addExchange(gs(AlchCoal),Item.coal,Item.lightStoneDust,Item.bucketLava);
		addExchange(gs(AlchCoal),Item.coal,Item.lightStoneDust,Volc);
		addExchange(gs(AlchCoal,2),Item.coal,Item.lightStoneDust,Item.coal,Item.lightStoneDust,Item.bucketLava);
		addExchange(gs(AlchCoal,2),Item.coal,Item.lightStoneDust,Item.coal,Item.lightStoneDust,Volc);
		addExchange(gs(AlchCoal,3),Item.coal,Item.coal,Item.coal,Item.lightStoneDust,Item.lightStoneDust,Item.lightStoneDust,Item.bucketLava);
		addExchange(gs(AlchCoal,3),Item.coal,Item.coal,Item.coal,Item.lightStoneDust,Item.lightStoneDust,Item.lightStoneDust,Volc);
		//Mobius Fuel Recipe
		addExchange(gs(mobiusFuel),AlchCoal,3);
		addExchange(gs(mobiusFuel,2),AlchCoal,6);
		addExchange(gs(Block.glowStone),mobiusFuel);
		//Wood Recipe
		addExchange(gs(Block.wood),Block.cactus,2);
		addExchange(gs(Block.wood,2),Block.cactus,4);
		addExchange(gs(Block.wood,3),Block.cactus,6);
		addExchange(gs(Block.wood,4),Block.cactus,8);
		//mushroom Recipe
		addExchange(gs(Block.mushroomRed),Item.lightStoneDust,Block.plantRed);
		addExchange(gs(Block.mushroomRed,2),Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed);
		addExchange(gs(Block.mushroomRed,3),Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed);
		addExchange(gs(Block.mushroomRed,4),Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed,Item.lightStoneDust,Block.plantRed);
		addExchange(gs(Block.mushroomBrown),Item.lightStoneDust,Block.plantRed);
		addExchange(gs(Block.mushroomBrown,2),Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow);
		addExchange(gs(Block.mushroomBrown,3),Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow);
		addExchange(gs(Block.mushroomBrown,4),Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow,Item.lightStoneDust,Block.plantYellow);
		//apple recipe
		addExchange(gs(Item.appleRed),Item.redstone,Block.plantRed);
		addExchange(gs(Item.appleRed,2),Item.redstone,Block.plantRed,Item.redstone,Block.plantRed);
		addExchange(gs(Item.appleRed,3),Item.redstone,Block.plantRed,Item.redstone,Block.plantRed,Item.redstone,Block.plantRed);
		addExchange(gs(Item.appleRed,4),Item.redstone,Block.plantRed,Item.redstone,Block.plantRed,Item.redstone,Block.plantRed,Item.redstone,Block.plantRed);
		//pumpkin recipe
		addExchange(gs(Block.pumpkin),Block.plantYellow,Block.plantRed);
		addExchange(gs(Block.pumpkin,2),Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed);
		addExchange(gs(Block.pumpkin,3),Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed);
		addExchange(gs(Block.pumpkin,4),Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed,Block.plantYellow,Block.plantRed);
		//slimeball recipe
		addSRecipe(gs(Item.slimeBall),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Item.bucketWater));
		addSRecipe(gs(Item.slimeBall),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Ever));
		addSRecipe(gs(Item.slimeBall,2),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Item.bucketWater));
		addSRecipe(gs(Item.slimeBall,2),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Item.seeds),gs(Item.reed),gs(Block.sapling),gs(Ever));
		//material block recipe
		addExchange(gs(Block.blockGold),Block.blockSteel,4);
		addExchange(gs(Block.blockGold,2),Block.blockSteel,8);
		addExchange(gs(Block.blockDiamond),Block.blockGold,4);
		addExchange(gs(Block.blockDiamond,2),Block.blockGold,8);
		//dye Recipe
		for(int i = 0;i < 16;i++)
		{
			addExchange(gs(Item.dyePowder,2,15 - i),gs(Block.cloth,1,i),3);
		}
	}
	public ItemStack[] gti(ItemStack... objs)
	{
		return objs;
	}
	public void addRingRecipe()
	{
		addRingRecipe(Block.wood,4);
		addRingRecipe(Block.sapling,4);
		addRingRecipe(Block.stoneBrick,4);
		addRingRecipe(Block.cloth,0x10);
		addRingRecipe(Item.dyePowder,0x10);
		addRingRecipe(gti(gs(Item.porkRaw),gs(Item.beefRaw),gs(Item.chickenRaw),gs(Item.fishRaw)));
		addRingRecipe(gti(gs(Item.porkCooked),gs(Item.beefCooked),gs(Item.chickenCooked),gs(Item.fishCooked)));
	}
	public void addRingRecipe(Object obj,int count)
	{
		for(int i = 0;i < count;i++)
		{
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(Phil));
			list.add(gs(obj,1,i));
			addSRecipe(gs(obj,1,(i + 1) % count),list.toArray());
		}
	}
	public void addRingRecipe(ItemStack[] objs)
	{
		int l = objs.length;
		for(int i = 0;i < l;i++)
		{
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(Phil));
			list.add(objs[i]);
			addSRecipe(objs[(i + 1) % l],list.toArray());
		}
	}
	public void addRelicRecipe()
	{
		addRecipe(gs(Ever),"WWW","DDD","WWW",'W',Item.bucketWater,'D',DM);
		addRecipe(gs(Volc),"WWW","DDD","WWW",'W',Item.bucketLava,'D',DM);
		addRecipe(gs(Ever),"IDI","WEW","WIW",'W',Item.bucketWater,'I',Item.ingotIron,'D',Item.diamond,'E',Ever);
		addRecipe(gs(Volc),"IDI","WEW","WIW",'W',Item.bucketLava,'I',Item.ingotIron,'D',Item.diamond,'E',Volc);
		addRecipe(gs(Ever),"IDI","WEW","WIW",'W',Ever,'I',Item.ingotIron,'D',Item.diamond,'E',Ever);
		addRecipe(gs(Volc),"IDI","WEW","WIW",'W',Volc,'I',Item.ingotIron,'D',Item.diamond,'E',Volc);
		addRecipe(gs(Band),"III","ILI","III",'I',Item.ingotIron,'L',Item.bucketLava);
		addRecipe(gs(Band),"III","ILI","III",'I',Item.ingotIron,'L',Volc);
		addRecipe(gs(Wolf),"DFD","FBF","DFD",'D',DM,'F',Item.feather,'B',Band);
		addRecipe(gs(DD),"DDD","DBD","DDD",'D',DMB,'B',Band);
	}
	public void RP2Addon()
	{
		RP2Addon.initBase();
		RP2Addon.initWorld();
		addRecipe(new ShapelessOreRecipe(OreDictionary.getOres("dustNikolite").get(0),gs(Phil),gs(Item.coal,-1)));
		addSRecipe(OreDictionary.getOres(INameRegistry.IngotRed).get(0),gs(Phil),gs(Item.ingotIron),gs(Item.redstone),gs(Item.redstone),gs(Item.redstone),gs(Item.redstone));
		addRecipe(new ShapelessOreRecipe(OreDictionary.getOres(INameRegistry.IngotRed).get(0),gs(Phil),"ingotCopper",gs(Item.redstone),gs(Item.redstone),gs(Item.redstone),gs(Item.redstone)));
		addRecipe(new ShapelessOreRecipe(OreDictionary.getOres(INameRegistry.IngotBlue).get(0),gs(Phil),"ingotSilver","dustNikolite","dustNikolite","dustNikolite","dustNikolite"));
		addRecipe(new ShapelessOreRecipe(getOre(INameRegistry.BouleSilicon),gs(Phil),gs(Block.sand),gs(Block.sand),gs(Block.sand),gs(Block.sand),gs(Block.sand),gs(Block.sand),gs(Block.sand),gs(Block.sand)));
		addRecipe(new ShapelessOreRecipe(getOre(INameRegistry.WaferBlue),gs(Phil),INameRegistry.WaferSilicon,"dustNikolite","dustNikolite","dustNikolite","dustNikolite"));
		addRecipe(new ShapelessOreRecipe(getOre(INameRegistry.WaferRed),gs(Phil),INameRegistry.WaferSilicon,gs(Item.redstone),gs(Item.redstone),gs(Item.redstone),gs(Item.redstone)));
		addRecipe(new ShapelessOreRecipe(getOre(INameRegistry.Tinplate),gs(Phil),"ingotTin",gs(Item.ingotIron),gs(Item.ingotIron)));
		addRecipe(new ShapelessOreRecipe(getOre(INameRegistry.IngotBrass),gs(Phil),"ingotCopper","ingotCopper","ingotCopper","ingotTin"));
		addRecipe(new ShapelessOreRecipe(gs(getOre(INameRegistry.WaferSilicon).itemID,16,getOre(INameRegistry.WaferSilicon).getItemDamage()),gs(Phil),INameRegistry.BouleSilicon));
		addFixRecipe(LOW,rp2SickleWood,3);
		addFixRecipe(LOW,rp2SickleStone,3);
		addFixRecipe(MIDDLE,rp2SickleIron,3);
		addFixRecipe(MIDDLE,rp2SickleGold,3);
		addFixRecipe(MIDDLE,rp2PickaxeSapphire,3);
		addFixRecipe(MIDDLE,rp2PickaxeRuby,3);
		addFixRecipe(MIDDLE,rp2PickaxeEmerald,3);
		addFixRecipe(MIDDLE,rp2AxeSapphire,3);
		addFixRecipe(MIDDLE,rp2AxeRuby,3);
		addFixRecipe(MIDDLE,rp2AxeEmerald,3);
		addFixRecipe(MIDDLE,rp2HoeSapphire,2);
		addFixRecipe(MIDDLE,rp2HoeRuby,2);
		addFixRecipe(MIDDLE,rp2HoeEmerald,2);
		addFixRecipe(MIDDLE,rp2SwordSapphire,2);
		addFixRecipe(MIDDLE,rp2SwordRuby,2);
		addFixRecipe(MIDDLE,rp2SwordEmerald,2);
		addFixRecipe(MIDDLE,rp2HoeSapphire,2);
		addFixRecipe(MIDDLE,rp2HoeRuby,2);
		addFixRecipe(MIDDLE,rp2HoeEmerald,2);
		addFixRecipe(MIDDLE,rp2SickleSapphire,3);
		addFixRecipe(MIDDLE,rp2SickleRuby,3);
		addFixRecipe(MIDDLE,rp2SickleEmerald,3);
		addFixRecipe(HIGH,rp2SickleDiamond,3);
	}

	public static void IC2Addon()
	{
		isIC2Loaded = true;
		instance.addIC2Recipe();
	}
}
