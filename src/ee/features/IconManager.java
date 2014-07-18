package ee.features;

import java.util.HashMap;
import java.util.Map;

public class IconManager {

	private static Map<String,Integer> table = new HashMap<String,Integer>();

	public static void init()
	{
		add(INameRegistry.Phil,0);
		add(INameRegistry.Mercurius,1);
		add(INameRegistry.ironband,2);
		add(INameRegistry.bandDyson+"Off",3);
		add(INameRegistry.bandDyson+"On",4);
		add(INameRegistry.arcEngel,5);
		add(INameRegistry.ringHarvest+"Off",6);
		add(INameRegistry.ringHarvest+"On",7);
		add(INameRegistry.ringFire+"Off",8);
		add(INameRegistry.ringFire+"On",9);
		add(INameRegistry.ringWolf+"Off",10);
		add(INameRegistry.ringWolf+"On",11);
		add(INameRegistry.Evertide,16);
		add(INameRegistry.Volcanite,17);
		add(INameRegistry.Darkmatter,23);
		add(INameRegistry.DMPickaxe,24);
		add(INameRegistry.DMSword,26);
		add(INameRegistry.AlchemicCoal,36);
		add(INameRegistry.MobiusFuel,37);
		add(INameRegistry.Eternalisfuel,38);
		add(INameRegistry.covalanceLow,41);
		add(INameRegistry.diamondPowder,108);
		add(INameRegistry.DMshovel,25);
		add(INameRegistry.DMAxe,27);
		add(INameRegistry.DMHoe,28);
		add(INameRegistry.DMShears,29);
		add(INameRegistry.AlchChest+"front",0);
		add(INameRegistry.AlchChest+"side",1);
		add(INameRegistry.AlchChest+"top",2);
		add(INameRegistry.TorchEE,16);
		add(INameRegistry.DMB,13);
		add(INameRegistry.DD,92);
	}

	public static void add(String str,int i)
	{
		table.put(str, i);
	}

	public static int get(String key)
	{
		return table.get(key).intValue();
	}

}
