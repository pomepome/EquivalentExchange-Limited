package ee.features.blocks;

import net.minecraft.block.material.Material;
import ee.features.NameRegistry;

public class BlockDM extends BlockEE {

	public BlockDM() {
		super(Material.rock,NameRegistry.DMBlock);
		this.setDefaultState(getDefaultState());
	}

}
