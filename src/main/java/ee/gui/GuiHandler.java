package ee.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;
import ee.features.tiles.TileEMCCharger;
import ee.features.tiles.TileEntityAggregator;
import ee.features.tiles.TileEntityAlchChest;
import ee.features.tiles.TileFuelBurner;
import ee.gui.container.ContainerAggregator;
import ee.gui.container.ContainerAlchBag;
import ee.gui.container.ContainerAlchChest;
import ee.gui.container.ContainerCharger;
import ee.gui.container.ContainerFuelBurner;
import ee.gui.container.ContainerPhilWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
		if(ID == EELimited.CHARGER)
		{
			TileEntity charger = world.getTileEntity(x, y, z);
			if(charger instanceof TileEMCCharger)
			{
				return new ContainerCharger(player.inventory,(TileEMCCharger)charger);
			}
		}
		if(ID == EELimited.BURNER)
		{
			TileEntity charger = world.getTileEntity(x, y, z);
			if(charger instanceof TileFuelBurner)
			{
				return new ContainerFuelBurner(player.inventory,(TileFuelBurner)charger);
			}
		}
		if(ID == EELimited.AGGREGATOR)
		{
			TileEntity aggregator = world.getTileEntity(x, y, z);
			if(aggregator instanceof TileEntityAggregator)
			{
				return new ContainerAggregator((TileEntityAggregator)aggregator,player);
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
		if(ID == EELimited.CHARGER)
		{
			TileEntity charger = world.getTileEntity(x, y, z);
			if(charger instanceof TileEMCCharger)
			{
				return new GuiEMCCharger(player.inventory,(TileEMCCharger)charger);
			}
		}
		if(ID == EELimited.BURNER)
		{
			TileEntity charger = world.getTileEntity(x, y, z);
			if(charger instanceof TileFuelBurner)
			{
				return new GuiFuelBurner(player.inventory,(TileFuelBurner)charger);
			}
		}
		if(ID == EELimited.AGGREGATOR)
		{
			TileEntity aggregator = world.getTileEntity(x, y, z);
			if(aggregator instanceof TileEntityAggregator)
			{
				return new GuiAggregator((TileEntityAggregator)aggregator,player);
			}
		}
		return null;
	}

}
