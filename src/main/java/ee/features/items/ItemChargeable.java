package ee.features.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemChargeable extends ItemEE implements IChargeable {

	int numCharges;

	public ItemChargeable(String name,int numCharge)
	{
		super(name);
		numCharges = numCharge;
		this.setMaxStackSize(1).setContainerItem(this);
	}
	public ItemChargeable(String name,int numCharge,int damage)
	{
		super(name,damage);
		numCharges = numCharge;
		this.setMaxStackSize(1).setContainerItem(this);
	}
	@Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.hasTagCompound();
    }
    /**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	int charge = getChargeLevel(stack);
    	return charge == 0 ? 1.0 : 1.0 - ((double)charge / (double)numCharges);
    }

    @Override
	public void changeCharge(EntityPlayer player, ItemStack stack)
	{
		int currentCharge = getChargeLevel(stack);

		if (player.isSneaking())
		{
			if (currentCharge > 0)
			{
				player.worldObj.playSoundAtEntity(player, "ee:items.uncharge", 1.0F, 0.5F + ((0.5F / (float)numCharges) * currentCharge));
				stack.stackTagCompound.setInteger("Charge",currentCharge - 1);
			}
		}
		else if (currentCharge < numCharges)
		{
			player.worldObj.playSoundAtEntity(player, "ee:items.charge", 1.0F, 0.5F + ((0.5F / (float)numCharges) * currentCharge));
			stack.stackTagCompound.setInteger("Charge",currentCharge + 1);
		}
	}

	@Override
	public int getChargeLevel(ItemStack is)
	{
		if(is.hasTagCompound())
		{
			return is.getTagCompound().getInteger("Charge");
		}
		else
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("Charge",0);
			is.setTagCompound(nbt);
			return 0;
		}
	}

}
