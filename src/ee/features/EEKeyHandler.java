package ee.features;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class EEKeyHandler extends KeyHandler {

	static KeyBinding eeCharge = new KeyBinding("Activate",Keyboard.KEY_G);
	public EEKeyHandler() {
		super(new KeyBinding[]{eeCharge},new boolean[]{false});
	}

	@Override
	public String getLabel() {
		return "eeKeys";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,boolean tickEnd, boolean isRepeat) {
		ItemStack is = EELimited.getMod().getPlayer().getCurrentEquippedItem();
		if(is!= null && is.getItem() instanceof ItemFunction)
		{
			((ItemFunction)is.getItem()).onActivate(is);
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
