package ee.gui.slot;

import ee.util.LocusRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDest extends Slot
{
	boolean isOutput;
	public SlotDest(IInventory inv, int slotNum, int x, int y,boolean output)
	{
		super(inv, slotNum, x, y);
		isOutput = output;
	}
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if(isOutput)
		{
			return false;
		}
		return LocusRegistry.isDestValid(stack) > 0;
	}
}
