package ee.features.items;

import ee.util.EEProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class ItemBodyStone extends ItemRing {

	public ItemBodyStone() {
		super("bodyStone");
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is)
	{
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d)
	{
		ItemFood f;
		FoodStats food = player.getFoodStats();
		if(food.needFood())
		{
			if(EEProxy.useResource(player, 4, true))
			{
				food.addStats(1, 1.0f);
			}
			else
			{
				is.setItemDamage(0);
			}
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float minX, float minY, float minZ)
    {
		onItemRightClick(stack, world, player);
		return super.onItemUse(stack, player, world, x, y, z, side, minX, minY, minZ);
    }
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		FoodStats food = player.getFoodStats();
		if(food.needFood())
		{
			food.addStats(1, 1.0f);
		}
		EEProxy.playSoundAtPlayer("random.burp", player, 0.5f, 1.0f);
		return stack;
    }
}
