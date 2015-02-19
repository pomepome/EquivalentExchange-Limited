package ee.features.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import ee.features.EEProxy;
import ee.features.NameRegistry;

public class ItemDMSword extends ItemEE
{
    public ItemDMSword()
    {
        super(NameRegistry.DMSword,6);
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        if (par1ItemStack.getItemDamage() == 1)
        {
            par2EntityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(EEProxy.getPlayer()), toolDamage * 2);
        }
        return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }
}