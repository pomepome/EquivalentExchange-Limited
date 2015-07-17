package ee.features.items.armor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

public class ItemDMArmor extends ItemArmor implements ISpecialArmor
{

	private final EnumArmorType type;

	public ItemDMArmor(EnumArmorType type)
	{
		super(ArmorMaterial.DIAMOND,0,type.ordinal());
		this.setCreativeTab(EELimited.TabEE).setUnlocalizedName("DMArmor_"+type.name).setHasSubtypes(false).setMaxDamage(0);
		this.type = type;
		GameRegistry.registerItem(this,"DMArmor_"+type.name);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,int slot)
	{
		EnumArmorType t = ((ItemDMArmor)armor.getItem()).type;
		if(source.isExplosion())
		{
			return new ArmorProperties(1, 1.0D, 350);
		}
		if (t == EnumArmorType.HEAD && source == DamageSource.fall)
		{
			return new ArmorProperties(1, 1.0D, 5);
		}
		if (t == EnumArmorType.HEAD || t == EnumArmorType.FEET)
		{
			return new ArmorProperties(0, 0.2D, 100);
		}
		return new ArmorProperties(0, 0.3D, 150);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		EnumArmorType t = ((ItemDMArmor) armor.getItem()).type;
		return (t == EnumArmorType.HEAD || t == EnumArmorType.FEET) ? 4 : 6;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot){}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons (IIconRegister par1IconRegister)
	{
		String type = this.type.name;

		this.itemIcon = par1IconRegister.registerIcon("ee:dm_armor/"+type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type)
	{
		char index = this.type == EnumArmorType.LEGS ? '2' : '1';
		return "ee:textures/armor/darkmatter_"+index+".png";
	}
}