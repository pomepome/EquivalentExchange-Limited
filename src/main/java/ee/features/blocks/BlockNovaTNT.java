package ee.features.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.entities.EntityNovaPrimed;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockNovaTNT extends BlockEE
{
	IIcon top,side,bottom;
	public BlockNovaTNT() {
		super("novaTNT");
	}
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float minX, float minY, float minZ)
    {
		if(world.isRemote)
		{
			return true;
		}
		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
		{
			detonate(world, x, y, z, player);
			if(!player.capabilities.isCreativeMode)
			{
				player.getCurrentEquippedItem().damageItem(1, player);
			}
		}
        return true;
    }
	public void onBlockAdded(World world, int x, int y, int z)
    {
		super.onBlockAdded(world, x, y, z);
		if(world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			world.setBlockToAir(x, y, z);
			detonate(world, x, y, z, null);
		}
    }
	private void detonate(World world, int x, int y, int z, EntityPlayer player) {
		world.setBlockToAir(x, y, z);
		EntityNovaPrimed tnt = new EntityNovaPrimed(world, x + 0.5, y, z + 0.5, player);
		world.spawnEntityInWorld(tnt);
		world.playSoundAtEntity(tnt, "game.tnt.primed", 1.0F, 1.0F);
	}
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.detonate(world, x, y, z, world.getClosestPlayer(x, y, z, 100000 * 100000));
            world.setBlockToAir(x, y, z);
        }
    }
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("ee:explosion/top");
		bottom = reg.registerIcon("ee:explosion/bottom");
		side = reg.registerIcon("ee:explosion/nova_side");
    }
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int ori, int metadata)
    {
		if(ori == 1)
		{
			return top;
		}
		else if(ori == 0)
		{
			return bottom;
		}
		return side;
    }
}
