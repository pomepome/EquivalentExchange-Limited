package ee.features.blocks.emc;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;
import ee.features.tiles.TileRFConverter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRFConverter extends BlockContainer {

	public BlockRFConverter() {
		super(Material.rock);
		this.setCreativeTab(EELimited.TabEE).setBlockName("RFConverter").setBlockTextureName("ee:RMBlock").setHardness(12f);
		GameRegistry.registerBlock(this, "RFConverter");
		GameRegistry.registerTileEntity(TileRFConverter.class,"RFConverter");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileRFConverter();
	}

}
