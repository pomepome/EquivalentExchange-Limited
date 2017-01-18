package ee.util;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UNKNOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.items.ItemKleinStar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class EEProxy
{
	public static final int MAXWORLDHEIGHT = 256;
    private static boolean initialized;
    public static Minecraft mc;
    private static EELimited ee;
    private static List<Class> peacefuls = new ArrayList<Class>();
	private static List<Class> mobs = new ArrayList<Class>();
	private static Map<Item,Integer> EMCMap = new HashMap<Item,Integer>();
	private static final Logger log = LogManager.getLogger("EELimited|Proxy");

    public static void Init(Minecraft var0, EELimited var1)
    {
        if (!initialized)
        {
            initialized = true;
        }
        loadEntityLists();
        registerFuels();
        mc = var0;
        ee = var1;
    }
    public static boolean containsNull(List list)
    {
    	for(Object obj : list)
    	{
    		if(obj == null)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public static ItemStack copyStack(ItemStack stack)
    {
    	return stack == null ? null : stack.copy();
    }
    public static ItemStack[] copyStacks(ItemStack[] stacks,int size)
    {
    	ItemStack[] ret = new ItemStack[size];
    	for(int i = 0;i < size;i++)
    	{
    		if(i >= stacks.length)
    		{
    			ret[i] = null;
    		}
    		else
    		{
    			ret[i] = stacks[i];
    		}
    	}
    	return ret;
    }
    public static ItemStack[] copyStacks(ItemStack... stacks)
    {
    	ItemStack[] ret = new ItemStack[stacks.length];
    	for(int i = 0;i < ret.length;i++)
    	{
    		ret[i] = copyStack(stacks[i]);
    	}
    	return ret;
    }
    public static ItemStack[] copyStacks(IInventory inv)
    {
    	ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
    	for(int i = 0;i < inv.getSizeInventory();i++)
    	{
    		ret[i] = copyStack(inv.getStackInSlot(i));
    	}
    	return ret;
    }
    public static void decrItem(IInventory inv,ItemStack stack)
    {
    	if(getItemCount(inv, stack) >= stack.stackSize)
    	{
    		int count = stack.stackSize;
    		for(int i = 0; i < inv.getSizeInventory();i++)
    		{
    			ItemStack stackInv = inv.getStackInSlot(i);
    			if(stackInv == null)
    			{
    				continue;
    			}
    			if(stackInv.getItem() == stack.getItem() && stackInv.getItemDamage() == stack.getItemDamage())
    			{
    				if(count > stackInv.stackSize)
    				{
    					count -= stackInv.stackSize;
    					inv.setInventorySlotContents(i, null);
    					continue;
    				}
    				stackInv.stackSize -= count;
    				if(stackInv.stackSize == 0)
    				{
    					stackInv = null;
    				}
    				inv.setInventorySlotContents(i, stackInv);
    				break;
    			}
    		}
    	}
    	inv.markDirty();
    }
    public static ItemStack[] decrItem(ItemStack[] inv,ItemStack obj, int count)
    {
    	ItemStack[] inv2 = copyStacks(inv);
    	for(int i = 0;i < inv.length;i++)
    	{
    		if(inv2[i] == null)
    		{
    			continue;
    		}
    		if(areItemStacksEqual(inv2[i],obj))
    		{
    			int toReduce = Math.min(inv2[i].stackSize, count);
    			count -= toReduce;
    			inv2[i].stackSize -= toReduce;
    		}
    		if(inv2[i].stackSize == 0)
    		{
    			inv2[i] = null;
    		}
    		if(count == 0)
    		{
    			break;
    		}
    	}
    	return inv2;
    }

	public static ForgeDirection getOpSide(ForgeDirection dir)
	{
		switch(dir)
		{
			case UP : return DOWN;
			case DOWN : return UP;
			case SOUTH : return NORTH;
			case NORTH : return SOUTH;
			case EAST : return WEST;
			case WEST : return EAST;
			default : return UNKNOWN;
		}
	}
	public static void clearInventory(IInventory inv)
	{
		for(int i = 0;i < inv.getSizeInventory();i++)
		{
			inv.setInventorySlotContents(i, null);
		}
		inv.markDirty();
	}
    public static ItemStack[] sort(ItemStack[] inventory,boolean optSize)
    {
    	List<ItemStack> list = new ArrayList<ItemStack>();
    	List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
    	for(int i = 0;i < inventory.length;i++)
    	{
    		if(inventory[i] == null)
        	{
        		continue;
        	}
    		boolean flag = false;
    		for(int j = 0;j < itemInfos.size();j++)
    		{
    			if(areItemStacksEqual(itemInfos.get(j).stack,inventory[i]))
    			{
    				itemInfos.get(j).amount += inventory[i].stackSize;
    				flag = true;
    				break;
    			}
    		}
    		if(!flag)
    		{
    			itemInfos.add(new ItemInfo(inventory[i],inventory[i].stackSize));
    		}
    	}
    	Collections.sort(itemInfos, new ComparatorItemInfo());
    	for(ItemInfo info : itemInfos)
    	{
    		ItemStack key = info.stack;
    		int limit = Math.min(key.getMaxStackSize(), 64);
    		int size = info.amount;
    		while(size > 0)
    		{
    			if(size > limit)
    			{
    				size -= limit;
    				list.add(new ItemStack(key.getItem(),limit,key.getItemDamage()));
    			}
    			else
    			{
    				list.add(new ItemStack(key.getItem(),size,key.getItemDamage()));
    				size = 0;
    			}
    		}
    	}
    	if(optSize)
    	{
    		if(list.size() < inventory.length)
    		{
    			for(int i = list.size();i < inventory.length;i++)
    			{
    				list.add(null);
    			}
    		}
    	}
    	return toArray(list);
    }
    public static void addToMap(Map<ItemStack,Integer> map,ItemStack is)
    {
    	if(is == null)
    	{
    		return;
    	}
    	Set<Map.Entry<ItemStack,Integer>> entryset = map.entrySet();
    	for(Map.Entry<ItemStack, Integer> entry : entryset)
    	{
    		ItemStack stack = entry.getKey();
    		if(areItemStacksEqual(is,stack))
    		{
    			map.replace(stack, entry.getValue() + is.stackSize);
    			log.info("replaced:"+is.getDisplayName() + "-" +entry.getValue() + is.stackSize);
    		}
    		return;
    	}
    	map.put(normalizeStack(is),is.stackSize);
    }
    public static ItemStack[] toArray(List<ItemStack> list)
    {
    	ItemStack[] ret = new ItemStack[list.size()];
    	for(int i = 0;i < ret.length;i++)
    	{
    		ret[i] = list.get(i);
    	}
    	return ret;
    }
    public static ItemStack normalizeStack(ItemStack stack)
    {
    	if(stack == null)
    	{
    		return null;
    	}
    	ItemStack ret = stack.copy();
    	ret.stackSize = 1;
    	return ret;
    }
    public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z)
	{
    	float jump = ((float) world.rand.nextGaussian() * 0.05F + 0.2F);
    	spawnEntityItem(world, stack, x, y, z, jump);
	}
    public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z,float jump)
	{
    	float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityitem;

		for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world.spawnEntityInWorld(entityitem))
		{
			int j1 = world.rand.nextInt(21) + 10;

			if (j1 > stack.stackSize)
				j1 = stack.stackSize;

			stack.stackSize -= j1;
			entityitem = new EntityItem(world, (double)((float) x + f), (double)((float) y + f1), (double)((float) z + f2), new ItemStack(stack.getItem(), j1, stack.getItemDamage()));
			float f3 = 0.05F;
			entityitem.motionX = (double)((float) world.rand.nextGaussian() * f3);
			entityitem.motionY = jump;
			entityitem.motionZ = (double)((float) world.rand.nextGaussian() * f3);

			if (stack.hasTagCompound())
			{
				entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
			}
		}
	}

	public static void spawnEntityItem(World world, ItemStack stack, int x, int y, int z)
	{
		spawnEntityItem(world,stack, x + 0.5, y + 0.5, z + 0.5);
	}
	public static void spawnEntityItem(World world, ItemStack stack, int x, int y, int z,float jump)
	{
		spawnEntityItem(world,stack, x + 0.5, y + 0.5, z + 0.5,jump);
	}
	public static float getFlowHight(EntityPlayer player)
	{
		World w = player.worldObj;
		int x,y,z;
		x = (int)Math.ceil(player.posX);
		y = (int)Math.ceil(player.posY - player.getYOffset());
		z = (int)Math.ceil(player.posZ);
		int meta = w.getBlockMetadata(x, y - 1, z);
		if(meta >= 8)
		{
			meta = 0;
		}
		return 1f - BlockLiquid.getLiquidHeightPercent(meta);
	}
    public static String getOreDictionaryName(ItemStack stack)
    {
    	int[] IDs = OreDictionary.getOreIDs(stack);
    	if(IDs == null||IDs.length == 0)
    	{
    		return "";
    	}
    	return OreDictionary.getOreName(IDs[0]);
    }
    public static int getRelativeOrientation(EntityLivingBase ent)
    {
    	int direction = 0;
		int facing = MathHelper.floor_double(ent.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH.ordinal();
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST.ordinal();
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH.ordinal();
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST.ordinal();
		}
		return direction;
    }
    public static void setPlayerFlight(EntityPlayer player, boolean state)
	{
		player.capabilities.allowFlying = state;
		if (!state)
		{
			player.capabilities.isFlying = false;
		}
	}
    @SideOnly(Side.CLIENT)
    public static EntityClientPlayerMP getPlayer()
    {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }
    public static World getWorld()
    {
    	return getPlayer().worldObj;
    }
    public static boolean ContainsItemStack(List<ItemStack> list, ItemStack toSearch)
	{
		Iterator<ItemStack> iter = list.iterator();

		while (iter.hasNext())
		{
			ItemStack stack = iter.next();

			if (stack == null)
			{
				continue;
			}

			if (stack.getItem().equals(toSearch.getItem()))
			{
				if( !stack.getHasSubtypes() || stack.getItemDamage() == toSearch.getItemDamage())
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsItemStack(ItemStack[] stacks, ItemStack toSearch)
	{
		for (ItemStack stack : stacks)
		{
			if (stack == null)
			{
				continue;
			}

			if (stack.getItem() == toSearch.getItem())
			{
				if (!stack.getHasSubtypes() || stack.getItemDamage() == toSearch.getItemDamage())
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean invContainsItem(IInventory inv, ItemStack toSearch)
	{
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);

			if (stack != null && basicAreStacksEqual(stack, toSearch))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean invContainsItem(ItemStack inv[], ItemStack toSearch)
	{
		for (ItemStack stack : inv)
		{
			if (stack != null && basicAreStacksEqual(stack, toSearch))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean invContainsItem(ItemStack inv[], Item toSearch)
	{
		for (ItemStack stack : inv)
		{
			if (stack != null && stack.getItem() == toSearch)
			{
				return true;
			}
		}
		return false;
	}
	public static ItemStack pushStackInInv(IInventory inv, ItemStack stack, int low_limit)
	{
		for (int i = low_limit; i < inv.getSizeInventory(); i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null)
			{
				inv.setInventorySlotContents(i, stack);
				return null;
			}

			if (areItemStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv.setInventorySlotContents(i, invStack);
					inv.markDirty();
					return null;
				}

				invStack.stackSize += remaining;
				inv.setInventorySlotContents(i, invStack);
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static ItemStack pushStackInInv(IInventory inv, ItemStack stack, int low_limit,int high_limit)
	{
		for (int i = low_limit; i < high_limit; i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null)
			{
				inv.setInventorySlotContents(i, stack);
				return null;
			}

			if (areItemStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv.setInventorySlotContents(i, invStack);
					inv.markDirty();
					return null;
				}

				invStack.stackSize += remaining;
				inv.setInventorySlotContents(i, invStack);
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static ItemStack pushStackInInv(IInventory inv, ItemStack stack)
	{
		int limit;

		if (inv instanceof InventoryPlayer)
		{
			limit = 36;
		}
		else
		{
			limit = inv.getSizeInventory();
		}

		for (int i = 0; i < limit; i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null)
			{
				inv.setInventorySlotContents(i, stack);
				return null;
			}

			if (areItemStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv.setInventorySlotContents(i, invStack);
					return null;
				}

				invStack.stackSize += remaining;
				inv.setInventorySlotContents(i, invStack);
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static boolean pushStacksInInv(ItemStack[] inv,boolean actuallyPush,ItemStack... stacks)
	{
		int stackLimit = 64;
		if(actuallyPush)
		{
			if(!pushStacksInInv(inv,false,stacks))
			{
				return false;
			}
			for(ItemStack stack : stacks)
			{
				pushStackInInv(inv,stack);
			}
			return true;
		}
		else
		{
			ItemStack[] stackInv = copyStacks(inv,inv.length);
			for(ItemStack stack : stacks)
			{
				if(pushStackInInv(stackInv,stack) != null)
				{
					return false;
				}
			}
			return true;
		}
	}
	public static boolean pushStacksInInv(IInventory inv,boolean actuallyPush,int low_limit,ItemStack... stacks)
	{
		int stackLimit = inv.getInventoryStackLimit();
		if(actuallyPush)
		{
			if(!pushStacksInInv(inv,false,low_limit,stacks))
			{
				return false;
			}
			for(ItemStack stack : stacks)
			{
				pushStackInInv(inv,stack);
			}
			inv.markDirty();
			return true;
		}
		else
		{
			ItemStack[] stackInv = copyStacks(inv);
			for(ItemStack stack : stacks)
			{
				if(pushStackInInv(stackInv,stack) != null)
				{
					return false;
				}
			}
			return true;
		}
	}
	/**
	 *	Returns an itemstack if the stack passed could not entirely fit in the inventory, otherwise returns null.
	 */
	public static ItemStack pushStackInInv(ItemStack[] inv, ItemStack stack)
	{
		for (int i = 0; i < inv.length; i++)
		{
			ItemStack invStack = inv[i];

			if (invStack == null)
			{
				inv[i] = stack;
				return null;
			}

			if (areItemStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv[i] = invStack;
					return null;
				}

				invStack.stackSize += remaining;
				inv[i] = invStack;
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
    public static void chatToPlayer(EntityPlayer p,String message)
    {
    	if(p.worldObj.isRemote)
    	{
    		return;
    	}
    	p.addChatMessage(new ChatComponentText(message));
    }
    public static int getEMC(ItemStack is)
    {
    	if(is.hasTagCompound())
    	{
    		return is.getTagCompound().getInteger("EMC");
    	}
    	return 0;
    }
    public static void removeEMC(ItemStack is,int ammount)
    {
    	if(getEMC(is) > ammount)
    	{
    		setEMC(is,getEMC(is) - ammount);
    	}
    }
    public static int getMaxEMC(ItemStack is)
    {
    	if(is.hasTagCompound())
    	{
    		return is.getTagCompound().getInteger("MaxEMC");
    	}
    	return 0;
    }
    public static void setEMC(ItemStack is,int EMC)
    {
    	if(is.hasTagCompound())
    	{
    		if(EMC > getMaxEMC(is))
    		{
    			EMC = getMaxEMC(is);
    		}
    		is.getTagCompound().setInteger("EMC",EMC);
    	}
    }
    public static int getItemCount(IInventory inv,Item item)
    {
    	int ret = 0;
    	for(int i = 0;i < inv.getSizeInventory();i++)
    	{
    		ItemStack is = inv.getStackInSlot(i);
    		if(is != null && is.getItem() == item)
    		{
    			ret += is.stackSize;
    		}
    	}
    	return ret;
    }
    public static int getItemCount(IInventory inv,ItemStack item)
    {
    	int ret = 0;
    	for(int i = 0;i < inv.getSizeInventory();i++)
    	{
    		ItemStack is = inv.getStackInSlot(i);
    		if(is != null && is.getItem() == item.getItem() && is.getItemDamage() == item.getItemDamage())
    		{
    			ret += is.stackSize;
    		}
    	}
    	return ret;
    }
    public static int getItemCount(ItemStack[] inv,ItemStack item)
    {
    	int ret = 0;
    	for(int i = 0;i < inv.length;i++)
    	{
    		ItemStack is = inv[i];
    		if(is != null && areItemStacksEqual(item, is) && is.getItemDamage() == item.getItemDamage())
    		{
    			ret += is.stackSize;
    		}
    	}
    	return ret;
    }
    public static int getBlockCount(InventoryPlayer inv,Block b)
    {
    	int ret = 0;
    	for(int i = 0;i < inv.getSizeInventory();i++)
    	{
    		ItemStack is = inv.getStackInSlot(i);
    		if(is != null && areItemStacksEqual(is,new ItemStack(b)))
    		{
    			ret += is.stackSize;
    		}
    	}
    	return ret;
    }
    public static void decrItem(InventoryPlayer inv,Item item,int amount)
    {
    	if(inv.player.capabilities.isCreativeMode)
    	{
    		return;
    	}
    	for(int i = 0;i < amount;i++)
    	{
    		inv.consumeInventoryItem(item);
    	}
    	inv.markDirty();
    	inv.player.inventoryContainer.detectAndSendChanges();
    }
    public static boolean useResource(EntityPlayer player,int amount,boolean doUse)
    {
    	if(EELimited.disableResource||player == null)
    	{
    		return true;
    	}
    	if(player.capabilities.isCreativeMode)
    	{
    		return true;
    	}
    	if(doUse && !useResource(player,amount,false))
    	{
    		return false;
    	}
    	Item item = null;
    	InventoryPlayer inv = player.inventory;
    	World w = player.worldObj;
    	for(Map.Entry<Item,Integer> entry : EMCMap.entrySet())
    	{
    		Item i = entry.getKey();
    		int in = entry.getValue();
    		if(amount % in == 0&&getItemCount(inv,i) >= (amount / in))
    		{
    			if(doUse)
    			{
    				decrItem(inv,i,amount / in);
    				savePlayerInventory(player);
    			}
    			return true;
    		}
    	}
    	for(int i = 0;i < inv.getSizeInventory();i++)
    	{
    		ItemStack is = inv.getStackInSlot(i);
    		if(is != null && is.getItem() instanceof ItemKleinStar)
    		{
    			if(!is.hasTagCompound())
    			{
    				continue;
    			}
    			int stored = getEMC(is);
    			if(stored >= amount)
    			{
    				if(doUse)
        			{
    					setEMC(is,stored - amount);
        			}
    				inv.setInventorySlotContents(i, is);
    				savePlayerInventory(player);
    				return true;
    			}
    			else
    			{
    				continue;
    			}
    		}
    	}
    	savePlayerInventory(player);
    	return false;
    }
    public static void savePlayerInventory(EntityPlayer p)
    {
    	p.inventory.markDirty();
    	p.inventoryContainer.detectAndSendChanges();
    }
    public static boolean basicAreStacksEqualWide(ItemStack stack1, Item item)
	{
    	if(stack1 != null)
    	{
    		return stack1.getItem() == item;
    	}
    	else
    	{
    		return false;
    	}
	}
    public static boolean basicAreStacksEqual(ItemStack stack1, ItemStack stack2)
	{
    	if(stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock)
    	{
    		return ((ItemBlock)stack1.getItem()).field_150939_a == ((ItemBlock)stack2.getItem()).field_150939_a;
    	}
		return (stack1.getItem() == stack2.getItem()) && (stack1.getItemDamage() == stack2.getItemDamage());
	}
    public static ItemStack getStackFromInv(IInventory inv, ItemStack stack)
	{
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack s = inv.getStackInSlot(i);

			if (s == null)
			{
				continue;
			}
			if(stack.getItemDamage() > 10000)
			{
				if(basicAreStacksEqualWide(s, stack.getItem()))
				{
					return s;
				}
			}
			else if (basicAreStacksEqual(stack, s))
			{
				return s;
			}
		}

		return null;
	}
    public static ItemStack getStackFromInv(IInventory inv, ItemStack stack,boolean decrease)
	{
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack s = inv.getStackInSlot(i);
			if (s == null)
			{
				continue;
			}
			int amount = s.stackSize;
			if(stack.getItemDamage() > 10000)
			{
				if(basicAreStacksEqualWide(s, stack.getItem()))
				{
					if(decrease)
					{
						if(amount > 1)
						{
							s.stackSize--;
						}
						else
						{
							s = null;
						}
						inv.setInventorySlotContents(i, s);
						inv.markDirty();
					}
					return s;
				}
			}
			else if (basicAreStacksEqual(stack, s))
			{
				if(decrease)
				{
					if(amount > 1)
					{
						s.stackSize--;
					}
					else
					{
						s = null;
					}
					inv.setInventorySlotContents(i, s);
					inv.markDirty();
				}
				return s;
			}
		}

		return null;
	}

	public static ItemStack getStackFromInv(ItemStack[] inv, ItemStack stack)
	{
		if(stack == null)
		{
			return null;
		}
		for (ItemStack s : inv)
		{
			if (s == null)
			{
				continue;
			}
			if(stack.getItemDamage() > 10000)
			{
				if(basicAreStacksEqualWide(s, stack.getItem()))
				{
					return s;
				}
			}
			else if (basicAreStacksEqual(stack, s))
			{
				return s;
			}
		}

		return null;
	}
    public static void setEntityImmuneToFire(Entity target,boolean flag)
    {
    	ReflectionUtil.setEntityFireImmunity(target, flag);
    }
    public static void setPlayerSpeed(EntityPlayer target,float value)
    {
    	ReflectionUtil.setPlayerCapabilityWalkspeed(target.capabilities, value);
    }
    public static ForgeDirection getSide(EntityPlayer p, int x, int y, int z)
    {
    	EntityLivingBase base;
        return ForgeDirection.getOrientation(determineOrientation(p.worldObj, x, y, z, p));
    }
    public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase)
    {
        if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F)
        {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

            if (d0 - (double)par2 > 2.0D)
            {
                return 1;
            }

            if ((double)par2 - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
    public static void registerFuelEMC(Item item,int value)
    {
    	if(!EMCMap.containsKey(item))
    	{
    		EMCMap.put(item, value);
    	}
    }
    public static void registerFuelEMC(Block block,int value)
    {
    	Item item = Item.getItemFromBlock(block);
    	if(!EMCMap.containsKey(item))
    	{
    		EMCMap.put(item, value);
    	}
    }

    public static boolean isClient(World var0)
    {
        return var0.isRemote;
    }

    public static boolean isServer()
    {
        return false;
    }

    public static TileEntity getTileEntity(IBlockAccess var0, int var1, int var2, int var3, Class var4)
    {
        if (var2 < 0)
        {
            return null;
        }
        else
        {
            TileEntity var5 = var0.getTileEntity(var1, var2, var3);
            return !var4.isInstance(var5) ? null : var5;
        }
    }

    public static boolean isEntityFireImmune(Entity var0)
    {
        return var0.isImmuneToFire();
    }

    public static int getEntityHealth(EntityLivingBase var0)
    {
        return (int)var0.getHealth();
    }

    public static FoodStats getFoodStats(EntityPlayer var0)
    {
        return var0.getFoodStats();
    }

    public static WorldInfo getWorldInfo(World var0)
    {
        return var0.getWorldInfo();
    }

    public static int getMaxStackSize(Item var0)
    {
        return var0.getItemStackLimit(new ItemStack(var0));
    }

    public static int blockDamageDropped(Block var0, int var1)
    {
        return var0.damageDropped(var1);
    }

    public static void dropBlockAsItemStack(Block var0, EntityLiving var1, int var2, int var3, int var4, ItemStack var5)
    {
        var0.dropBlockAsItem(var1.worldObj, var2, var3, var4, var5.getItemDamage(), 0);
    }

    public static boolean isJumping(EntityPlayer var0)
    {
        return mc.gameSettings.keyBindJump.isPressed();
    }

    public static boolean isFuel(Object var0,int damage)
    {
        return isFuel(var0,damage);
    }

    public static boolean isFuel(int var0, int var1)
    {
        return GameRegistry.getFuelValue(ee.gs(var0, 1, var1)) != 0;
    }

    public static void playSound(World w,String var0, float var1, float var2, float var3, float var4, float var5)
    {
       w.playSound(var1,var2,var3,var0, var4, var5,false);
    }

    public static void playSoundAtPlayer(String var0, EntityPlayer var1, float var2, float var3)
    {
        var1.worldObj.playSoundAtEntity(var1, var0, var2, var3);
    }
    public static Side getSide()
    {
    	return FMLCommonHandler.instance().getSide();
    }
    public static Entity getRandomEntity(World world, Entity toRandomize)
	{
		Class entClass = toRandomize.getClass();

		if (peacefuls.contains(entClass))
		{
			return getNewEntityInstance((Class) getRandomListEntry(peacefuls, entClass), world);
		}
		else if (mobs.contains(entClass))
		{
			return getNewEntityInstance((Class) getRandomListEntry(mobs, entClass), world);
		}
		else if (world.rand.nextInt(2) == 0)
		{
			return new EntitySlime(world);
		}
		else
		{
			return new EntitySheep(world);
		}
	}

	public static Object getRandomListEntry(List<?> list, Object toExclude)
	{
		Object obj;

		do
		{
			int random = randomIntInRange(list.size() - 1, 0);
			obj = list.get(random);
		}
		while(obj.equals(toExclude));

		return obj;
	}
	public static int randomIntInRange(int max, int min)
	{
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
	public static Entity getNewEntityInstance(Class c, World world)
	{
		try
		{
			Constructor constr = c.getConstructor(World.class);
			Entity ent = (Entity) constr.newInstance(world);

			if (ent instanceof EntitySkeleton)
			{
				if (world.rand.nextInt(2) == 0)
				{
					((EntitySkeleton) ent).setSkeletonType(1);
					ent.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
				}
				else
				{
					ent.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
				}
			}
			else if (ent instanceof EntityPigZombie)
			{
				ent.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
			}

			return ent;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	public static boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		return ItemStack.areItemStacksEqual(getNormalizedStack(stack1), getNormalizedStack(stack2));
	}
	private static ItemStack getNormalizedStack(ItemStack stack1)
	{
		if(stack1 == null)
		{
			return null;
		}
		ItemStack stack = stack1.copy();
		stack.stackSize = 1;
		return stack;
	}
	public static boolean hasSpace(IInventory inv, ItemStack stack)
	{
		return pushStacksInInv(inv, false, 0,stack);
	}

	public static boolean hasSpace(ItemStack[] inv, ItemStack stack)
	{
		return pushStacksInInv(inv,false,stack);
	}
	private static void loadEntityLists()
	{
		//Peacefuls
		peacefuls.add(EntityCow.class);
		peacefuls.add(EntityMooshroom.class);
		peacefuls.add(EntitySheep.class);
		peacefuls.add(EntityPig.class);
		peacefuls.add(EntityChicken.class);
		peacefuls.add(EntityOcelot.class);
		peacefuls.add(EntityVillager.class);
		peacefuls.add(EntitySquid.class);
		peacefuls.add(EntityHorse.class);

		//Mobs
		mobs.add(EntityZombie.class);
		mobs.add(EntitySkeleton.class);
		mobs.add(EntitySpider.class);
		mobs.add(EntityCaveSpider.class);
		mobs.add(EntityCreeper.class);
		mobs.add(EntityEnderman.class);
		mobs.add(EntitySilverfish.class);
		mobs.add(EntityGhast.class);
		mobs.add(EntityPigZombie.class);
		mobs.add(EntitySlime.class);
		mobs.add(EntityWitch.class);
		mobs.add(EntityBlaze.class);
		mobs.add(EntityFireball.class);
	}
	private static void registerFuels()
	{
		registerFuelEMC(Items.redstone,4);
		registerFuelEMC(Items.coal,4);
		registerFuelEMC(Items.glowstone_dust,16);
		registerFuelEMC(Items.gunpowder,48);
	}
	public static boolean canFillTank(IFluidHandler tank, Fluid fluid, int side)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(side);

		if (tank.canFill(dir, fluid))
		{
			boolean canFill = false;

			for (FluidTankInfo tankInfo : tank.getTankInfo(dir))
			{
				if (tankInfo.fluid == null)
				{
					canFill = true;
					break;
				}

				if (tankInfo.fluid.getFluid() == fluid && tankInfo.fluid.amount < tankInfo.capacity)
				{
					canFill = true;
					break;
				}
			}

			return canFill;
		}

		return false;
	}

	public static void fillTank(IFluidHandler tank, Fluid fluid, int side, int quantity)
	{
		tank.fill(ForgeDirection.getOrientation(side), new FluidStack(fluid, quantity), true);
	}
	public static ArrayList<ItemStack> getBlockDrops(World world, EntityPlayer player, Block block, ItemStack stack, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) > 0 && block.canSilkHarvest(world, player, x, y, z, meta))
		{
			return Lists.newArrayList(new ItemStack(block, 1, meta));
		}

		return block.getDrops(world, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
	}

	/**
	 * Gets an AABB for AOE digging operations. The offset increases both the breadth and depth of the box.
	 */
	public static AxisAlignedBB getBroadDeepBox(Coordinates coords, ForgeDirection direction, int offset)
	{
		if (direction.offsetX > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetX < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x, coords.y - offset, coords.z - offset, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetY > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x + offset, coords.y, coords.z + offset);
		}
		else if (direction.offsetY < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y, coords.z - offset, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetZ > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x + offset, coords.y + offset, coords.z);
		}
		else if (direction.offsetZ < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	}

	/**
	 * Returns in AABB that is always 3x3 orthogonal to the side hit, but varies in depth in the direction of the side hit
	 */
	public static AxisAlignedBB getDeepBox(Coordinates coords, ForgeDirection direction, int depth)
	{
		if (direction.offsetX != 0)
		{
			if (direction.offsetX > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - depth, coords.y - 1, coords.z - 1, coords.x, coords.y + 1, coords.z + 1);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x, coords.y - 1, coords.z - 1, coords.x + depth, coords.y + 1, coords.z + 1);
		}
		else if (direction.offsetY != 0)
		{
			if (direction.offsetY > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - depth, coords.z - 1, coords.x + 1, coords.y, coords.z + 1);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y, coords.z - 1, coords.x + 1, coords.y + depth, coords.z + 1);
		}
		else
		{
			if (direction.offsetZ > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - 1, coords.z - depth, coords.x + 1, coords.y + 1, coords.z);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - 1, coords.z, coords.x + 1, coords.y + 1, coords.z + depth);
		}
	}
	public static void igniteNearby(World world, EntityPlayer player)
	{
		for (int x = (int) (player.posX - 8); x <= player.posX + 8; x++)
			for (int y = (int) (player.posY - 5); y <= player.posY + 5; y++)
				for (int z = (int) (player.posZ - 8); z <= player.posZ + 8; z++)
					if (world.rand.nextInt(128) == 0 && world.isAirBlock(x, y, z))
					{
						checkedPlaceBlock(((EntityPlayerMP) player), x, y, z, Blocks.fire, 0);
					}
	}
	public static boolean hasBreakPermission(EntityPlayerMP player, int x, int y, int z)
	{
		return hasEditPermission(player, x, y, z)
				&& !ForgeHooks.onBlockBreakEvent(player.worldObj, player.theItemInWorldManager.getGameType(), player, x, y, z).isCanceled();
	}

	public static boolean hasEditPermission(EntityPlayerMP player, int x, int y, int z)
	{
		return player.canPlayerEdit(x, y, z, player.worldObj.getBlockMetadata(x, y, z), null)
				&& !MinecraftServer.getServer().isBlockProtected(player.worldObj, x, y, z, player);
	}
	public static boolean checkedPlaceBlock(EntityPlayerMP player, int x, int y, int z, Block toPlace, int toPlaceMeta)
	{
		if (!hasEditPermission(player, x, y, z))
		{
			return false;
		}
		World world = player.worldObj;
		BlockSnapshot before = BlockSnapshot.getBlockSnapshot(world, x, y, z);
		world.setBlock(x, y, z, toPlace);
		world.setBlockMetadataWithNotify(x, y, z, toPlaceMeta, 3);
		BlockEvent.PlaceEvent evt = new BlockEvent.PlaceEvent(before, Blocks.air, player); // Todo verify can use air here
		MinecraftForge.EVENT_BUS.post(evt);
		if (evt.isCanceled())
		{
			world.restoringBlockSnapshots = true;
			before.restore(true, false);
			world.restoringBlockSnapshots = false;
			return false;
		}
		return true;
	}
	/**
	 * Gets an AABB for AOE digging operations. The charge increases only the breadth of the box.
	 * Y level remains constant. As such, a direction hit is unneeded.
	 */
	public static AxisAlignedBB getFlatYBox(Coordinates coords, int offset)
	{
		return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y, coords.z - offset, coords.x + offset, coords.y, coords.z + offset);
	}
	public static List<TileEntity> getTileEntitiesWithinAABB(World world, AxisAlignedBB bBox)
	{
		List<TileEntity> list = Lists.newArrayList();

		for (int i = (int) bBox.minX; i <= bBox.maxX; i++)
			for (int j = (int) bBox.minY; j <= bBox.maxY; j++)
				for (int k = (int) bBox.minZ; k <= bBox.maxZ; k++)
				{
					TileEntity tile = world.getTileEntity(i, j, k);
					if (tile != null)
					{
						list.add(tile);
					}
				}

		return list;
	}
}
