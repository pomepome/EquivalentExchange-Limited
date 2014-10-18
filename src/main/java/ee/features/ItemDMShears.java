package ee.features;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemShears;

public class ItemDMShears extends ItemShears {
	public ItemDMShears()
	{
		super();
		String name = NameRegistry.DMShears;
		GameRegistry.registerItem(this,name);
		this.setTextureName("ee:"+name).setUnlocalizedName(name).setCreativeTab(EELimited.TabEE).setMaxDamage(0);
	}
}
