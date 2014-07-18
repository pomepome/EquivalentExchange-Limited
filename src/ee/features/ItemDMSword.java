package ee.features;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMSword extends ItemFunction {

	public int damageVsEntity = 10;
	public ItemDMSword(int id) {
		super(id,INameRegistry.DMSword);
		this.setMaxDamage(200);
	}
	public int getDamageVsEntity(Entity par1Entity)
    {
        return this.damageVsEntity * (int)((EELimited.instance.getPlayer().getCurrentEquippedItem().getItemDamage() + 0.8)*(EELimited.instance.getPlayer().getCurrentEquippedItem().getItemDamage() + 0.8));
    }
	@Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.blockID == Block.web.blockID ? 20:1;
    }
	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,
			int z, int blockId) {
	}
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}

}
