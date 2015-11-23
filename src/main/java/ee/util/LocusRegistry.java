package ee.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class LocusRegistry
{
	private static Map<ItemStack,Integer> destMap = new HashMap<ItemStack,Integer>();
	private static Map<ItemStack,Integer> fuelMap = new HashMap<ItemStack,Integer>();

	public static void registerDest(ItemStack stack,int objective)
	{
		if(stack == null)
		{
			return;
		}
		if(isDestValid(stack) == 0)
		{
			destMap.put(stack,objective);
		}
	}
	public static int isDestValid(ItemStack stack)
	{
		for(Map.Entry<ItemStack, Integer> entry: destMap.entrySet())
		{
			if(EEProxy.areItemStacksEqual(stack, entry.getKey()))
			{
				return entry.getValue();
			}
		}
		return 0;
	}
	public static int getFuelValue(ItemStack stack)
	{
		for(Map.Entry<ItemStack, Integer> entry : fuelMap.entrySet())
		{
			ItemStack fuel = entry.getKey();
			if(EEProxy.areItemStacksEqual(stack, fuel))
			{
				return fuelMap.get(fuel);
			}
		}
		return 0;
	}
	public static void registerFuelValue(ItemStack stack,int value)
	{
		if(getFuelValue(stack) == 0)
		{
			fuelMap.put(stack, value);
		}
	}
}