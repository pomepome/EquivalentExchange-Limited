package ee.features;

import net.minecraft.item.ItemShears;

public class ItemDMShears extends ItemShears {

	public ItemDMShears(int par1) {
		super(par1);
		this.setItemName(INameRegistry.DMShears).setTextureFile("/ee/splites/eqexsheet.png").setIconIndex(IconManager.get(INameRegistry.DMShears)).setMaxDamage(0);
	}

}
