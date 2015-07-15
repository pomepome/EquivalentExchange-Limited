package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;

public class ItemEE extends Item {
	int toolDamage = 0;

    public ItemEE(String name)
    {
        super();
        this.setUnlocalizedName(name).setTextureName("ee:" + name).setCreativeTab(EELimited.TabEE);
        GameRegistry.registerItem(this, name);
    }

    public ItemEE(String name, int damage)
    {
        super();
        this.setFull3D().setUnlocalizedName(name).setTextureName("ee:" + name).setMaxDamage(200).setCreativeTab(EELimited.TabEE).setMaxStackSize(1);
        GameRegistry.registerItem(this, name);
        toolDamage = damage;
    }

    public boolean isWood(Block b)
    {

        if (b == null)
        {
            return false;
        }

        String name = b.getUnlocalizedName();
        return name.toLowerCase().contains("wood") || name.toLowerCase().contains("log");
    }
    @SuppressWarnings("unchecked")
    public Multimap getItemAttributeModifiers()
    {
        if (toolDamage == 0)
        {
            return super.getItemAttributeModifiers();
        }

        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.toolDamage, 0));
        return multimap;
    }
}
