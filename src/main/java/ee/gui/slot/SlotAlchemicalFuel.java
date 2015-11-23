package ee.gui.slot;

import ee.util.LocusRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAlchemicalFuel extends Slot
{
	public SlotAlchemicalFuel(IInventory inv, int slotNum, int x, int y)
	{
		super(inv, slotNum, x, y);
	}
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return LocusRegistry.getFuelValue(stack) > 0;
	}
}
