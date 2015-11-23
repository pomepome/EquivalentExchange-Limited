package ee.gui.slot;

import ee.features.items.ItemAlchemyBag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAlchemyBagOnly extends Slot
{
	public SlotAlchemyBagOnly(IInventory inv, int slot, int x, int y)
	{
		super(inv, slot, x, y);
	}
	public boolean isItemValid(ItemStack toPut)
    {
		return toPut.getItem() instanceof ItemAlchemyBag;
    }
}
