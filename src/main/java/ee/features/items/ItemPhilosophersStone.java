package ee.features.items;

import ee.features.EELimited;
import ee.features.NameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPhilosophersStone extends ItemEEFunctional {
	public ItemPhilosophersStone()
    {
        super(NameRegistry.Philo);
    }
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		return new ItemStack(EELimited.PhilTool);
    }
}
