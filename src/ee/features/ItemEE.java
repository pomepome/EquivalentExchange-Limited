package ee.features;

import net.minecraft.item.Item;

public class ItemEE extends Item {

	public ItemEE(int id,String name) {
		super(id - 256);
		this.setIconIndex(IconManager.get(name)).setItemName(name).setTextureFile("/ee/splites/eqexsheet.png").setCreativeTab(EELimited.TabEE);
	}

}
