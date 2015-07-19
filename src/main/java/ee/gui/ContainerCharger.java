package ee.gui;

import ee.features.items.ItemKleinStar;
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
		Slot slot = this.getSlot(slotIndex);

		if (slot == null || !slot.getHasStack())
		{
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if (slotIndex > 0&&slotIndex <= 27)
		{
			//player main inventory shift-clicked
			if(stack.getItem() instanceof ItemKleinStar)
			{
				if (!this.mergeItemStack(stack, 0,1, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,28,36, false))
				{
					return null;
				}
			}
		}
		else if(slotIndex > 27)
		{
			//hotbar shift-clicked
			if(stack.getItem() instanceof ItemKleinStar)
			{
				if (!this.mergeItemStack(stack, 0,1, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,1,27, false))
				{
					return null;
				}
			}
		}
		else
		{
			if (!this.mergeItemStack(stack, 0,this.inventorySlots.size(), false))
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
