package ee.features.items.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IModeChange
{
	public void onActivated(EntityPlayer player,ItemStack is);
}
