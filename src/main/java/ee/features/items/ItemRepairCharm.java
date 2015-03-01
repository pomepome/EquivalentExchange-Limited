package ee.features.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.Timer1s;

public class ItemRepairCharm extends ItemEEFunctional
{
	public ItemRepairCharm() {
		super(NameRegistry.Repair);
    }
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!world.isRemote&&entity instanceof EntityPlayer&&Timer1s.isTime())
		{
			EntityPlayer p = (EntityPlayer)entity;
			IInventory inv = p.inventory;
			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				ItemStack is = inv.getStackInSlot(i);
				if(is != null&&EELimited.isRepairable(is.getItem())&&is.getItemDamage() > 0)
				{
					is.setItemDamage(is.getItemDamage() - 1);
				}
				inv.setInventorySlotContents(i, is);
			}
		}
	}
}
