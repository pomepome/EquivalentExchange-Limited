package ee.features.items.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IExtraFunction
{
	void onExtraFunction(EntityPlayer p,ItemStack is);
}
