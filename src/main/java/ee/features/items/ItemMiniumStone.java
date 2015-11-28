package ee.features.items;

import ee.network.PacketHandler;
import ee.network.PacketSound;
import net.minecraft.item.ItemStack;

public class ItemMiniumStone extends ItemEEFunctional
{
	public ItemMiniumStone()
	{
		super("MiniumStone");
		this.setMaxDamage(20);
	}
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copiedStack = itemStack.copy();
        
        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }

}
