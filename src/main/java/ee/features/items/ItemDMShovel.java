package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import ee.features.NameRegistry;

public class ItemDMShovel extends ItemEE
{
    public ItemDMShovel()
    {
        super(NameRegistry.DMShovel, 9);
    }

    @Override
    public float getDigSpeed(ItemStack stack,IBlockState state)
    {
    	Block block = state.getBlock();
        return (block.getMaterial() == Material.ground || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.sand) ? 10 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
    }
}

