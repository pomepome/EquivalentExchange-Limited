package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMPickaxe extends ItemFunction {
	public int damageVsEntity = 9;
	public ItemDMPickaxe(int id) {
		super(id,INameRegistry.DMPickaxe);
		this.setMaxDamage(20);
	}
	public boolean canHarvestBlock(Block par1Block)
    {
		return true;
    }
	public int getDamageVsEntity(Entity par1Entity)
    {
        return this.damageVsEntity;
    }
	@Override
    public float getStrVsBlock(ItemStack stack, Block block, int meta)
    {
        return block.blockMaterial == Material.rock ? 20 * (stack.getItemDamage() + 1):2.5F;
    }
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}
	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,
			int z, int blockId) {
	}
}
