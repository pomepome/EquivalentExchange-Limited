package ee.features;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
    static Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        return get(fuel);
    }

    public static boolean register(int itemID, int value)
    {
        if (map.containsKey(itemID))
        {
            return false;
        }

        map.put(itemID, value);
        return true;
    }

    public int get(ItemStack is)
    {
        if (map.containsKey(is.itemID))
        {
            return map.get(is.itemID);
        }

        return 0;
    }
}
