package ee.features;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabEE extends CreativeTabs {

	public CreativeTabEE(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return EELimited.Phil.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "EELimited";
	}

}
