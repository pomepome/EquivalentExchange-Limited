package ee.features;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDamageDisabler extends ItemRing
{
    public ItemDamageDisabler(int par1)
    {
        super(par1, NameRegistry.DamageDisable);
        setMaxStackSize(1);
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is)
    {
        if (player.getFoodStats().needFood())
        {
            player.getFoodStats().addStats((ItemFood)EELimited.Food);
        }
    }

    @Override
    public void doPassive(World world, EntityPlayer player, ItemStack is, int d)
    {
    }
}
