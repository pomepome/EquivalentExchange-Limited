package ee.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.Constants;
import ee.features.EELimited;
import ee.features.EEProxy;
import ee.features.tile.TileEMCCollectorMk2;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEMCCollectorMk2 extends BlockContainer
{
	IIcon top,front,side;
	public BlockEMCCollectorMk2() {
		super(Material.rock);
		this.setCreativeTab(EELimited.TabEE).setLightLevel(Constants.COLLECTOR_LIGHT_VALS[1]).setBlockName("EMCCollectorMk2").setBlockTextureName("stone").setHardness(7f);
		GameRegistry.registerBlock(this,"EMCCollectorMk2");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEMCCollectorMk2();
	}
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("ee:collector/top_Mk2");
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
		if(ori == metadata)
		{
			return front;
		}
		return side;
    }
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);
		world.setBlockMetadataWithNotify(x, y, z, EEProxy.getRelativeOrientation(entityLiving), 2);
	}
}
