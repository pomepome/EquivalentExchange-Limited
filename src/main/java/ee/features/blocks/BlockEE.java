package ee.features.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ee.features.EELimited;

public class BlockEE extends Block {

	public BlockEE(Material materialIn,String name)
	{
		super(materialIn);
		GameRegistry.registerBlock(this, name);
		setCreativeTab(EELimited.TabEE).setUnlocalizedName(name);
		((EELimited)EELimited.instance).registerIcon(this, name,0);
    }
}
