package ee.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class BagData extends WorldSavedData {

	public ItemStack[] inventory = new ItemStack[104];
	public boolean initialized;
	public boolean needUpdate;

	public BagData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

	public void onUpdate(World var1, EntityPlayer var2)
	{
		if(!initialized)
		{
			initialized = true;
		}
		if(var1.getWorldTime() % 801 == 1)
		{
			needUpdate = true;
		}
		if(needUpdate)
		{
			markDirty();
			needUpdate = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound p_76184_1_) {
		NBTTagList var2 = p_76184_1_.getTagList("Items", 10);
        this.inventory = new ItemStack[104];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < inventory.length)
            {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound var1) {
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < inventory.length; ++var3)
		{
			if (inventory[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		var1.setTag("Items", var2);
	}

}
