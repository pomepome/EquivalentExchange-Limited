package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.entity.EntityMobRandomizer;

public class ItemPhilosophersStone extends ItemEEFunctional implements IExtraFunction,IProjectileShooter {
	public ItemPhilosophersStone()
    {
        super(NameRegistry.Philo);
    }
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer p)
    {
		if(!p.isSneaking())
		{
			return var1;
		}
		return new ItemStack(EELimited.PhilTool);
    }
	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		p.openGui(EELimited.instance,EELimited.CRAFT,p.worldObj,(int)p.posX,(int)p.posY, (int)p.posZ);
	}
	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityMobRandomizer(player.worldObj,player));
		player.worldObj.playSoundAtEntity(player, "ee:items.transmute",1.0F,1.0F);
		return true;
	}
}
