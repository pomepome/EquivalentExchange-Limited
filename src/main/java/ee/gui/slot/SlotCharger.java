package ee.gui.slot;

import ee.features.items.ItemKleinStar;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCharger extends Slot {

	public SlotCharger(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_)
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_ + 41, p_i1824_4_+ 15);
	}
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if(stack != null)
		{
			if(stack.getItem() instanceof ItemKleinStar)
			{
				return true;
			}
		}
		return false;
	}
}
