package ee.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class AggregatorRegistry
{
	private static Map<Item,Double> mapItem = new HashMap<Item,Double>();
	private static Map<Block,Double> mapBlock = new HashMap<Block,Double>();

	public static void register(Item item,double value)
	{
		if(!mapItem.containsKey(item))
		{
			mapItem.put(item, value);
		}
	}
	public static void register(Block b,double value)
	{
		if(!mapBlock.containsKey(b))
		{
			mapBlock.put(b, value);
		}
	}

	public static double get(ItemStack stack)
	{
		if(stack == null)
		{
			return 0;
		}
		if(stack.getItem() instanceof ItemBlock)
		{
			ItemBlock item = (ItemBlock)stack.getItem();
			Block b = item.field_150939_a;
			if(mapBlock.containsKey(b))
			{
				return mapBlock.get(b);
			}
		}
		else
		{
			Item item = stack.getItem();
			if(mapItem.containsKey(item))
			{
				return mapItem.get(item);
			}
		}
		return 0;
	}
}
