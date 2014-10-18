package ee.features;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
    static Map<Item, Integer> map = new HashMap<Item, Integer>();

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        return get(fuel);
    }

    public static boolean register(Object obj, int value)
    {
        if (map.containsKey(EELimited.gs(obj).getItem()))
        {
            return false;
        }

        map.put(EELimited.gs(obj).getItem(), value);
        return true;
    }

    public int get(ItemStack is)
    {
        if (map.containsKey(is.getItem()))
        {
            return map.get(is.getItem());
        }

        return 0;
    }
}
