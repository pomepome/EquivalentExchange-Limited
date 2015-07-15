package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EEProxy;
import ee.features.NameRegistry;
import ee.features.entity.EntityHomingArrow;

public class ItemArchangelSmite extends ItemEEFunctional {

	public ItemArchangelSmite() {
		super(NameRegistry.ArchAngel);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		EntityHomingArrow arrow = new EntityHomingArrow(world, player, 2.0F);
		if (!world.isRemote)
		{
			boolean flag = false;
			if(player.inventory.hasItem(Items.arrow))
			{
				arrow.setPickable(true);
				player.inventory.consumeInventoryItem(Items.arrow);
				flag = true;
			}
			else if(EEProxy.getBlockCount(player.inventory,Blocks.cobblestone) >= 14)
			{
				EEProxy.decrItem(player.inventory,Item.getItemFromBlock(Blocks.cobblestone),14);
				EntityHomingArrow arrow2 = new EntityHomingArrow(world, player, 2.0F);
				world.spawnEntityInWorld(arrow2);
				flag = true;
			}
			else if(EEProxy.getBlockCount(player.inventory,Blocks.stone) >= 14)
			{
				EEProxy.decrItem(player.inventory,Item.getItemFromBlock(Blocks.stone),14);
				EntityHomingArrow arrow2 = new EntityHomingArrow(world, player, 2.0F);
				world.spawnEntityInWorld(arrow2);
				flag = true;
			}
			else if(EEProxy.useResource(player,14,true))
			{
				EntityHomingArrow arrow2 = new EntityHomingArrow(world, player, 2.0F);
				world.spawnEntityInWorld(arrow2);
				flag = true;
			}
			if(flag)
			{
				world.spawnEntityInWorld(arrow);
			}
			player.inventory.markDirty();
			player.inventoryContainer.detectAndSendChanges();
		}

		return stack;
	}

}
