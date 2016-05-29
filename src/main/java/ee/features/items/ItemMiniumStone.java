package ee.features.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.items.interfaces.IExtraFunction;
import ee.gui.PhilData;
import ee.network.PacketHandler;
import ee.network.PacketSound;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemMiniumStone extends ItemEEFunctional implements IExtraFunction
{
	private static final int MAX_USES = 64;
	public ItemMiniumStone()
	{
		super("MiniumStone");
	}
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
		int damage = 0;
		prepareStack(itemStack);
		damage = itemStack.getTagCompound().getInteger("damage");
		boolean broken = false;
        ItemStack copiedStack = itemStack.copy();
        NBTTagCompound nbt = copiedStack.getTagCompound();
        nbt.setInteger("damage", damage + 1);
        copiedStack.stackSize = 1;
        if(broken)
        {
        	copiedStack = null;
        }
        return copiedStack;
    }
	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		p.openGui(EELimited.instance, EELimited.MINIUM_CRAFT, p.worldObj, (int)p.posX, (int)p.posY, (int)p.posZ);
	}
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		prepareStack(itemStack);
		list.add("Uses left:"+(MAX_USES- itemStack.getTagCompound().getInteger("damage")));
	}
	public static PhilData getPhilData(ItemStack item, World world)
	{
		PhilData data = null;
		if(item != null && item.getItem() instanceof ItemMiniumStone)
		{
			data = ItemPhilosophersStone.getPhilData(world);
		}
		return data;
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
		prepareStack(stack);
        return stack.getTagCompound().getInteger("damage") > 0;
    }
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
		prepareStack(stack);
        return (double)stack.getTagCompound().getInteger("damage") / MAX_USES;
    }
	private void prepareStack(ItemStack stack)
	{
		if(!stack.hasTagCompound())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("damage", 0);
			stack.setTagCompound(nbt);
		}
	}
}
