package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IProjectileShooter
{
	public boolean shootProcectile(EntityPlayer player,ItemStack is);
}