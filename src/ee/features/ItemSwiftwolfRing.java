package ee.features;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwiftwolfRing extends ItemFunction {

	public ItemSwiftwolfRing(int id) {
		super(id,INameRegistry.ringWolf + "Off");
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCost(0.3);
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
		if(player.fallDistance > 0)
		{
			player.fallDistance = 0;
		}
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1,1,0));
		par3List.add(new ItemStack(par1,1,1));
    }
	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,int z, int blockId) {
	}


}
