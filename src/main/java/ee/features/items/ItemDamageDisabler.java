package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EEProxy;
import ee.features.NameRegistry;

public class ItemDamageDisabler extends ItemEERing {

	public ItemDamageDisabler() {
		super(NameRegistry.DamageDisable);
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
		if(EEProxy.getFoodStats(player).needFood())
		{
			EEProxy.getFoodStats(player).addStats(20, 1);
		}
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}

}
