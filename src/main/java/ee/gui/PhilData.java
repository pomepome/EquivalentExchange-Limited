package ee.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class PhilData extends WorldSavedData
{
	
	public ItemStack[] inventory;
	
	public PhilData(String data_name)
	{
		super(data_name);
		inventory = new ItemStack[9];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList taglist = nbt.getTagList("Items", 10);

        for (int var3 = 0; var3 < taglist.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)taglist.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < inventory.length)
            {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList taglist = new NBTTagList();

		for (int var3 = 0; var3 < inventory.length; ++var3)
		{
			if (inventory[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				inventory[var3].writeToNBT(var4);
				taglist.appendTag(var4);
			}
		}
		nbt.setTag("Items", taglist);
	}

}
