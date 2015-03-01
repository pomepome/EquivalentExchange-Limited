package ee.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EEAPI {
	public static ItemStack getItem(String name) throws EELimitedException
	{
		Object obj;
		try
		{
			obj = Class.forName("ee.features.EELimited").getField(name).get(null);
		}
		catch(Exception ex)
		{
			throw new EELimitedException("Requested item doesn't exists:"+name);
		}
		if(obj instanceof Item)
		{
			return new ItemStack((Item)obj);
		}
		if(obj instanceof Block)
		{
			return new ItemStack((Block)obj);
		}
		if(obj instanceof ItemStack)
		{
			return (ItemStack)obj;
		}
		throw new EELimitedException("Requested object is not a Item or Block:"+name);
	}
}
