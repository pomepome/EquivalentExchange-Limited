package ee.features.items;

import ee.features.NameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMShovel extends ItemEE
{
    public ItemDMShovel()
    {
        super(NameRegistry.DMShovel, 9);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block,int meta)
    {
        return (block.getMaterial() == Material.ground || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.sand) ? 10 * (int)((stack.getItemDamage() + 1) * 1.5) : 2.5F;
    }
}
