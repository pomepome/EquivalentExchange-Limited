package ee.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.tiles.TileEntityAggregator;
import ee.gui.slot.SlotAggregator;
import ee.gui.slot.SlotSlided;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAggregator extends Container
{
	TileEntityAggregator tile;

	double lastProgress;

	public ContainerAggregator(TileEntityAggregator agg, EntityPlayer p)
	{
		tile = agg;
		//Charger Inventory
		this.addSlotToContainer(new SlotAggregator(agg,0,20,8,false));
		this.addSlotToContainer(new SlotAggregator(agg,1,38,8,false));
		this.addSlotToContainer(new SlotAggregator(agg,2,20,26,false));
		this.addSlotToContainer(new SlotAggregator(agg,3,38,26,false));
		this.addSlotToContainer(new SlotAggregator(agg,4,56,17,false));
		this.addSlotToContainer(new SlotAggregator(agg,5,116,35,true));
		this.addSlotToContainer(new SlotAggregator(agg,6,138,26,true));
		this.addSlotToContainer(new SlotAggregator(agg,7,156,26,true));
		this.addSlotToContainer(new SlotAggregator(agg,8,138,44,true));
		this.addSlotToContainer(new SlotAggregator(agg,9,156,44,true));
		//Player Inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new SlotSlided(p.inventory, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));
			}
		}
		//Player Hotbar
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new SlotSlided(p.inventory, i, 48 + i * 18, 212));
		}
	}
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
    {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, (int)(tile.progress * 10000));
    }
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastProgress != this.tile.progress)
            {
                icrafting.sendProgressBarUpdate(this, 0, (int)(this.tile.progress * 10000));
            }
        }

        this.lastProgress = this.tile.progress;
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int value)
    {
        if (ID == 0)
        {
            this.tile.progress = (double)value / 10000;
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

		if (slotIndex > 9&&slotIndex <= 36)
		{
			//Player main inventory shift-clicked
			if(tile.isItemValidForSlot(slotIndex, stack))
			{
				if (!this.mergeItemStack(stack, 0,4, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,37,45, false))
				{
					return null;
				}
			}
		}
		else if(slotIndex > 36)
		{
			//hotbar shift-clicked
			if(tile.isItemValidForSlot(slotIndex, stack))
			{
				if (!this.mergeItemStack(stack, 0,4, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,10,36, false))
				{
					return null;
				}
			}
		}
		else
		{
			if (!this.mergeItemStack(stack,10,this.inventorySlots.size(), false))
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
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
