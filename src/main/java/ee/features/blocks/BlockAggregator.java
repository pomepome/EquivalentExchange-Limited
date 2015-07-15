package ee.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;
import ee.gui.TileEntityAggregator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAggregator extends BlockContainer
{
	public BlockAggregator() {
		super(Material.rock);
		this.setHardness(10f).setCreativeTab(EELimited.TabEE).setBlockName("Aggregator");
		GameRegistry.registerBlock(this,"Aggregator");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityAggregator();
	}
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimited.instance,EELimited.AGGREGATOR, world, x, y, z);
		}
		return true;
	}

}
