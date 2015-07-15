package ee.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.KeyRegistry;

@SideOnly(Side.CLIENT)
public class KeyEvent
{
	@SubscribeEvent
	public void keyPress(KeyInputEvent event)
	{
		for (int i = 0; i < KeyRegistry.array.length; i++)
		{
			if (KeyRegistry.array[i].isPressed())
			{
				PacketHandler.sendToServer(new PacketKeyInput(i));
			}
		}
	}
}
