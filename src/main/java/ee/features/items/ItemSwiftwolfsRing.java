package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.NameRegistry;

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
    protected boolean useResource()
    {
    	return true;
    }
}