package ee.features.blocks;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EELimited;
import ee.features.items.interfaces.IPedestalItem;
import ee.features.tiles.DMPedestalTile;
import ee.features.tiles.TileEmc;
import ee.network.PacketHandler;
import ee.network.PacketPedestalSync;
import ee.util.EEProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class BlockDMPedestal extends Block
{
	public BlockDMPedestal() {
        super(Material.rock);
        this.setCreativeTab(EELimited.TabEE);
        this.setHardness(1.0F);
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
        this.setBlockTextureName("ee:DMBlock");
        setBlockName("ee_dmPedestal");
        GameRegistry.registerBlock(this, "DMPedestal");
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        DMPedestalTile tile = ((DMPedestalTile) world.getTileEntity(x, y, z));
        if (tile.getItemStack() != null)
        {
            EEProxy.spawnEntityItem(world, tile.getItemStack().copy(), x, y, z);
        }
        tile.invalidate();
        super.breakBlock(world, x, y, z, block, meta);
    }
    @Override
    public boolean canConnectRedstone(IBlockAccess world,int x,int y,int z,int side)
    {
    	return true;
    }
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            DMPedestalTile tile = ((DMPedestalTile) world.getTileEntity(x, y, z));
            if (player.isSneaking())
            {
                player.openGui(EELimited.instance, EELimited.PEDESTAL , world, x, y, z);
            }
            else
            {
                if (tile.getItemStack() != null && tile.getItemStack().getItem() instanceof IPedestalItem)
                {
                    tile.setActive(!tile.getActive());
                }
            }
            PacketHandler.sendToAllAround(new PacketPedestalSync(tile), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32));
        }
        return true;
    }
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	TileEntity tile = world.getTileEntity(x, y, z);
    	if(tile != null && tile instanceof DMPedestalTile && !world.isRemote)
    	{
    		DMPedestalTile ped = (DMPedestalTile)tile;
    		boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z);
    		boolean lastPowered = world.getBlockMetadata(x, y, z) == 1;
    		if(lastPowered != powered && powered)
    		{
    			ped.setActive(!ped.getActive());
        		PacketHandler.sendToAllAround(new PacketPedestalSync(ped), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32));
    		}
    		world.setBlockMetadataWithNotify(x, y, z, powered ? 1 : 0, 3);
    		world.markBlockForUpdate(x, y, z);
    	}
    }
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack stack)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("EEBlock") && tile instanceof TileEmc)
		{
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			tile.readFromNBT(stack.stackTagCompound);
		}
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
        return EELimited.RENDER_PEDESTAL;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return 12;
    }

    @Override
    public boolean hasTileEntity(int meta)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new DMPedestalTile();
    }
}
