package ee.features;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	static Map<Integer,Integer> map = new HashMap<Integer,Integer>();
	public static void register(int id,int burnTime)
	{
		if(!map.containsKey(id))
		{
			map.put(id,burnTime);
		}
	}
	@Override
	public int getBurnTime(ItemStack fuel) {
		if(map.containsKey(fuel.itemID))
		{
			return map.get(fuel.itemID);
		}
		return 0;
	}

}
