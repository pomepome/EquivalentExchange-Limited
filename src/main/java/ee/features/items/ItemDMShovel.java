package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import ee.features.NameRegistry;

public class ItemDMShovel extends ItemEETool
{
    public ItemDMShovel()
    {
        super(NameRegistry.DMShovel, 9);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block,int meta)
    {
        return (block.getMaterial() == Material.ground || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.sand) ? 10 * (int)((getChargeLevel(stack) + 1) * 1.5) : 2.5F;
    }
}
