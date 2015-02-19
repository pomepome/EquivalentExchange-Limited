package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EELimited;
import ee.features.NameRegistry;

public class ItemPhilosophersStone extends ItemEEFunctional {
	public ItemPhilosophersStone()
    {
        super(NameRegistry.Philo);
    }
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(!var3.isSneaking())
		{
			return var1;
		}
		return new ItemStack(EELimited.PhilTool);
    }
}
