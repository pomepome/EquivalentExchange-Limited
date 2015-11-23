package ee.util;

import net.minecraft.item.ItemStack;

public class ItemInfo
{
	ItemStack stack;
	int amount;
	public ItemInfo(ItemStack base,int amo)
	{
		stack = base;
		amount = amo;
	}
}
