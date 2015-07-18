package ee.gui;

import ee.features.tile.TileEMCCharger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCharger extends Container
{
	TileEMCCharger c;
	public ContainerCharger(InventoryPlayer pInv,TileEMCCharger tile)
	{
		c = tile;
		//Charger Inventory
		this.addSlotToContainer(new SlotCharger(tile,1,79,53));
		//Player Inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(pInv, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));
			}
		}
		//Player Hotbar
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(pInv, i, 48 + i * 18, 212));
		}
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		if (slotIndex == 0)
		{
			return null;
		}

		Slot slot = this.getSlot(slotIndex);

		if (slot == null || !slot.getHasStack())
		{
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if (slotIndex <= 36)
		{
			if (!this.mergeItemStack(stack, 0, 127, false))
			{
				return null;
			}
		}

		if (stack.stackSize == 0)
		{
			slot.putStack(null);
		}

		else slot.onSlotChanged();
		slot.onPickupFromSlot(player, stack);
		return newStack;
	}
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return true;
	}
	public void detectAndSendChanges()
    {
		super.detectAndSendChanges();
    }

}
