package ee.features;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class EEKeyHandler extends KeyHandler {

	static KeyBinding key = new KeyBinding("Activate",Keyboard.KEY_H);
	boolean skip = false;

	public EEKeyHandler() {
		super(new KeyBinding[]{key},new boolean[]{false});
	}

	@Override
	public String getLabel() {
		return "Activate";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,boolean tickEnd, boolean isRepeat)
	{
		if(skip)
		{
			skip = false;
			return;
		}
		skip = true;
		try
		{
			ItemStack is = FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem();
			Item item = is.getItem();
			if(is != null && item != null && item instanceof ItemEE)
			{
				((ItemEE)item).onActivated(is);
			}
		}
		catch(Exception e)
		{

		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

}
