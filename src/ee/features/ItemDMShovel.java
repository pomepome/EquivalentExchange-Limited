package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMShovel extends ItemFunction {

	public int damageVsEntity = 10;
	public ItemDMShovel(int id) {
		super(id,INameRegistry.DMshovel);
		this.setMaxDamage(20);
	}
	public boolean canHarvestBlock(Block par1Block)
    {
		return par1Block.blockMaterial != Material.rock;
    }
	public int getDamageVsEntity(Entity par1Entity)
    {
        return this.damageVsEntity;
    }
	@Override
    public float getStrVsBlock(ItemStack stack, Block block, int meta)
    {
        return block.blockMaterial == Material.ground ||block.blockMaterial == Material.sand? 10 * (stack.getItemDamage() + 1):2.5F;
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
