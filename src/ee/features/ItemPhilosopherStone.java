package ee.features;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPhilosopherStone extends ItemFunction {

	public ItemPhilosopherStone(int id) {
		super(id,INameRegistry.Phil);
	}
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack)
    {
		return true;
    }

	@Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

	public String getItemDisplayName(ItemStack par1ItemStack)
    {
		return EnumChatFormatting.YELLOW.toString() + EnumChatFormatting.ITALIC + super.getItemDisplayName(par1ItemStack);
    }
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		//ModLoader.openGUI(var3, new GuiPortableCrafting(var3.inventory));
		//var3.openGui(EELimited.instance,256,var2,0,0,0);
		return var1;
    }
	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, int d) {
	}
	@Override
	public void onDestroyedLog(EntityPlayer p, ItemStack is, int x, int y,
			int z, int blockId) {
	}
}
