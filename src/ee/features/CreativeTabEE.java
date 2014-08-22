package ee.features;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabEE extends CreativeTabs
{
    public CreativeTabEE(String label)
    {
        super(label);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return EELimited.Phil;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return "EELimited";
    }
}
