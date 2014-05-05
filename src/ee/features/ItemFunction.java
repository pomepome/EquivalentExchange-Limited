package ee.features;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemFunction extends ItemEE {

	public ItemFunction(int id, String name) {
		super(id, name);
		this.setMaxStackSize(1).setContainerItem(this);
	}

	@Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

	@Override
	public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if(!par1ItemStack.getHasSubtypes()|par1ItemStack.getItemDamage() == 1)
		{
			doPassive(par2World,(EntityPlayer)par2World.playerEntities.get(0),par1ItemStack);
		}
	}

	public abstract void doPassive(World world,EntityPlayer player,ItemStack is);

}
