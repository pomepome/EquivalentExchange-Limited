package ee.features;

import java.util.EnumSet;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

	static boolean flag = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	 @Override
	    public void tickEnd(EnumSet<TickType> type, Object... tickData)
	    {
	        if (type.equals(EnumSet.of(TickType.RENDER)))
	        {
	            onRenderTick();
	        }
	        else if (type.equals(EnumSet.of(TickType.CLIENT)))
	        {
	            GuiScreen guiscreen = FMLClientHandler.instance().getClient().currentScreen;
	            if (guiscreen != null)
	            {
	                onTickInGUI(guiscreen);
	            } else {
	                onTickInGame();
	            }
	        }
	    }

	 @Override
	    public EnumSet<TickType> ticks()
	    {
	        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	    }

	    @Override
	    public String getLabel() { return null; }


	    public void onRenderTick()
	    {

	    }

	    public void onTickInGUI(GuiScreen guiscreen)
	    {

	    }

	    public void onTickInGame()
	    {
	    	EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
	    	if(player.getHeldItem() != null&&EELimited.debug)
	    	{
	    		if(player.getHeldItem().getItem() != null)
	    		{
	    			player.addChatMessage(player.getHeldItem().getItemName());
	    		}
	    	}
	    	if(!flag)
	    	{
	    		flag = true;
	    		if(ModLoader.isModLoaded("IC2"))
	    		{
	    			EELimited.IC2Addon();
	    		}
	    		EELimited.addSmeltingExchange();
	    	}
	    }

}
