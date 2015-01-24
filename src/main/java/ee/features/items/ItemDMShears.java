package ee.features.items;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;
import ee.features.NameRegistry;
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
