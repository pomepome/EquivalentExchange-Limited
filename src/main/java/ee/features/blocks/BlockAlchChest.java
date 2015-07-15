package ee.features.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.NameRegistry;
import ee.features.tile.TileEntityAlchChest;

public class BlockAlchChest extends BlockDirection implements ITileEntityProvider {

	public BlockAlchChest() {
		super(Material.rock);
		setHardness(10.0f).setCreativeTab(EELimited.TabEE).setBlockName(NameRegistry.AlchChest).setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);;
		GameRegistry.registerBlock(this,this.getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityAlchChest();
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
		return EELimited.RENDER_CHEST;
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimited.instance,EELimited.ALCH_CHEST, world, x, y, z);
		}

		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("ee:chest_top");
	}
	public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
		TileEntity tile = var1.getTileEntity(var2, var3, var4);
		if(tile instanceof TileEntityAlchChest)
		{
			return ((TileEntityAlchChest)tile).lightLevel;
		}
		return 0;
    }
}
