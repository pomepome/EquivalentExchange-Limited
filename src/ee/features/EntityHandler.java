package ee.features;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntityHandler
{
    @ForgeSubscribe
    public void entityLivingUpdate(LivingUpdateEvent e)
    {
        if (!EELimited.isStarted)
        {
            EELimited.isStarted = true;
            EELimited.instance.addSmeltingExchange();
        }

        EntityLivingBase entity = e.entityLiving;

        if (entity instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)entity;
            ItemStack[] inv = p.inventory.mainInventory;

            if (p.capabilities.isCreativeMode)
            {
                return;
            }

            boolean allowFly = false;
            boolean disableDamage = false;

            for (int i = 0; i < inv.length; i++)
            {
                ItemStack is = inv[i];

                if (is == null)
                {
                    continue;
                }

                if (is.itemID == EELimited.IDSwift && is.getItemDamage() == 1)
                {
                    allowFly = true;
                }

                if (is.itemID == EELimited.IDDD && is.getItemDamage() == 1)
                {
                    disableDamage = true;
                }
            }

            p.capabilities.allowFlying = allowFly;
            p.capabilities.disableDamage = disableDamage;

            if (p.inventory.hasItem(EELimited.IDVolc))
            {
                p.extinguish();
            }
        }
    }
    @ForgeSubscribe
    public void livingHurt(LivingHurtEvent e)
    {
        if (e.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)e.entityLiving;
            DamageSource d = e.source;

            if (p.inventory.hasItem(EELimited.IDVolc) && (d == DamageSource.lava || d == DamageSource.inFire || d == DamageSource.onFire))
            {
                e.setCanceled(true);
            }
        }
    }
}
