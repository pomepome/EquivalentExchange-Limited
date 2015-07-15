package ee.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;

public class SlotAlchemyBag extends Slot {

	public SlotAlchemyBag(IInventory inv, int id,int x, int y) {
		super(inv,id,x,y);
	}

	public boolean isItemValid(ItemStack toPut)
    {
		return EELimited.cantPutAlchemyBag ? !(toPut.getItem() instanceof ItemAlchemyBag) : true;
    }

}
