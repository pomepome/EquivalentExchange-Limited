package ee.features;

import static ee.features.Level.*;
import ic2.api.IElectricItem;
import ic2.api.Ic2Recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = EELimited.modid,version = "1a")

public class EELimited {

	public static final String modid = "EELimited";
	public static boolean debug = false;
	public static final CreativeTabs TabEE = new CreativeTabEE("EELimited");

	public static int IDPhil;
	public static int IDDP;
	public static int IDTorch;
	public static int IDVolc;
	public static int IDAlchCoal;
	public static int IDCov;

	public static int GUI_PORT;

	public static Item Phil;
	public static Item DP;
	public static Item Volc;
	public static Item AlchCoal;
	public static Item covalanceDust;

	public static Block EETorch;

	public static Object instance;

	@Mod.Init
	public void Init(FMLInitializationEvent event)
	{
		GuiIDs.PORT_CRAFT = 256;
		IconManager.init();
		Block.commandBlock.setCreativeTab(CreativeTabs.tabRedstone);
		loadConfig();
		NetworkRegistry.instance().registerGuiHandler(this,new GuiHandler());
		instance = this;
		Phil = new ItemPhilosopherStone(IDPhil);
		DP = new ItemNonFunction(IDDP,INameRegistry.diamondPowder);
		AlchCoal = new ItemNonFunction(IDAlchCoal,INameRegistry.AlchemicCoal);
		EETorch = new BlockEETorch(IDTorch);
		covalanceDust = new ItemCov(IDCov,INameRegistry.covalanceLow);
		Localization(Phil,"Philosopher's Stone","賢者の石");
		Localization(DP,"Diamond Powder","ダイヤモンドの粉末");
		Localization(EETorch,"Interdiction Torch","結界灯火");
		Localization(getCovalence(LOW),"Covalence Dust(Low)","共有結合粉(低)");
		Localization(getCovalence(MIDDLE),"Covalence Dust(Mid)","共有結合粉(中)");
		Localization(getCovalence(HIGH),"Covalence Dust(High)","共有結合粉(高)");
		Localization(AlchCoal,"Alchemical Coal","錬金炭");

		addFixRecipe();

		// * Recipes(Don't require Philosopher's Stone)
		addRecipe(gs(Item.slimeBall),"SRP"," W ",'S',Item.seeds,'R',Item.reed,'P',gs(Block.sapling,1,-1),'W',Item.bucketWater);
		addRecipe(gs(Item.bucketWater),"S","B",'S',Item.snowball,'B',Item.bucketEmpty);
		addRecipe(gs(Item.bucketLava),"GRG"," B ",'G',Item.gunpowder,'R',Item.redstone,'B',Item.bucketEmpty);
		addRecipe(gs(Item.bucketMilk),"B","W","E",'B',gs(Item.dyePowder,1,0xF),'W',Item.bucketWater,'E',Item.bucketEmpty);
		addRecipe(gs(Phil),"RGR","GSG","RGR",'R',Item.redstone,'G',Item.lightStoneDust,'S',Item.slimeBall);
		addRecipe(gs(Phil),"CPC","PEP","CPC",'C',gs(Item.coal.itemID),'P',Item.enderPearl,'E',Item.egg);
		addSRecipe(gs(Item.lightStoneDust,4),gs(Item.coal.itemID),gs(Item.redstone));
		addSRecipe(gs(Item.redstone,4),gs(Item.coal.itemID),gs(Block.cobblestone));
		addSRecipe(changeAmount(getCovalence(LOW),10),gs(Block.cobblestone),gs(Block.cobblestone),gs(Item.lightStoneDust));
		addSRecipe(changeAmount(getCovalence(LOW),10),gs(Block.cobblestone),gs(Block.cobblestone),gs(Item.coal.itemID),gs(Item.coal.itemID));
		addSRecipe(changeAmount(getCovalence(MIDDLE),10),gs(Item.ingotIron),gs(Item.ingotIron),gs(Item.redstone));
		addSRecipe(changeAmount(getCovalence(HIGH),10),gs(Item.diamond),gs(Item.diamond),gs(Block.glowStone));
		addSRecipe(changeAmount(getCovalence(HIGH),10),gs(Item.diamond),gs(Item.diamond),gs(AlchCoal),gs(AlchCoal));


	}

	public ItemStack getCovalence(Level lev)
	{
		switch(lev)
		{
			case HIGH   :return gs(covalanceDust);
			case MIDDLE :return gs(covalanceDust,1,1);
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
		list.add(gs(Phil));
		for(int i = 0;i < amount;i++)
		{
			list.add(gs(obj));
		}
		addSRecipe(dest,list.toArray());
	}
	public void addExchange(ItemStack dest,Object... objs)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(gs(Phil));
		for(int i = 0;i < objs.length;i++)
		{
			list.add(gs(objs[i]));
		}
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
		TickRegistry.registerTickHandler(new TickHandler(),Side.CLIENT);
		MinecraftForgeClient.preloadTexture("/ee/splites/eqexsheet.png");
	}

	public void loadConfig()
	{
		Configuration config = new Configuration(new File(Minecraft.getMinecraftDir(),"config/EELimited.cfg"));
		config.load();
		debug = config.get(Configuration.CATEGORY_GENERAL, "Debug Mod", false).getBoolean(false);
		GuiIDs.ALCH_BAG = config.get(Configuration.CATEGORY_GENERAL, "Alchemy bag GUI ID", 12).getInt(12);
		GUI_PORT = config.get(Configuration.CATEGORY_GENERAL, "Portable Crafting GUI ID", 197).getInt(197);
		IDPhil=config.get(config.CATEGORY_ITEM,"Philosopher's stone ID",146).getInt();
		IDDP=config.get(config.CATEGORY_ITEM,"Diamond powder ID",147).getInt();
		IDCov=config.get(config.CATEGORY_ITEM,"Covalance Dust ID",149).getInt();
		IDTorch=config.get(config.CATEGORY_BLOCK,"Interdiction Torch ID",148).getInt();
		IDAlchCoal=config.get(config.CATEGORY_ITEM,"Alchemical Coal ID",150).getInt();
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

	public static void addIC2Recipe()
	{
		Ic2Recipes.addMaceratorRecipe(gs(Item.diamond),gs(DP));
		Ic2Recipes.addMaceratorRecipe(gs(Block.oreDiamond),gs(DP,2));
		Item[] items = Item.itemsList;
		for(int i =0;i < items.length;i++)
		{
			if(items[i]!=null&&!(items[i] instanceof IElectricItem)&&!items[i].getHasSubtypes()&&items[i].getMaxDamage() > 0)
			{
				addSRecipe(gs(items[i]),gs(DP),gs(items[i],1,-1));
			}
		}
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
		addRecipe(new ShapelessOreRecipe(gs(getOre(INameRegistry.WaferSilicon).itemID,16,getOre(INameRegistry.WaferSilicon).getItemDamage()),gs(Phil),INameRegistry.BouleSilicon));
	}

	public static void IC2Addon()
	{
		addIC2Recipe();
	}
}
