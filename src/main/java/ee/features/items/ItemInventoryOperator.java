package ee.features.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.items.interfaces.IExtraFunction;
import ee.util.EEProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemInventoryOperator extends ItemChargeable implements IExtraFunction
{

	public ItemInventoryOperator()
	{
		super("inventiryOperator",1);
		this.setTextureName("sugar");
	}

	private void setMode(ItemStack stack,int mode)
	{
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(mode > 1 || mode < 0)
		{
			mode = 0;
		}
		stack.getTagCompound().setInteger("Mode", mode);
	}

	private int getMode(ItemStack stack)
	{
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Mode"))
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Mode", 0);
			stack.setTagCompound(tag);
			return 0;
		}
		return stack.getTagCompound().getInteger("Mode");
	}

	private String getModeText(ItemStack stack)
	{
		int mode = getMode(stack);
		String raw = "NOTHING";
		switch(mode)
		{
			case 0 : raw = "operator.remove";break;
			case 1 : raw = "operator.copy";break;
		}
		return StatCollector.translateToLocal(raw);
	}
	private String getModeDescText(int mode)
	{
		String raw = "NOTHING";
		switch(mode)
		{
			case 0 : raw = "operator.desc.remove";break;
			case 1 : raw = "operator.desc.copy";break;
		}
		return StatCollector.translateToLocal(raw);
	}

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
    	list.add("Mode:"+getModeText(stack));
    }

	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		int mode = getMode(is);
		mode = (mode + 1) % 2;
		EEProxy.chatToPlayer(p, getModeDescText(mode));
		setMode(is,mode);
		if(mode == 0 && is.getTagCompound().hasKey("Content"))
		{
			EEProxy.chatToPlayer(p, "Saved content removed,");
			is.getTagCompound().removeTag("Content");
		}
	}
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float minX, float minY, float minZ)
    {
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null)
		{
			getMode(stack);
			return true;
		}
		if(player.isSneaking() && !world.isRemote && getChargeLevel(stack) == 1)
		{
			int mode = getMode(stack);
			TileEntity tile = world.getTileEntity(x, y, z);
			if(mode == 0)
			{
				if(tile != null && tile instanceof IInventory)
				{
					IInventory inv = (IInventory)tile;
					for(int i = 0;i < inv.getSizeInventory();i++)
					{
						inv.setInventorySlotContents(i, null);
					}
					inv.markDirty();
					EEProxy.chatToPlayer(player, "Inventory cleared.");
				}
			}
			else
			{
				if(tile != null && tile instanceof IInventory)
				{
					IInventory inv = (IInventory)tile;
					if(tag.hasKey("Content"))
					{
						for(int i = 0;i < inv.getSizeInventory();i++)
						{
							inv.setInventorySlotContents(i, null);
						}
						NBTTagList tagList = tag.getTagList("Content", 10);
						for(int i = 0;i < tagList.tagCount();i++)
						{
							NBTTagCompound comp = tagList.getCompoundTagAt(i);
							int slot = comp.getInteger("Slot");
							ItemStack content = ItemStack.loadItemStackFromNBT(comp);
							if(slot >= 0 && slot < inv.getSizeInventory())
							{
								inv.setInventorySlotContents(slot, content);
							}
						}
						EEProxy.chatToPlayer(player, "Content has pasted.");
					}
					else
					{
						NBTTagList tagList = new NBTTagList();
						for(int i = 0;i < inv.getSizeInventory();i++)
						{
							ItemStack content = inv.getStackInSlot(i);
							if(content != null)
							{
								NBTTagCompound subNBT = new NBTTagCompound();
								subNBT.setInteger("Slot", i);
								content.writeToNBT(subNBT);
								tagList.appendTag(subNBT);
							}
						}
						if(tagList.tagCount() > 0)
						{
							tag.setTag("Content", tagList);
						}
						EEProxy.chatToPlayer(player, "Content has copied.");
					}
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
		return true;
    }
}
