package ee.features.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ee.features.NameRegistry;
import ee.features.PlayerTimers;
import ee.util.EEProxy;

public class ItemRepairCharm extends ItemEEFunctional
{
	public ItemRepairCharm() {
		super(NameRegistry.Repair);
		this.setTextureName("ee:0");
    }
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if (!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
		}

		if (world.isRemote || !(entity instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) entity;

		PlayerTimers.activateRepair(player);

		if (PlayerTimers.canRepair(player))
		{
			repairAllItems(player);
		}
	}

	public void repairAllItems(EntityPlayer player)
	{
		IInventory inv = player.inventory;

		for (int i = 0; i < 40; i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null || invStack.getItem() instanceof ItemEE || !invStack.getItem().isRepairable())
			{
				continue;
			}

			if (invStack.equals(player.getCurrentEquippedItem()) && player.isSwingInProgress) {
				//Don't repair item that is currently used by the player.
				continue;
			}

			if (!invStack.getHasSubtypes() && invStack.getMaxDamage() != 0 && invStack.getItemDamage() > 0 && EEProxy.useResource(player,1,true))
			{
				invStack.setItemDamage(invStack.getItemDamage() - 1);
				inv.markDirty();
				continue;
			}
			if (invStack.hasTagCompound())
			{
				NBTTagCompound tag = invStack.getTagCompound();
				if(tag.hasKey("Damage"))
				{
					int current = tag.getInteger("Damage");
					tag.setInteger("Damage",Math.max(current - 1,0));
					inv.markDirty();
					continue;
				}
			}
		}
	}
}
