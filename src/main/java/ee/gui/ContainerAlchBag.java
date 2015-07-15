package ee.gui;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;

@ChestContainer(isLargeChest = true,rowSize = 13)
public class ContainerAlchBag extends Container
{
	IInventory BagInv;
	int metaBag;
	public ContainerAlchBag(InventoryPlayer invPlayer,IInventory inv,int meta)
	{
		BagInv = inv;
		metaBag = meta;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				this.addSlotToContainer(new SlotAlchemyBag(inv, j + i * 13, 12 + j * 18, 5 + i * 18));
			}
		}
		//Player Inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 152 + i * 18));
			}
		}

		//Player Hotbar
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 210));
		}
	}
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	 {
		 ItemStack itemstack = null;
		 Slot slot = (Slot)this.inventorySlots.get(par2);

		 if (slot != null && slot.getHasStack())
		 {
			 ItemStack itemstack1 = slot.getStack();
			 itemstack = itemstack1.copy();

			 if (par2 < BagInv.getSizeInventory())
			 {
				 if (!this.mergeItemStack(itemstack1, this.BagInv.getSizeInventory(), this.inventorySlots.size(), true))
				 {
					 return null;
				 }
			 }
			 else if(EELimited.cantPutAlchemyBag && itemstack1.getItem() instanceof ItemAlchemyBag)
			 {
				 return null;
			 }
			 else if (!this.mergeItemStack(itemstack1, 0, this.BagInv.getSizeInventory(), false))
			 {
				 return null;
			 }
			 if (itemstack1.stackSize == 0)
			 {
				 slot.putStack(null);
			 }
			 else
			 {
				 slot.onSlotChanged();
			 }
		 }

		 return itemstack;
	 }
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
