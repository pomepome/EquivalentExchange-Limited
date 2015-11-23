package ee.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.tiles.TileEntityColoredAlchChest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockColoredAlchChest extends BlockDirection {

	public BlockColoredAlchChest()
	{
		super(Material.rock);
		setHardness(10.0f).setCreativeTab(EELimited.TabEE).setBlockName(NameRegistry.AlchChest+"_colored").setBlockTextureName("ee:chest_top").setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		GameRegistry.registerBlock(this,this.getUnlocalizedName());
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimited.instance,EELimited.ALCH_COLORED, world, x, y, z);
		}

		return true;
	}
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityColoredAlchChest();
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return EELimited.RENDER_COLORED_CHEST;
	}
}
