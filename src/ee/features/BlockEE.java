package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockEE extends Block {

	public BlockEE(int par1,Material material,String str) {
		super(par1,IconManager.get(str),material);
		setTextureFile("/ee/splites/eqexterra.png").setBlockName(str).setCreativeTab(EELimited.TabEE);
		GameRegistry.registerBlock(this,str);
	}

}
