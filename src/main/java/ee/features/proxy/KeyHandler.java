package ee.features.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;

public class KeyHandler {
	public static final KeyBinding keyCraft = new KeyBinding("key.craft",Keyboard.KEY_C,"EELimited");
	public static void init()
	{
		ClientRegistry.registerKeyBinding(keyCraft);
	}
}
