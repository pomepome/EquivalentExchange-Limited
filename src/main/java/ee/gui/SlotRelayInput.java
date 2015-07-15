package ee.gui;

import ee.features.EELimited;
import ee.features.EEProxy;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRelayInput extends Slot
{
	public SlotRelayInput(IInventory inventory, int slotIndex, int xPos, int yPos) 
	{
		super(inventory, slotIndex, xPos, yPos);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}
		
		return stack.getItem() == EELimited.Klein || EEProxy.getEMC(stack) > 0;
	}
}