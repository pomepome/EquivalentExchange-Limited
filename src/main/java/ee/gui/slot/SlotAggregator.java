package ee.gui.slot;

import ee.util.AggregatorRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAggregator extends Slot
{

	private boolean isOutput;

	public SlotAggregator(IInventory inv, int slotNum, int x, int y,boolean output)
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
		return AggregatorRegistry.get(stack) > 0;
	}
}
