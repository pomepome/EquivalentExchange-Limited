package ee.features.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ee.features.EELimited;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		if(ID == EELimited.CraftID)
		{
			return new ContainerCrafting(player,world);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		if(ID == EELimited.CraftID)
		{
			return new GuiPCrafting(player.inventory,world);
		}
		return null;
	}

}
