package ee.features;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDamageDisabler extends ItemFunction {

	public ItemDamageDisabler(int id) {
		super(id,INameRegistry.DD);
		this.setHasSubtypes(true);
	}

	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1,1,0));
		par3List.add(new ItemStack(par1,1,1));
    }

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
		if(player.getFoodStats().needFood())
		{
			player.getFoodStats().addStats((ItemFood)EELimited.Food);
		}
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}

	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,
			int z, int blockId) {
	}

}
