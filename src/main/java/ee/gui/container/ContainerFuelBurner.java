package ee.gui.container;

import ee.features.EELimited;
import ee.features.tiles.TileFuelBurner;
import ee.gui.slot.SlotBurner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFuelBurner extends Container
{
	TileFuelBurner fb;
	public ContainerFuelBurner(InventoryPlayer pInv,TileFuelBurner tile)
	{
		fb = tile;
		//Charger Inventory
		this.addSlotToContainer(new SlotBurner(tile,1,79,53));
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
			if(EELimited.instance.getFuelValue(stack) > 0)
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
			if(EELimited.instance.getFuelValue(stack) > 0)
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
			//Charger inventory shift-clicked
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
}
