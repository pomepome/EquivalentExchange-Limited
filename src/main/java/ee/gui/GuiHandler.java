package ee.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;
import ee.features.tile.TileEntityAlchChest;

public class GuiHandler implements IGuiHandler {

	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z)
	{
		if(ID == EELimited.ALCH_BAG_ID)
		{
			ItemStack is = player.getCurrentEquippedItem();
			if(is != null && is.getItem() instanceof ItemAlchemyBag)
			{
				InventoryAlchBag inventory = new InventoryAlchBag(is,world);
				return new ContainerAlchBag(player.inventory,inventory,is.getItemDamage());
			}
		}
		if(ID == EELimited.CRAFT)
		{
			return new ContainerPhilWorkbench(player.inventory);
		}
		if(ID == EELimited.ALCH_CHEST)
		{
			TileEntity chest = world.getTileEntity(x, y, z);
			if(chest instanceof TileEntityAlchChest)
			{
				return new ContainerAlchChest(player.inventory,(TileEntityAlchChest)chest);
			}
		}
		if(ID == EELimited.AGGREGATOR)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAggregator)
			{
				return new ContainerAggregator(player.inventory,(TileEntityAggregator)tile);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z)
	{
		if(ID == EELimited.ALCH_BAG_ID)
		{
			ItemStack is = player.getCurrentEquippedItem();
			if(is != null && is.getItem() instanceof ItemAlchemyBag)
			{
				InventoryAlchBag inventory = new InventoryAlchBag(is,world);
				return new GuiAlchChest(player.inventory,inventory,is.getItemDamage());
			}
		}
		if(ID == EELimited.CRAFT)
		{
			return new GuiPhilWorkbench(player.inventory);
		}
		if(ID == EELimited.ALCH_CHEST)
		{
			TileEntity chest = world.getTileEntity(x, y, z);
			if(chest instanceof TileEntityAlchChest)
			{
				return new GuiAlchChest(player.inventory,(TileEntityAlchChest)chest);
			}
		}
		if(ID == EELimited.AGGREGATOR)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAggregator)
			{
				return new GuiAggregator(player.inventory,(TileEntityAggregator)tile);
			}
		}
		return null;
	}

}
