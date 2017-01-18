package ee.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import ee.features.EELimited;
import ee.features.items.ItemAlchemyBag;
import ee.features.tiles.DMPedestalTile;
import ee.features.tiles.TileEMCCharger;
import ee.features.tiles.TileEntityAggregator;
import ee.features.tiles.TileEntityAlchChest;
import ee.features.tiles.TileEntityColoredAlchChest;
import ee.features.tiles.TileEntityLocus;
import ee.features.tiles.TileFuelBurner;
import ee.gui.GuiAggregator;
import ee.gui.GuiAlchChest;
import ee.gui.GuiColoredAlchChest;
import ee.gui.GuiEMCCharger;
import ee.gui.GuiFuelBurner;
import ee.gui.GuiLocus;
import ee.gui.GuiMiniumWorkbench;
import ee.gui.GuiPedestal;
import ee.gui.GuiPhilWorkbench;
import ee.gui.InventoryAlchBag;
import ee.gui.container.ContainerAggregator;
import ee.gui.container.ContainerAlchBag;
import ee.gui.container.ContainerAlchChest;
import ee.gui.container.ContainerCharger;
import ee.gui.container.ContainerColoredAlchChest;
import ee.gui.container.ContainerFuelBurner;
import ee.gui.container.ContainerLocus;
import ee.gui.container.ContainerMiniumWorkbench;
import ee.gui.container.ContainerPedestal;
import ee.gui.container.ContainerPhilWorkbench;
import ee.network.PacketChatMessage;
import ee.network.PacketHandler;
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
		if(ID == EELimited.MINIUM_CRAFT)
		{
			return new ContainerMiniumWorkbench(player.inventory);
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
		if(ID == EELimited.LOCUS)
		{
			TileEntity locus = world.getTileEntity(x, y, z);
			if(locus instanceof TileEntityLocus)
			{
				return new ContainerLocus((TileEntityLocus)locus,player.inventory);
			}
		}
		if(ID == EELimited.ALCH_COLORED)
		{
			TileEntity alch = world.getTileEntity(x, y, z);
			if(alch instanceof TileEntityColoredAlchChest)
			{
				return new ContainerColoredAlchChest(player.inventory,(TileEntityColoredAlchChest)alch);
			}
		}
		if(ID == EELimited.PEDESTAL)
		{
			TileEntity ped = world.getTileEntity(x, y, z);
			if(ped instanceof DMPedestalTile)
			{
				return new ContainerPedestal(player.inventory, (DMPedestalTile)ped);
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
		if(ID == EELimited.MINIUM_CRAFT)
		{
			return new GuiMiniumWorkbench(player.inventory);
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
		if(ID == EELimited.LOCUS)
		{
			TileEntity locus = world.getTileEntity(x, y, z);
			if(locus instanceof TileEntityLocus)
			{
				return new GuiLocus((TileEntityLocus)locus,player);
			}
		}
		if(ID == EELimited.ALCH_COLORED)
		{
			TileEntity alch = world.getTileEntity(x, y, z);
			if(alch instanceof TileEntityColoredAlchChest)
			{
				return new GuiColoredAlchChest(player.inventory,(TileEntityColoredAlchChest)alch);
			}
		}
		if(ID == EELimited.PEDESTAL)
		{
			TileEntity ped = world.getTileEntity(x, y, z);
			if(ped instanceof DMPedestalTile)
			{
				return new GuiPedestal(player.inventory, (DMPedestalTile)ped);
			}
		}
		return null;
	}

}
