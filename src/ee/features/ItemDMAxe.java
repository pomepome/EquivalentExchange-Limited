package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMAxe extends ItemFunction {
	public boolean skip1 = true;
	public int damageVsEntity = 12;
	public ItemDMAxe(int id) {
		super(id,INameRegistry.DMAxe);
		this.setMaxDamage(20);
	}
	public boolean canHarvestBlock(Block par1Block)
    {
		return par1Block.blockMaterial != Material.rock;
    }
	public boolean isWood(int blockId)
	{
		Block b = Block.blocksList[blockId];
		if(b == null)
		{
			return false;
		}
		String name = b.getBlockName();
		return name.toLowerCase().contains("wood")||name.toLowerCase().contains("log");
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		skip1 = !skip1;
		if(skip)
		{
			return true;
		}
		if(par1ItemStack.getItemDamage() > 0 && isWood(par3World.getBlockId(par4, par5, par6)))
		{
			if(EELimited.cutDown)
			{
				for(;isWood(par3World.getBlockId(par4, par5, par6));par5--){}
				par5++;
			}
			for(int i = par5;i < 256;i++)
			{
				if(!isWood(par3World.getBlockId(par4, i, par6)))
				{
					break;
				}
				Block.blocksList[par3World.getBlockId(par4,i, par6)].dropBlockAsItem(par3World, par4,i, par6, par3World.getBlockMetadata(par4,i, par6), 0);	
				Block.blocksList[par3World.getBlockId(par4,i, par6)].breakBlock(par3World, par4, i, par6, par3World.getBlockMetadata(par4, i, par6), 0);
				par3World.setBlockWithNotify(par4, i, par6, 0);
			}
		}
		else
		{
			par1ItemStack.setItemDamage(1 - par1ItemStack.getItemDamage());
		}
		return true;
    }
	public int getDamageVsEntity(Entity par1Entity)
    {
        return this.damageVsEntity;
    }
	@Override
    public float getStrVsBlock(ItemStack stack, Block block, int meta)
    {
        return block.blockMaterial == Material.wood? 20 * (stack.getItemDamage() + 1):2.5F;
    }
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}
	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y, int z,int blockId) {
		
	}

}
