package ee.gui.container;

import ee.features.items.ItemAlchemyBag;
import ee.features.tiles.TileEntityColoredAlchChest;
import ee.gui.slot.SlotAlchemyBagOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerColoredAlchChest extends Container
{
	private TileEntityColoredAlchChest tile;
	public ContainerColoredAlchChest(InventoryPlayer invPlayer,TileEntityColoredAlchChest tile)
	{
		this.tile = tile;
		tile.openInventory();
		//Chest Inventory
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				this.addSlotToContainer(new Slot(tile, j + i * 13, 12 + j * 18, 5 + i * 18));
			}
		}
		this.addSlotToContainer(new SlotAlchemyBagOnly(tile, 104, 22, 170));
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
		if (slotIndex < 104)
		{
			if (!this.mergeItemStack(stack, 132, this.inventorySlots.size(), false))
			{
				if(!this.mergeItemStack(stack, 105, 131, false))
				{
					return null;
				}
			}
		}
		else if(slotIndex == 104)
		{
			return null;
		}
		else if (stack.getItem() instanceof ItemAlchemyBag)
		{
			tile.updateEntity();
			if(!this.mergeItemStack(stack, 0, 103, false))
			{
				return null;
			}
		}
		else if (!this.mergeItemStack(stack, 0, 103, false))
		{
			return null;
		}

		if (stack.stackSize == 0)
		{
			slot.putStack((ItemStack)null);
		}
		else
		{
			slot.onSlotChanged();
		}

		return newStack;
	}
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		tile.closeInventory();
	}
}
