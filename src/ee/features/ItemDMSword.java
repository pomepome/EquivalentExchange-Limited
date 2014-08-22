package ee.features;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.client.FMLClientHandler;

public class ItemDMSword extends ItemEE
{
    public ItemDMSword(int par1)
    {
        super(par1, NameRegistry.DMSword, 10);
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        if (par1ItemStack.getItemDamage() == 1)
        {
            par2EntityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(FMLClientHandler.instance().getClient().thePlayer), 20);
            par2EntityLivingBase.setFire(20);
        }

        return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }
}
