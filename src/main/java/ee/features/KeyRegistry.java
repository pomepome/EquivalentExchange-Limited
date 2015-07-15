package ee.features;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyRegistry {
	public static final KeyBinding[] array = new KeyBinding[]{new KeyBinding("Activate",Keyboard.KEY_G,"EELimited"),new KeyBinding("Charge",Keyboard.KEY_V,"EELimited"),new KeyBinding("Special",Keyboard.KEY_R,"EELimited"),new KeyBinding("Extra",Keyboard.KEY_C,"EELimited")};
	public static boolean isKeyDown(int i)
	{
		return array[i].isPressed();
	}
	public static void registerKies()
	{
		for(int i = 0;i < array.length;i++)
		{
			ClientRegistry.registerKeyBinding(array[i]);
		}
	}
}
