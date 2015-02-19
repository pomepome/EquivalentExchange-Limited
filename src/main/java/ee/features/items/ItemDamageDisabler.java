package ee.features.items;

import ee.features.NameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDamageDisabler extends ItemRing
{
    public ItemDamageDisabler()
    {
        super(NameRegistry.DamageDisable);
        setMaxStackSize(1);
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is)
    {
        if (player.getFoodStats().needFood())
        {
            player.getFoodStats().addStats(20,1.0F);
        }
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is, int d)
    {
    }
}

