package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IChargeable {
	void changeCharge(EntityPlayer player, ItemStack stack);
	int getChargeLevel(ItemStack is);
}
