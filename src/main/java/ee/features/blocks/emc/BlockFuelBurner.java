package ee.features.blocks.emc;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EEBlocks;
import ee.features.EELimited;
import ee.features.tiles.TileFuelBurner;
import ee.util.EEProxy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFuelBurner extends BlockContainer
{
	boolean isOn;
	TileFuelBurner tile;
	IIcon other,front_off,front_on;
	public BlockFuelBurner(boolean flag) {
		super(Material.rock);
		this.setBlockName("FuelBurner").setHardness(5f);
		if(!flag)
		{
			setCreativeTab(EELimited.TabEE);
			GameRegistry.registerBlock(this,"FuelBurner");
			GameRegistry.registerTileEntity(TileFuelBurner.class,"tileFuelBurner");
		}
		else
		{
			GameRegistry.registerBlock(this,"FuelBurnerOn");
		}
		isOn = flag;
	}
	public void updateFurnaceBlockState(boolean isActive, World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(isActive)
		{
			world.setBlock(x, y, z,EEBlocks.FuelBurnerOn);
		}
		else
		{
			world.setBlock(x, y, z, EEBlocks.FuelBurner);
		}
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		if(tile != null)
		{
			tile.validate();
			world.setTileEntity(x, y, z, tile);
		}
	}

	@Override
	public Item getItemDropped(int no, Random rand, int clue)
	{
		return Item.getItemFromBlock(EEBlocks.FuelBurner);
	}
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		tile = new TileFuelBurner();
		return tile;
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimited.instance,EELimited.BURNER, world, x, y, z);
		}
		return true;
	}
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		other = reg.registerIcon("ee:matter_furnace/dm");
		front_on = reg.registerIcon("ee:matter_furnace/dm_on");
		front_off = reg.registerIcon("ee:matter_furnace/dm_off");
    }
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int ori, int metadata)
    {
		if(ori == metadata)
		{
			return isOn ? front_on : front_off;
		}
		if(metadata == 0 && ori == 3)
		{
			return isOn ? front_on : front_off;
		}
		return other;
    }
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);
		world.setBlockMetadataWithNotify(x, y, z, EEProxy.getRelativeOrientation(entityLiving), 2);
	}
}
