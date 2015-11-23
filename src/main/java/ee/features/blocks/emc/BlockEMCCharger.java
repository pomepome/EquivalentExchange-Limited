package ee.features.blocks.emc;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.tiles.TileEMCCharger;
import ee.util.EEProxy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEMCCharger extends BlockContainer
{
	IIcon top,front,side;
	public BlockEMCCharger() {
		super(Material.rock);
		this.setCreativeTab(EELimited.TabEE).setBlockName("EMCCharger").setBlockTextureName("cobblestone").setHardness(1000f);
		GameRegistry.registerBlock(this,"EMCCharger");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEMCCharger();
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimited.instance,EELimited.CHARGER, world, x, y, z);
		}
		return true;
	}
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("ee:charger/top");
		front = reg.registerIcon("ee:charger/front");
		side = reg.registerIcon("ee:charger/other");
    }
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int ori, int metadata)
    {
		if(ori == 1)
		{
			return top;
		}
		if(metadata == 0 && ori == 3)
		{
			return front;
		}
		else if(ori == metadata)
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
