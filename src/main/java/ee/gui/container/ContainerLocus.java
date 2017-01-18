package ee.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.tiles.TileEntityLocus;
import ee.gui.slot.SlotAlchemicalFuel;
import ee.gui.slot.SlotDest;
import ee.gui.slot.SlotSlided;
import ee.util.EEProxy;
import ee.util.LocusRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLocus extends Container
{
	TileEntityLocus tile;

	private int lastProc,lastBurn;

	public ContainerLocus(TileEntityLocus tile,InventoryPlayer p)
	{
		this.tile = tile;
		for(int i = 0;i < 4;i++)
		{
			for(int j = 0;j < 2;j++)
			{
				this.addSlotToContainer(new SlotAlchemicalFuel(tile, i * 2 + j,20 + 18 * j,8 + i * 18));
			}
		}
		this.addSlotToContainer(new SlotAlchemicalFuel(tile, 8, 56, 53));
		this.addSlotToContainer(new SlotDest(tile,9,56,17,false));
		this.addSlotToContainer(new SlotDest(tile,10,116,35,true));

		//Player Inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new SlotSlided(p, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));
			}
		}
		//Player Hotbar
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new SlotSlided(p, i, 48 + i * 18, 212));
		}
	}
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
    {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, tile.procTime);
		crafting.sendProgressBarUpdate(this, 1, tile.currentBurnTime);
    }
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastProc != this.tile.procTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.procTime);
            }
            if (this.lastBurn != tile.currentBurnTime)
            {
            	icrafting.sendProgressBarUpdate(this, 1, tile.currentBurnTime);
            }
        }

        this.lastProc = this.tile.procTime;
        this.lastBurn = this.tile.currentBurnTime;
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int value)
    {
        if (ID == 0)
        {
        	tile.procTime = value;
        }
        if (ID == 1)
        {
        	tile.currentBurnTime = value;
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

		if (slotIndex > 10&&slotIndex <= 37)
		{
			//Player main inventory shift-clicked
			if(LocusRegistry.isDestValid(stack) > 0)
			{
				if (!this.mergeItemStack(stack, 9,9, false))
				{
					return null;
				}
			}
			else if(LocusRegistry.getFuelValue(stack) > 0)
			{
				if (!this.mergeItemStack(stack, 0,8, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,38,46, false))
				{
					return null;
				}
			}
		}
		else if(slotIndex > 37)
		{
			//hotbar shift-clicked
			if(LocusRegistry.isDestValid(stack) > 0)
			{
				if (!this.mergeItemStack(stack, 9,9, false))
				{
					return null;
				}
			}
			else if(LocusRegistry.getFuelValue(stack) > 0)
			{
				if (!this.mergeItemStack(stack, 0,8, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,11,37, false))
				{
					return null;
				}
			}
		}
		else
		{
			if (!this.mergeItemStack(stack,11,this.inventorySlots.size(), false))
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
