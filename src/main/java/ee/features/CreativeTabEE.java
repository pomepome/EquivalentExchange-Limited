package ee.features;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabEE extends CreativeTabs {

	public CreativeTabEE() {
		super("EELimited");
	}

	@Override
	public Item getTabIconItem() {
		return EELimited.Phil;
	}

}
