package ee.features.items;

import ee.features.NameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwiftwolfsRing extends ItemRing
{
    public ItemSwiftwolfsRing()
    {
        super(NameRegistry.Swift);
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is)
    {
        if (player.fallDistance > 0)
        {
            player.fallDistance = 0;
        }
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is, int d)
    {
    }
}