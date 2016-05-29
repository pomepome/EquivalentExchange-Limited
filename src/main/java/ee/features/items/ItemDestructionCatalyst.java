package ee.features.items;

import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import ee.network.PacketHandler;
import ee.network.PacketSound;
import ee.network.PacketSpawnParticle;
import ee.util.Coordinates;
import ee.util.EEProxy;
import ee.util.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDestructionCatalyst extends ItemChargeable {

	public ItemDestructionCatalyst() {
		super("destCatal", 3);
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote) return stack;

		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, false);

		if (mop != null && mop.typeOfHit.equals(MovingObjectType.BLOCK))
		{
			int numRows = calculateDepthFromCharge(stack);
			boolean hasAction = false;

			ForgeDirection direction = ForgeDirection.getOrientation(mop.sideHit);

			Coordinates coords = new Coordinates(mop);
			AxisAlignedBB box = EEProxy.getDeepBox(coords, direction, --numRows);

			List<ItemStack> drops = Lists.newArrayList();

			for (int x = (int) box.minX; x <= box.maxX; x++)
				for (int y = (int) box.minY; y <= box.maxY; y++)
					for (int z = (int) box.minZ; z <= box.maxZ; z++)
					{
						Block block = world.getBlock(x, y, z);
						float hardness = block.getBlockHardness(world, x, y, z);

						if (block == Blocks.air || hardness >= 50.0F || hardness == -1.0F)
						{
							continue;
						}

						if (!EEProxy.useResource(player, 8, true))
						{
							break;
						}

						if (!hasAction)
						{
							hasAction = true;
						}

						block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
						world.setBlockToAir(x, y, z);

						if (world.rand.nextInt(8) == 0)
						{
							PacketHandler.sendToAllAround(new PacketSpawnParticle("largesmoke", x, y, z), new TargetPoint(world.provider.dimensionId, x, y + 1, z, 32));
						}
					}

			player.swingItem();
			EEProxy.playSoundAtPlayer(EnumSounds.ACTION.getPath(), player,1.0f,1.0f);
		}

		return stack;
	}

	protected int calculateDepthFromCharge(ItemStack stack)
	{
		int charge = getChargeLevel(stack);
		if (charge <= 0)
		{
			return 1;
		}
		return (int) Math.pow(2, 1 + charge);
	}

}
