package ee.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;

public final class ReflectionUtil
{
	//Field mappings. {"MCP Name", "Obfuscated Name", "SRG Name"}
	private static final String[] arrowInGroundMapping = new String[]{"inGround", "i", "field_70254_i"};
	private static final String[] FireImmuneMapping = new String[]{"isImmuneToFire", "ae", "field_70178_ae"};
	private static final String[] playerWalkSpeedMapping = new String[]{"walkSpeed", "g", "field_75097_g"};

	public static boolean getArrowInGround(EntityArrow instance)
	{
		return ReflectionHelper.getPrivateValue(EntityArrow.class, instance, arrowInGroundMapping);
	}

	public static void setEntityFireImmunity(Entity instance, boolean value)
	{
		ReflectionHelper.setPrivateValue(Entity.class, instance, value, FireImmuneMapping);
	}

	public static void setPlayerCapabilityWalkspeed(PlayerCapabilities instance, float value)
	{
		ReflectionHelper.setPrivateValue(PlayerCapabilities.class, instance, value, playerWalkSpeedMapping);
	}
}
