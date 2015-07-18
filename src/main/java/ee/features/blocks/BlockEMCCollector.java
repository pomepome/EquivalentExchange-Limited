package ee.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.Constants;
import ee.features.EELimited;
import ee.features.tile.TileEMCCollector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEMCCollector extends BlockContainer
{
	IIcon top,front,side;
	public BlockEMCCollector() {
		super(Material.rock);
		this.setCreativeTab(EELimited.TabEE).setLightLevel(Constants.COLLECTOR_LIGHT_VALS[0]).setBlockName("EMCCollector").setBlockTextureName("stone").setHardness(6f);
		GameRegistry.registerBlock(this,"EMCCollector");
	}
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEMCCollector();
	}
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("ee:collector/top_Mk1");
		front = reg.registerIcon("ee:collector/front");
		side = reg.registerIcon("ee:collector/other");
    }
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int ori, int metadata)
    {
		if(ori == 1)
		{
			return top;
		}
		return side;
    }
}
