package ee.features.tiles;

import java.util.List;

import ee.features.EEBlocks;
import ee.features.EEItems;
import ee.features.EELimited;
import ee.features.items.ItemEE;
import ee.network.PacketAlchChestUpdate;
import ee.network.PacketChatMessage;
import ee.network.PacketHandler;
import ee.util.EEProxy;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAlchChest extends TileDirection implements IInventory
{
	ItemStack[] inventory = new ItemStack[104];
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	public boolean isEETorchOn;
	private boolean prevEETorchOn;

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		inventory = new ItemStack[104];
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			byte slot = subNBT.getByte("Slot");

			if (slot >= 0 && slot < 104)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(subNBT);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < 104; i++)
		{
			if (inventory[i] == null)
			{
				continue;
			}

			NBTTagCompound subNBT = new NBTTagCompound();
			subNBT.setByte("Slot", (byte) i);
			inventory[i].writeToNBT(subNBT);
			list.appendTag(subNBT);
		}

		nbt.setTag("Items", list);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return inventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		ItemStack stack = inventory[var1];
		if (stack != null)
		{
			if (stack.stackSize <= var2)
			{
				inventory[var1] = null;
			}
			else
			{
				stack = stack.splitStack(var2);
				if (stack.stackSize == 0)
				{
					inventory[var1] = null;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (inventory[slot] != null)
		{
			ItemStack stack = inventory[slot];
			inventory[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
		this.onInventoryChanged();
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Alchemical Chest";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return false;
	}

	@Override
	public boolean receiveClientEvent(int number, int arg)
	{
		if (number == 1)
		{
			numPlayersUsing = arg;
			return true;
		}
		else return super.receiveClientEvent(number, arg);
	}

	@Override
	public void openInventory()
	{
		++numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, EEBlocks.AlchChest, 1, numPlayersUsing);
	}

	@Override
	public void closeInventory()
	{
		--numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, EEBlocks.AlchChest, 1, numPlayersUsing);
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		updateChest();
		updateRelic();
	}
	private void onInventoryChanged()
	{
		if(EEBlocks.EETorch == null)
		{
			return;
		}
		isEETorchOn = EEProxy.getStackFromInv(inventory, new ItemStack(EEBlocks.EETorch)) != null;
		if(isEETorchOn != prevEETorchOn)
		{
			prevEETorchOn = isEETorchOn;
			PacketHandler.sendToServer(new PacketAlchChestUpdate(xCoord, yCoord, zCoord));
		}
	}
	private void updateChest()
	{
		if (++ticksSinceSync % 20 == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, EEBlocks.AlchChest, 1, numPlayersUsing);
		}
		if(ticksSinceSync % 2 == 0)
		{
			onInventoryChanged();
		}
		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;
		double adjustedXCoord, adjustedZCoord;

		if (numPlayersUsing > 0 && lidAngle == 0.0F)
		{
			adjustedXCoord = xCoord + 0.5D;
			adjustedZCoord = zCoord + 0.5D;
			worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;

			if (numPlayersUsing > 0)
			{
				lidAngle += angleIncrement;
			}
			else
			{
				lidAngle -= angleIncrement;
			}

			if (lidAngle > 1.0F)
			{
				lidAngle = 1.0F;
			}

			if (lidAngle < 0.5F && var8 >= 0.5F)
			{
				adjustedXCoord = xCoord + 0.5D;
				adjustedZCoord = zCoord + 0.5D;
				worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F)
			{
				lidAngle = 0.0F;
			}
		}
	}
	private void updateRelic()
	{
		if (!this.worldObj.isRemote)
		{
			if(EEItems.Repair != null)
			{
				ItemStack rTalisman = EEProxy.getStackFromInv(this, new ItemStack(EEItems.Repair));

				if (rTalisman != null)
				{
						byte coolDown = rTalisman.stackTagCompound.getByte("Cooldown");

						if (coolDown > 0)
						{
							rTalisman.stackTagCompound.setByte("Cooldown", (byte) (coolDown - 1));
						}
						else
						{
							boolean hasAction = false;

							for (int i = 0; i < 104; i++)
							{
								ItemStack invStack = this.getStackInSlot(i);

								if (invStack == null || invStack.getItem() instanceof ItemEE)
								{
									continue;
								}

								if (!invStack.getHasSubtypes() && invStack.getMaxDamage() != 0 && invStack.getItemDamage() > 0)
								{
									invStack.setItemDamage(invStack.getItemDamage() - 1);
									this.setInventorySlotContents(i, invStack);

									if (!hasAction)
									{
										hasAction = true;
									}
								}
							}

							if (hasAction)
							{
								rTalisman.stackTagCompound.setByte("Cooldown", (byte) 19);
							}
						}
					}
				}
				if(EEItems.BHR != null)
				{
					ItemStack blackHoleBand = EEProxy.getStackFromInv(this, new ItemStack(EEItems.BHR, 1, 1));

					if (blackHoleBand != null)
					{
						AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.xCoord - 5, this.yCoord - 5, this.zCoord - 5, this.xCoord + 5, this.yCoord + 5, this.zCoord + 5);

						List<EntityItem> itemList = this.worldObj.getEntitiesWithinAABB(EntityItem.class, box);
						for (EntityItem item : itemList)
						{
							if (getDistanceFrom(item.posX, item.posY, item.posZ) <= 4.0f)
							{
								if(EELimited.Debug)
								{
									PacketHandler.sendToServer(new PacketChatMessage("Picked up "+ item.getEntityItem().getDisplayName()));
								}
								if (EEProxy.hasSpace(this, item.getEntityItem()))
								{
									ItemStack remain = EEProxy.pushStackInInv(this, item.getEntityItem());
									worldObj.playSoundEffect(item.posX,item.posY,item.posZ,"random.pop", 0.2F, ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
									if (remain == null)
									{
										item.setDead();
									}
								}
							}
							else
							{
								double d1 = (this.xCoord - item.posX);
								double d2 = (this.yCoord - item.posY);
								double d3 = (this.zCoord - item.posZ);
								double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

								item.motionX += d1 / d4 * 0.2D;
								item.motionY += d2 / d4 * 0.2D;
								item.motionZ += d3 / d4 * 0.2D;
							}
						}
					}
				}
		}
	}
}