package ee.features.items;

import java.util.List;

import ee.features.EELimited;
import ee.features.items.interfaces.IExtraFunction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMiniumStone extends ItemEEFunctional implements IExtraFunction
{
	public ItemMiniumStone()
	{
		super("MiniumStone");
		this.setMaxDamage(64);
	}
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copiedStack = itemStack.copy();
        
        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }
	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		p.openGui(EELimited.instance, EELimited.CRAFT, p.worldObj, (int)p.posX, (int)p.posY, (int)p.posZ);
	}
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Uses left:"+(this.getMaxDamage() - itemStack.getItemDamage()));
	}
}
