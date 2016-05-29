package ee.features;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabEE extends CreativeTabs {

	public CreativeTabEE() {
		super("EELimited");
	}

	@Override
	public Item getTabIconItem() {
		if(EEItems.Phil == null)
		{
			return EEItems.miniumStone;
		}
		return EEItems.Phil;
	}

}
