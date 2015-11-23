package ee.util;

import java.util.Comparator;

import net.minecraft.item.Item;

public class ComparatorItemInfo implements Comparator<ItemInfo> {

	@Override
	public int compare(ItemInfo o1, ItemInfo o2)
	{
		Item i1 = o1.stack.getItem();
		Item i2 = o2.stack.getItem();
		int id1 = Item.getIdFromItem(i1);
		int id2 = Item.getIdFromItem(i2);
		if(id1 == id2)
		{
			return 0;
		}
		return id1 > id2 ? 1 : -1;
	}

}
